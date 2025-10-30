package com.example.myapplication.order

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.myapplication.R

public class OrderFragmentDirections private constructor() {
  public companion object {
    public fun actionOrderFragmentToDetailsFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_orderFragment_to_detailsFragment)
  }
}
