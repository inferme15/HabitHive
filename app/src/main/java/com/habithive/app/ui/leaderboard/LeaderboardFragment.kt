package com.habithive.app.ui.leaderboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.habithive.app.adapters.LeaderboardAdapter
import com.habithive.app.databinding.FragmentLeaderboardBinding
import com.habithive.app.model.LeaderboardUser

class LeaderboardFragment : Fragment() {
    
    private var _binding: FragmentLeaderboardBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var leaderboardAdapter: LeaderboardAdapter
    private val leaderboardUsers = mutableListOf<LeaderboardUser>()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        
        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Setup RecyclerView
        setupRecyclerView()
        
        // Setup tabs
        setupTabs()
        
        // Load daily leaderboard by default
        loadLeaderboard("daily")
        
        // Setup refresh
        binding.swipeRefresh.setOnRefreshListener {
            val tabPosition = binding.tabLayout.selectedTabPosition
            val type = if (tabPosition == 0) "daily" else "weekly"
            loadLeaderboard(type)
        }
    }
    
    private fun setupRecyclerView() {
        leaderboardAdapter = LeaderboardAdapter(leaderboardUsers, auth.currentUser?.uid ?: "")
        
        binding.recyclerLeaderboard.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = leaderboardAdapter
        }
    }
    
    private fun setupTabs() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> loadLeaderboard("daily")
                    1 -> loadLeaderboard("weekly")
                }
            }
            
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
    
    private fun loadLeaderboard(type: String) {
        binding.progressBar.visibility = View.VISIBLE
        
        // Determine which score field to use for sorting
        val scoreField = if (type == "daily") "dailyScore" else "weeklyScore"
        
        firestore.collection("achievements")
            .orderBy(scoreField, Query.Direction.DESCENDING)
            .limit(50)
            .get()
            .addOnSuccessListener { documents ->
                binding.progressBar.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false
                
                leaderboardUsers.clear()
                var rank = 1
                
                // Process achievements
                for (doc in documents) {
                    val userId = doc.id
                    val score = doc.getLong(scoreField)?.toInt() ?: 0
                    
                    // Get user name from users collection
                    firestore.collection("users")
                        .document(userId)
                        .get()
                        .addOnSuccessListener { userDoc ->
                            val name = userDoc.getString("name") ?: "Unknown"
                            
                            val leaderboardUser = LeaderboardUser(
                                id = userId,
                                name = name,
                                score = score,
                                rank = rank++
                            )
                            
                            leaderboardUsers.add(leaderboardUser)
                            leaderboardUsers.sortByDescending { it.score }
                            leaderboardAdapter.notifyDataSetChanged()
                            
                            // Update empty state
                            if (leaderboardUsers.isEmpty()) {
                                binding.textEmpty.visibility = View.VISIBLE
                            } else {
                                binding.textEmpty.visibility = View.GONE
                            }
                        }
                }
            }
            .addOnFailureListener { e ->
                binding.progressBar.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false
                Toast.makeText(requireContext(), "Error loading leaderboard: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
