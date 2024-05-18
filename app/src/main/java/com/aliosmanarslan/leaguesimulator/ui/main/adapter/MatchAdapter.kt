package com.aliosmanarslan.leaguesimulator.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aliosmanarslan.leaguesimulator.R
import com.aliosmanarslan.leaguesimulator.data.Match

class MatchAdapter(private var matches: List<Match>) :
    RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.match_item, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]
        holder.bind(match)
    }

    override fun getItemCount(): Int = matches.size

    fun updateData(newMatches: List<Match>) {
        matches = newMatches
        notifyDataSetChanged()
    }

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val matchInfo: TextView = itemView.findViewById(R.id.matchInfo)

        fun bind(match: Match) {
            matchInfo.text =
                "${match.team1.name} ${match.team1Score} - ${match.team2Score} ${match.team2.name}"
        }
    }
}