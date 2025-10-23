package com.example.myapplication.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.example.myapplication.users.UserViewModel

abstract class BaseFragment <VB : ViewBinding>(private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB)
    : Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listeners()
        bind()
    }

    abstract fun listeners()
    abstract fun bind()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}