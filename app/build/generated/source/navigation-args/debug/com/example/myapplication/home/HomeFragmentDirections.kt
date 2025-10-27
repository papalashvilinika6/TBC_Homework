package com.example.myapplication.home

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.myapplication.R
import kotlin.Int

public class HomeFragmentDirections private constructor() {
  private data class ActionHomeFragmentToGameFragment(
    public val boardSize: Int = 3,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_homeFragment_to_gameFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("boardSize", this.boardSize)
        return result
      }
  }

  public companion object {
    public fun actionHomeFragmentToGameFragment(boardSize: Int = 3): NavDirections =
        ActionHomeFragmentToGameFragment(boardSize)
  }
}
