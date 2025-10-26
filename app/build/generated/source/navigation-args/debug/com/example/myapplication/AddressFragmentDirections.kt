package com.example.myapplication

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class AddressFragmentDirections private constructor() {
  public companion object {
    public fun actionAddressFragmentToAddFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_addressFragment_to_addFragment)

    public fun actionAddressFragmentToEditFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_addressFragment_to_editFragment)
  }
}
