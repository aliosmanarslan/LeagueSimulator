package com.aliosmanarslan.leaguesimulator.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aliosmanarslan.leaguesimulator.R
import com.aliosmanarslan.leaguesimulator.data.Team

class StrengthAdapter(private var teams: List<Team>) :
    RecyclerView.Adapter<StrengthAdapter.StrengthViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StrengthViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.team_strength_item, parent, false)
        return StrengthViewHolder(view)
    }

    override fun onBindViewHolder(holder: StrengthViewHolder, position: Int) {
        val team = teams[position]
        holder.bind(team)
    }

    override fun getItemCount(): Int = teams.size

    fun updateData(newTeams: List<Team>) {
        teams = newTeams
        notifyDataSetChanged()
    }

    class StrengthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val teamName: TextView = itemView.findViewById(R.id.teamName)
        private val teamStrength: TextView = itemView.findViewById(R.id.teamStrength)

        fun bind(team: Team) {
            teamName.text = team.name
            teamStrength.text = " x${team.strength}"
        }
    }
}