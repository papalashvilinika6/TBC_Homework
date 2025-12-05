package com.example.myapplication.presentation.ui.cards

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCardBinding
import com.example.myapplication.presentation.ui.CardEvent
import com.example.myapplication.presentation.ui.common.BaseFragment
import com.example.myapplication.presentation.ui.common.showSnack
import com.example.myapplication.presentation.ui.utils.hide
import com.example.myapplication.presentation.ui.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardFragment :
    BaseFragment<FragmentCardBinding>(FragmentCardBinding::inflate) {

    private val viewModel: CardViewModel by viewModels()
    private lateinit var adapter: CardPagerAdapter

    override fun bind() {
        adapter = CardPagerAdapter()
        binding.viewPager.adapter = adapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        viewModel.onEvent(CardEvent.Load)
    }

    override fun observers() {

        lifecycleScope.launch {
            viewModel.state.collect { state ->

                adapter.submitList(state.cards)
                binding.progressBar.isVisible = state.isLoading
                state.errorMessage?.let { message ->
                    binding.root.showSnack(message)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.sideEffect.collect { effect ->
                when (effect) {
                    is CardSideEffect.ShowError -> {
                        binding.root.showSnack(getString(R.string.error_loading_cards))
                    }
                }
            }
        }
    }

}
