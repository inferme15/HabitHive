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
import com.google.firebase.firestore.*
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
    private var achievementListener: ListenerRegistration? = null
    private var selectedType: String = "daily"
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupTabs()
        loadLeaderboard(selectedType)
        listenForLeaderboardUpdates()

        binding.swipeRefresh.setOnRefreshListener {
            loadLeaderboard(selectedType)
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
                selectedType = if (tab?.position == 0) "daily" else "weekly"
                achievementListener?.remove()
                loadLeaderboard(selectedType)
                listenForLeaderboardUpdates()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun loadLeaderboard(type: String) {
        if (!isAdded || _binding == null || isLoading) return
        isLoading = true

        binding.progressBar.visibility = View.VISIBLE
        leaderboardUsers.clear()
        leaderboardAdapter.notifyDataSetChanged()

        val currentUserId = auth.currentUser?.uid ?: return
        val scoreField = if (type == "daily") "dailyScore" else "weeklyScore"

        firestore.collection("users").document(currentUserId)
            .get()
            .addOnSuccessListener { userDoc ->
                val shareGoal = userDoc.getBoolean("shareGoal") ?: false

                val achievementQuery = if (shareGoal) {
                    firestore.collection("achievements")
                        .orderBy(scoreField, Query.Direction.DESCENDING)
                        .limit(50)
                } else {
                    firestore.collection("achievements")
                        .whereEqualTo(FieldPath.documentId(), currentUserId)
                }

                achievementQuery.get().addOnSuccessListener { documents ->
                    val achievements = documents.map { doc ->
                        val userId = doc.id
                        val score = doc.getLong(scoreField)?.toInt() ?: 0
                        userId to score
                    }

                    val usersToFetch = achievements.map { it.first }.toSet()

                    firestore.collection("users")
                        .whereIn(FieldPath.documentId(), usersToFetch.toList())
                        .get()
                        .addOnSuccessListener { userDocs ->
                            if (!isAdded || _binding == null) return@addOnSuccessListener

                            val userMap = userDocs.associateBy { it.id }
                            val results = mutableListOf<LeaderboardUser>()

                            for ((userId, score) in achievements) {
                                val uDoc = userMap[userId]
                                val name = uDoc?.getString("name") ?: "Unknown"
                                val uShare = uDoc?.getBoolean("shareGoal") ?: false

                                if (shareGoal) {
                                    if (uShare || userId == currentUserId) {
                                        results.add(LeaderboardUser(userId, name, score, 0))
                                    }
                                } else if (userId == currentUserId) {
                                    results.add(LeaderboardUser(userId, name, score, 0))
                                }
                            }

                            results.sortByDescending { it.score }
                            results.forEachIndexed { index, user -> user.rank = index + 1 }

                            leaderboardUsers.clear()
                            leaderboardUsers.addAll(results)
                            leaderboardAdapter.notifyDataSetChanged()

                            binding.textEmpty.visibility = if (results.isEmpty()) View.VISIBLE else View.GONE
                            binding.progressBar.visibility = View.GONE
                            binding.swipeRefresh.isRefreshing = false
                            isLoading = false
                        }
                        .addOnFailureListener { showError(it.message) }
                }.addOnFailureListener { showError(it.message) }
            }
            .addOnFailureListener { showError(it.message) }
    }

    private fun listenForLeaderboardUpdates() {
        achievementListener?.remove()
        val currentUserId = auth.currentUser?.uid ?: return

        achievementListener = firestore.collection("achievements")
            .document(currentUserId)
            .addSnapshotListener { _, error ->
                if (error == null && isAdded && _binding != null) {
                    loadLeaderboard(selectedType)
                }
            }
    }

    private fun showError(message: String?) {
        if (_binding != null && isAdded) {
            binding.progressBar.visibility = View.GONE
            binding.swipeRefresh.isRefreshing = false
            Toast.makeText(requireContext(), "Error loading leaderboard: $message", Toast.LENGTH_SHORT).show()
        }
        isLoading = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        achievementListener?.remove()
    }
}
