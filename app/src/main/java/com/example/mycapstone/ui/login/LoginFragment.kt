package com.example.mycapstone.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mycapstone.R
import com.example.mycapstone.databinding.LoginFragmentBinding
import com.example.mycapstone.ui.base.BaseNavigationFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import timber.log.Timber

class LoginFragment : BaseNavigationFragment(R.layout.login_fragment) {
  val viewModel: LoginViewModel by viewModels()
  lateinit var binding: LoginFragmentBinding

  private val webClientId = "633392197313-ougjvtocqosbff7hde9ghtck73ks2bhi.apps.googleusercontent.com"
  private lateinit var client: GoogleSignInClient
  private lateinit var firebaseAuth: FirebaseAuth

  private val requestCode = 1

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
    binding.vm = viewModel
    binding.loginButton.setOnClickListener { setGoogleLogin() }
    binding.logoutButton.setOnClickListener { logout() }

    binding.customToolbar.setOnMenuItemClickListener {
      when (it.itemId) {
        R.id.action_search -> {
          navigate(R.id.action_login_fragment_to_search_fragment)
          true
        }
        R.id.action_star -> {
          navigate(R.id.action_login_fragment_to_bookmark_fragment)
          true
        }
        else -> false
      }
    }
    return binding.root
  }


  private fun logout() {
    FirebaseAuth.getInstance().signOut()
    Toast.makeText(requireContext(), "로그아웃에 성공했습니다.", Toast.LENGTH_SHORT).show()
  }


  private fun setGoogleLogin() {
    // 요청 정보 옵션
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(webClientId).requestEmail().build()
    client = GoogleSignIn.getClient(requireActivity(), gso)
    firebaseAuth = FirebaseAuth.getInstance()
    // 로그인 요청
    startActivityForResult(client.signInIntent, requestCode)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == this.requestCode) {
      val task = GoogleSignIn.getSignedInAccountFromIntent(data)

      try {
        val account = task.getResult(ApiException::class.java)
        firebaseAuthWithGoogle(account!!.idToken)
      } catch (e: ApiException) {
        Toast.makeText(requireContext(), "Failed Google Login", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
      }
    }
  }

  private fun firebaseAuthWithGoogle(idToken: String?) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    firebaseAuth.signInWithCredential(credential)?.addOnCompleteListener(requireActivity()) { task ->
      if (task.isSuccessful) {
        // 인증에 성공한 후, 로그인된 유저 정보 가져오기
        firebaseAuth.currentUser?.email?.let { firebaseAuth.currentUser?.displayName?.let { it1 -> viewModel.updateUserInfo(it, it1) } }
        Timber.i("===lmw user Email=== ${viewModel.userEmail.value}")

        Toast.makeText(
          requireContext(), "${viewModel.userName.value} 님 안녕하세요!", Toast.LENGTH_LONG
        ).show()
        navigateUp(R.id.home_fragment, false)
      } else {
        Toast.makeText(requireContext(), "로그인에 실패했습니다", Toast.LENGTH_LONG).show()
      }
    }
  }
}