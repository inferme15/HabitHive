package com.habithive.app.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.auth.LoginActivity
import com.habithive.app.databinding.FragmentProfileBinding
import com.habithive.app.model.User

class ProfileFragment : Fragment() {
    
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        
        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Load user profile
        loadUserProfile()
        
        // Setup edit profile button
        binding.buttonEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }
        
        // Setup logout button
        binding.buttonLogout.setOnClickListener {
            logoutUser()
        }
    }
    
    private fun loadUserProfile() {
        val userId = auth.currentUser?.uid ?: return
        
        binding.progressBar.visibility = View.VISIBLE
        
        firestore.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                binding.progressBar.visibility = View.GONE
                
                if (document != null && document.exists()) {
                    val user = document.toObject(User::class.java)
                    user?.let {
                        binding.textName.text = it.name
                        binding.textEmail.text = it.email
                        
                        if (it.gender.isNotEmpty()) {
                            binding.textGender.text = it.gender
                        } else {
                            binding.textGender.text = "Not specified"
                        }
                        
                        if (it.health.isNotEmpty()) {
                            binding.textHealth.text = it.health
                        } else {
                            binding.textHealth.text = "Not specified"
                        }
                        
                        if (it.height > 0) {
                            binding.textHeight.text = "${it.height} cm"
                        } else {
                            binding.textHeight.text = "Not specified"
                        }
                        
                        if (it.weight > 0) {
                            binding.textWeight.text = "${it.weight} kg"
                        } else {
                            binding.textWeight.text = "Not specified"
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Error loading profile: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    
    private fun logoutUser() {
        auth.signOut()
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finish()
    }
    
    override fun onResume() {
        super.onResume()
        loadUserProfile()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
