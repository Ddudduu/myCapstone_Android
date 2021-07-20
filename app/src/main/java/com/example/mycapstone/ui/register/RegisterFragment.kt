package com.example.mycapstone.ui.register

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.mycapstone.R
import com.example.mycapstone.databinding.RegisterFragmentBinding
import androidx.navigation.fragment.findNavController

class RegisterFragment : Fragment() {
    val viewModel: RegisterViewmodel by viewModels()
    lateinit var binding: RegisterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<RegisterFragmentBinding>(
            inflater,
            R.layout.register_fragment,
            container,
            false
        )
        binding.viewModel = viewModel

        // 회원가입 버튼 클릭 시, 알림창 띄우기
        binding.buttonSignUp.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("알림")
                .setMessage("회원 가입이 완료되었습니다.")
                .setPositiveButton("확인"){dialog, which ->
                    // 메인 화면으로 넘어감
                    findNavController().navigate(R.id.action_register_to_home)
                }
                .create()

            alertDialog.show()

        }
        return binding.root
    }
}