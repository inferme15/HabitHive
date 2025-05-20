package com.habithive.app.ui.habits

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.habithive.app.adapters.HabitAdapter
import com.habithive.app.data.model.Exercise
import com.habithive.app.databinding.FragmentHabitsBinding
import com.habithive.app.ui.exercise.ExerciseActivity
import java.util.*

class HabitsFragment : Fragment() {

    private var _binding: FragmentHabitsBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var habitAdapter: HabitAdapter
    private val todayExerciseList = mutableListOf<Exercise>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        listenToTodayExercises()

        binding.fabAddHabit.setOnClickListener {
            startActivity(Intent(requireContext(), ExerciseActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        habitAdapter = HabitAdapter(todayExerciseList) { exerciseToDelete ->
            confirmAndDeleteExercise(exerciseToDelete)
        }

        binding.recyclerHabits.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = habitAdapter
        }
    }

    private fun listenToTodayExercises() {
        val userId = auth.currentUser?.uid ?: return

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val todayStart = calendar.time

        firestore.collection("exercises")
            .whereEqualTo("userId", userId)
            .whereGreaterThanOrEqualTo("date", todayStart)
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Toast.makeText(requireContext(), "Error loading exercises", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                todayExerciseList.clear()
                snapshot?.documents?.forEach { doc ->
                    doc.toObject(Exercise::class.java)?.let {
                        todayExerciseList.add(it.copy(id = doc.id))
                    }
                }

                habitAdapter.notifyDataSetChanged()
                binding.textEmpty.visibility = if (todayExerciseList.isEmpty()) View.VISIBLE else View.GONE
            }
    }

    private fun confirmAndDeleteExercise(exercise: Exercise) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Exercise")
            .setMessage("Are you sure you want to delete this exercise?")
            .setPositiveButton("Yes") { _, _ ->
                deleteExerciseWithAchievementsUpdate(exercise)
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteExerciseWithAchievementsUpdate(exercise: Exercise) {
        val userId = auth.currentUser?.uid ?: return

        firestore.collection("exercises").document(exercise.id)
            .delete()
            .addOnSuccessListener {
                updateAchievementsAfterDelete(userId, exercise.caloriesBurned, exercise.pointsEarned)
                Toast.makeText(requireContext(), "Exercise deleted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to delete exercise", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateAchievementsAfterDelete(userId: String, caloriesToSubtract: Int, pointsToSubtract: Int) {
        val ref = firestore.collection("achievements").document(userId)
        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(ref)
            val totalPoints = (snapshot.getLong("totalPoints") ?: 0L) - pointsToSubtract
            val calories = (snapshot.getLong("caloriesBurned") ?: 0L) - caloriesToSubtract
            val dailyScore = (snapshot.getLong("dailyScore") ?: 0L) - pointsToSubtract
            val weeklyScore = (snapshot.getLong("weeklyScore") ?: 0L) - pointsToSubtract

            transaction.update(ref, mapOf(
                "totalPoints" to totalPoints.coerceAtLeast(0),
                "caloriesBurned" to calories.coerceAtLeast(0),
                "dailyScore" to dailyScore.coerceAtLeast(0),
                "weeklyScore" to weeklyScore.coerceAtLeast(0)
            ))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
