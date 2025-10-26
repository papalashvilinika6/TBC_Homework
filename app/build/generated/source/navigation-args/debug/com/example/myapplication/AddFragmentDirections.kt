package com.example.myapplication

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class AddFragmentDirections private constructor() {
  public companion object {
    public fun actionAddFragmentToAddressFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_addFragment_to_addressFragment)
  }
}
