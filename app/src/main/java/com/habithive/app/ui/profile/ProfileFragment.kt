package com.habithive.app.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
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
        viewModel.loadUserData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUserData()
    }

    private fun setupUI() {
        binding.buttonLogout.setOnClickListener {
            viewModel.logout()
            navigateToLogin()
        }

        binding.buttonEditProfile.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.switchShareGoal.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateShareGoalPreference(isChecked)
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.userName.observe(viewLifecycleOwner) { binding.textName.text = it }
        viewModel.email.observe(viewLifecycleOwner) { binding.textEmail.text = it }
        viewModel.gender.observe(viewLifecycleOwner) { binding.textGender.text = it }
        viewModel.health.observe(viewLifecycleOwner) { binding.textHealth.text = it }
        viewModel.height.observe(viewLifecycleOwner) { binding.textHeight.text = it }
        viewModel.weight.observe(viewLifecycleOwner) { binding.textWeight.text = it }
        viewModel.dateOfBirth.observe(viewLifecycleOwner) { binding.textDob.text = it }
        viewModel.age.observe(viewLifecycleOwner) { binding.textAge.text = it }
        viewModel.bmi.observe(viewLifecycleOwner) { binding.textBmi.text = it }

        // ✅ Observe Share Goal
        viewModel.shareGoal.observe(viewLifecycleOwner) { shared ->
            binding.switchShareGoal.isChecked = shared
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
