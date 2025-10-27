package com.example.myapplication.address

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.myapplication.R

public class AddressFragmentDirections private constructor() {
  public companion object {
    public fun actionAddressFragmentToAddFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_addressFragment_to_addFragment)

    public fun actionAddressFragmentToEditFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_addressFragment_to_editFragment)
  }
}
