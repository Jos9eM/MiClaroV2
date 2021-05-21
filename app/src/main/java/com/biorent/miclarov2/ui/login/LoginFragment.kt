package com.biorent.miclarov2.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.biorent.miclarov2.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var binding: LoginFragmentBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.textLoginInit.observe(viewLifecycleOwner, {
            binding.loginInitTxt.text = it      })

        loginViewModel.hintLoginRUT.observe(viewLifecycleOwner, {
            binding.rutField.hint = it      })

        loginViewModel.hintLoginPass.observe(viewLifecycleOwner, {
            binding.passField.hint = it        })

        loginViewModel.textCheckRemember.observe(viewLifecycleOwner, {
            binding.checkRemember.text = it     })

        loginViewModel.textLoginBtn.observe(viewLifecycleOwner, {
            binding.loginBtn.text = it     })

        loginViewModel.textPassForgot.observe(viewLifecycleOwner, {
            binding.forgetPassTxt.text = it
        })

        loginViewModel.textHaveAccount.observe(viewLifecycleOwner, {
            binding.haveAccountTxt.text = it
        })

        loginViewModel.textRegisterHere.observe(viewLifecycleOwner, {
            binding.registerTxt.text = it
        })

        loginViewModel.textVisitPortal.observe(viewLifecycleOwner, {
            binding.visitPortalTxt.text = it
        })

        loginViewModel.textNeedHelp.observe(viewLifecycleOwner, {
            binding.needHelpTxt.text = it
        })

        return binding.root
    }
}