package com.example.myapplication.ui.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.Coil
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.myapplication.domain.model.Place
import com.example.myapplication.databinding.FragmentMapBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import com.example.myapplication.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.example.myapplication.R
import com.example.myapplication.domain.connectivity.ConnectivityObserver
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate), OnMapReadyCallback {

    private val vm: MapViewModel by viewModels()
    @Inject
    lateinit var connectivityObserver: ConnectivityObserver
    private var googleMap: GoogleMap? = null
    private var clusterManager: ClusterManager<PlaceClusterItem>? = null
    private var myMarker: Marker? = null

    override fun bind() {
        setupMap()
    }

    override fun listeners() {
        super.listeners()
        btnShowAll()
        btnZoomOut()
        btnZoomIn()
    }



    override fun observers() {
        super.observers()
        observeMapState()
        observeConnectivity()
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_container) as? SupportMapFragment
            ?: SupportMapFragment.newInstance().also {
                childFragmentManager.beginTransaction().replace(R.id.map_container, it).commitNow()
            }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false
        enableMyLocationIfGranted()

        clusterManager = ClusterManager<PlaceClusterItem>(requireContext(), map).also { cm ->
            map.setOnCameraIdleListener(cm)
            map.setOnMarkerClickListener(cm)

            cm.setOnClusterItemClickListener { item: PlaceClusterItem ->
                vm.onEvent(MapEvent.SelectPlace(item.place))
                true
            }
        }

        vm.onEvent(MapEvent.Load)
    }

    private fun observeMapState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collect { s ->
                    binding.loader.isVisible = s.loading
                    updateMyLocation(s.myLocation)
                    updateMarkers(s.places)
                    prefetchImages(s.places)
                    s.selected?.let { showBottomSheet(it) }
                }
            }
        }
    }

    private fun observeConnectivity() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                connectivityObserver.isConnected.collect { online ->
                    binding.noInternetBanner.isVisible = !online
                }
            }
        }
    }

    private fun prefetchImages(places: List<Place>) {
        val ctx = requireContext()
        val loader = Coil.imageLoader(ctx)
        places.asSequence()
            .map { it.imageUrl }
            .distinct()
            .forEach { url ->
                val req = ImageRequest.Builder(ctx)
                    .data(url)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.DISABLED)
                    .build()
                loader.enqueue(req)
            }
    }

    private fun enableMyLocationIfGranted() {
        val ctx = requireContext()
        val fine = ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarse = ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (fine || coarse) googleMap?.isMyLocationEnabled = true
    }

    private fun updateMyLocation(latLng: LatLng?) {
        val map = googleMap ?: return
        if (latLng != null) {
            if (myMarker == null) {
                myMarker = map.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(getString(R.string.you_are_here))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                )
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
            } else {
                myMarker?.position = latLng
            }
        }
    }

    private fun updateMarkers(places: List<Place>) {
        val cm = clusterManager ?: return
        cm.clearItems()
        cm.addItems(places.map { PlaceClusterItem(it) })
        cm.cluster()
    }

    private fun showBottomSheet(place: Place) {
        vm.onEvent(MapEvent.SelectPlace(null))
        PlaceBottomSheetDialogFragment.newInstance(place).show(childFragmentManager,
            getString(R.string.place_sheet))
    }

    private fun fitAll(places: List<Place>) {
        val map = googleMap ?: return
        if (places.isEmpty()) return
        val builder = LatLngBounds.builder()
        places.forEach { builder.include(LatLng(it.latitude, it.longitude)) }
        val bounds = builder.build()
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 80))
    }

    private fun btnZoomIn() {
        binding.btnZoomIn.setOnClickListener { googleMap?.animateCamera(CameraUpdateFactory.zoomIn()) }
    }

    private fun btnZoomOut() {
        binding.btnZoomOut.setOnClickListener { googleMap?.animateCamera(CameraUpdateFactory.zoomOut()) }
    }

    private fun btnShowAll() {
        binding.btnShowAll.setOnClickListener { fitAll(vm.state.value.places) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clusterManager = null
        googleMap = null
    }
}

