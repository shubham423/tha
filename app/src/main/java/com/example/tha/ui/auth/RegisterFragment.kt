package com.example.tha.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tha.AuthViewModel
import com.example.tha.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {


    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        subscribeToObservers()

        binding.registerBtn.setOnClickListener {
            viewModel.register(
                binding.emailEt.text.toString(),
                binding.nameEt.text.toString(),
                binding.passwordEt.text.toString(),
                binding.mobileEt.text.toString(),
            )
        }

        binding.alreadyHaveAccountTv.setOnClickListener {
            if(findNavController().previousBackStackEntry != null) {
                findNavController().popBackStack()
            } else findNavController().navigate(
                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            )
        }
    }

    private fun subscribeToObservers() {
        viewModel.registerStatus.observe(viewLifecycleOwner){
            if (it=="success"){
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            }else{
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}