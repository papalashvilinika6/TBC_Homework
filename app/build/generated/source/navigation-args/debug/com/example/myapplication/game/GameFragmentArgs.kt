package com.example.myapplication.game

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class GameFragmentArgs(
  public val boardSize: Int = 3,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("boardSize", this.boardSize)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("boardSize", this.boardSize)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): GameFragmentArgs {
      bundle.setClassLoader(GameFragmentArgs::class.java.classLoader)
      val __boardSize : Int
      if (bundle.containsKey("boardSize")) {
        __boardSize = bundle.getInt("boardSize")
      } else {
        __boardSize = 3
      }
      return GameFragmentArgs(__boardSize)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): GameFragmentArgs {
      val __boardSize : Int?
      if (savedStateHandle.contains("boardSize")) {
        __boardSize = savedStateHandle["boardSize"]
        if (__boardSize == null) {
          throw IllegalArgumentException("Argument \"boardSize\" of type integer does not support null values")
        }
      } else {
        __boardSize = 3
      }
      return GameFragmentArgs(__boardSize)
    }
  }
}
