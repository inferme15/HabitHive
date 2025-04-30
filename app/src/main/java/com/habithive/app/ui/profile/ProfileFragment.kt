package com.habithive.app.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.habithive.app.R
import com.habithive.app.databinding.FragmentProfileBinding
import com.habithive.app.ui.auth.LoginActivity

class ProfileFragment : Fragment() {
    
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: ProfileViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        observeViewModel()
    }
    
    private fun setupUI() {
        binding.buttonLogout.setOnClickListener {
            viewModel.logout()
            navigateToLogin()
        }
        
        binding.buttonEditProfile.setOnClickListener {
            // Navigate to edit profile screen
            // TODO: Implement edit profile functionality
        }
    }
    
    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        
        viewModel.userName.observe(viewLifecycleOwner) { name ->
            binding.textName.text = name
        }
        
        viewModel.email.observe(viewLifecycleOwner) { email ->
            binding.textEmail.text = email
        }
        
        viewModel.gender.observe(viewLifecycleOwner) { gender ->
            binding.textGender.text = gender
        }
        
        viewModel.health.observe(viewLifecycleOwner) { health ->
            binding.textHealth.text = health
        }
        
        viewModel.height.observe(viewLifecycleOwner) { height ->
            binding.textHeight.text = height
        }
        
        viewModel.weight.observe(viewLifecycleOwner) { weight ->
            binding.textWeight.text = weight
        }
    }
    
    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}