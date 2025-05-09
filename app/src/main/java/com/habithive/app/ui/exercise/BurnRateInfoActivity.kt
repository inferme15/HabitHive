package com.habithive.app.ui.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.habithive.app.R
import com.habithive.app.databinding.ActivityBurnRateInfoBinding

/**
 * Activity to display information about exercise burn rates
 */
class BurnRateInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBurnRateInfoBinding

    // Data for burn rate explanation
    data class BurnRateInfo(
        val exerciseType: String,
        val burnRate: Int,
        val intensityLevel: String
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityBurnRateInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.burn_rate_info_title)

        // Setup RecyclerView
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // Create burn rate data
        val burnRates = createBurnRatesList()

        // Set up adapter and layout manager
        val adapter = BurnRateAdapter(burnRates)
        binding.recyclerViewBurnRates.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewBurnRates.adapter = adapter
    }

    private fun createBurnRatesList(): List<BurnRateInfo> {
        return listOf(
            BurnRateInfo("Walking", 4, "Low"),
            BurnRateInfo("Running", 10, "High"),
            BurnRateInfo("Cycling", 7, "Medium"),
            BurnRateInfo("Swimming", 8, "Medium-High"),
            BurnRateInfo("Hiking", 6, "Medium"),
            BurnRateInfo("Yoga", 3, "Low"),
            BurnRateInfo("Weight Training", 5, "Medium"),
            BurnRateInfo("HIIT", 12, "Very High"),
            BurnRateInfo("Pilates", 4, "Low-Medium"),
            BurnRateInfo("Dancing", 7, "Medium"),
            BurnRateInfo("Rock Climbing", 9, "High"),
            BurnRateInfo("Boxing", 11, "Very High"),
            BurnRateInfo("Tennis", 8, "Medium-High"),
            BurnRateInfo("Basketball", 9, "High"),
            BurnRateInfo("Soccer", 10, "High"),
            BurnRateInfo("Rowing", 8, "Medium-High"),
            BurnRateInfo("Martial Arts", 9, "High"),
            BurnRateInfo("CrossFit", 12, "Very High"),
            BurnRateInfo("Skiing", 7, "Medium"),
            BurnRateInfo("Snowboarding", 7, "Medium"),
            BurnRateInfo("Other", 5, "Medium")
        )
    }

    /**
     * Adapter for the burn rate RecyclerView
     */
    inner class BurnRateAdapter(private val burnRates: List<BurnRateInfo>) :
        RecyclerView.Adapter<BurnRateAdapter.BurnRateViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BurnRateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_burn_rate_info, parent, false)
            return BurnRateViewHolder(view)
        }

        override fun onBindViewHolder(holder: BurnRateViewHolder, position: Int) {
            val burnRate = burnRates[position]
            holder.bind(burnRate)
        }

        override fun getItemCount(): Int = burnRates.size

        inner class BurnRateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val textExerciseType: TextView = itemView.findViewById(R.id.textExerciseType)
            private val textBurnRate: TextView = itemView.findViewById(R.id.textBurnRate)
            private val textIntensity: TextView = itemView.findViewById(R.id.textIntensity)

            fun bind(burnRateInfo: BurnRateInfo) {
                textExerciseType.text = burnRateInfo.exerciseType
                textBurnRate.text = getString(R.string.burn_rate_value, burnRateInfo.burnRate)
                textIntensity.text = burnRateInfo.intensityLevel
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}