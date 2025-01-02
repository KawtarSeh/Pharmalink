package com.example.pharmalink.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.example.pharmalink.R
import com.example.pharmalink.databinding.FragmentLoginBinding


/**
 * A simple [Fragment] subclass.
 * Use the [login.newInstance] factory method to
 * create an instance of this fragment.
 */
class login : Fragment() {
    private var _binding: FragmentLoginBinding?= null
    private val binding get()= _binding !!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentLoginBinding.inflate(
            inflater, container, false)
        binding.loginButton.setOnClickListener{
            findNavController().navigate(R.id.action_login_to_home)
        }
    return binding.getRoot();
    }

    }
