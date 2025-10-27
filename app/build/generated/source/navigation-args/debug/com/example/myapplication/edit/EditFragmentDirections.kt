package com.example.myapplication.edit

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.myapplication.R

public class EditFragmentDirections private constructor() {
  public companion object {
    public fun actionEditFragmentToAddressFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_editFragment_to_addressFragment)
  }
}
