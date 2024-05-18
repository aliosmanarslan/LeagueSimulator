package com.aliosmanarslan.leaguesimulator.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aliosmanarslan.leaguesimulator.R
import com.aliosmanarslan.leaguesimulator.databinding.ActivityMainBinding
import com.aliosmanarslan.leaguesimulator.ui.main.adapter.LeagueTableAdapter
import com.aliosmanarslan.leaguesimulator.ui.main.adapter.MatchAdapter
import com.aliosmanarslan.leaguesimulator.ui.main.adapter.StrengthAdapter
import com.aliosmanarslan.leaguesimulator.utils.Cons.playedMatch


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: LeagueViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var matchRecyclerView: RecyclerView
    private lateinit var strengthRecyclerView: RecyclerView
    private lateinit var leagueAdapter: LeagueTableAdapter
    private lateinit var matchAdapter: MatchAdapter
    private lateinit var strengthAdapter: StrengthAdapter
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(LeagueViewModel::class.java)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        leagueAdapter = LeagueTableAdapter(viewModel.leagueTable.value ?: listOf())
        recyclerView.adapter = leagueAdapter

        matchRecyclerView = findViewById(R.id.matchRecyclerView)
        matchRecyclerView.layoutManager = LinearLayoutManager(this)
        matchAdapter = MatchAdapter(viewModel.matches.value ?: listOf())
        matchRecyclerView.adapter = matchAdapter

        strengthRecyclerView = findViewById(R.id.strengthRecyclerView)
        strengthRecyclerView.layoutManager = LinearLayoutManager(this)
        strengthAdapter = StrengthAdapter(viewModel.leagueTable.value ?: listOf())
        strengthRecyclerView.adapter = strengthAdapter

        val playedMatchText: TextView = findViewById(R.id.weekPredictions)
        val allSimulateButton: Button = findViewById(R.id.allSimulateButton)
        val simulateButton: Button = findViewById(R.id.simulateButton)
        allSimulateButton.setOnClickListener {
            for (i in playedMatch..36) {
                viewModel.simulateMatches()
                playedMatchText.text = "$playedMatch th Week predictions of championships"
            }
            allSimulateButton.isClickable = false
            val color = Color.GRAY
            it.setBackgroundColor(color)
            simulateButton.setBackgroundColor(color)
        }

        simulateButton.setOnClickListener {
            if (allSimulateButton.isClickable) {
                viewModel.simulateMatches()
                playedMatchText.text = "$playedMatch th Week predictions of championships"
            }
        }

        viewModel.leagueTable.observe(this) {
            leagueAdapter.updateData(it)
            strengthAdapter.updateData(it)
        }

        viewModel.matches.observe(this) {
            matchAdapter.updateData(it)
        }
    }
}