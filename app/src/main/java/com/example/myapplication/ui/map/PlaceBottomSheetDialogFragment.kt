package com.example.myapplication.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import coil.request.CachePolicy
import com.example.myapplication.domain.model.Place
import com.example.myapplication.databinding.BottomSheetPlaceBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PlaceBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetPlaceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomSheetPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val place = requireArguments().getParcelable<Place>(ARG_PLACE)!!
        binding.txtTitle.text = place.title
        binding.txtDesc.text = place.description
        binding.img.load(place.imageUrl) {
            diskCachePolicy(CachePolicy.ENABLED)
        }
    }

    override fun onDestroyView() { _binding = null; super.onDestroyView() }

    companion object {
        private const val ARG_PLACE = "arg_place"
        fun newInstance(place: Place) = PlaceBottomSheetDialogFragment().apply {
            arguments = Bundle().apply { putParcelable(ARG_PLACE, place) }
        }
    }
}

