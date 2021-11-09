package com.example.mycapstone.ui.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.navigation.NavDirections
import timber.log.Timber
import androidx.navigation.fragment.findNavController


open class BaseNavigationFragment(@LayoutRes layoutId: Int) : BaseFragment(layoutId) {
  /**
   * 이전 화면으로 이동하기
   */
  protected fun navigateUp() {
    try {
      if (parentFragmentManager.backStackEntryCount == 0) {
        activity?.finish()
      } else {
        onNavigateUp()
        findNavController().navigateUp()
      }
    } catch (e: Exception) {
      Timber.w(e)
    }
  }

  /**
   * 최상위 화면으로 이동하기 위해
   */
  protected fun navigateUp(destinationId: Int, inclusive: Boolean) {
    try {
      if (parentFragmentManager.backStackEntryCount == 0) {
        activity?.finish()
      } else {
        onNavigateUp()
        findNavController().popBackStack(destinationId, inclusive)
      }
    } catch (e: Exception) {
      Timber.w(e)
    }
  }

  /**
   * 뒤로가기할 때 할 일이 있으면 여기서 처리하면 됨
   */
  open fun onNavigateUp() {}

  /**
   * 다른 화면으로 이동하기
   */
  protected fun navigate(@IdRes actionResId: Int, bundle: Bundle? = null) {
    findNavController().navigate(actionResId, bundle)
  }

  /**
   * 다른 화면으로 이동하기
   */
  protected fun navigate(navDirections: NavDirections) {
    findNavController().navigate(navDirections)
  }
}