package com.example.myapplication

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class EditFragmentDirections private constructor() {
  public companion object {
    public fun actionEditFragmentToAddressFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_editFragment_to_addressFragment)
  }
}
