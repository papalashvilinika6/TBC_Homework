package com.example.myapplication.add

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.myapplication.R

public class AddFragmentDirections private constructor() {
  public companion object {
    public fun actionAddFragmentToAddressFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_addFragment_to_addressFragment)
  }
}
