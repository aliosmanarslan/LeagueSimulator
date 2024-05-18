package com.aliosmanarslan.leaguesimulator.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aliosmanarslan.leaguesimulator.R
import com.aliosmanarslan.leaguesimulator.data.Team
import com.aliosmanarslan.leaguesimulator.utils.Cons

class LeagueTableAdapter(private var teams: List<Team>) :
    RecyclerView.Adapter<LeagueTableAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = teams[position]
        holder.bind(team)
    }

    override fun getItemCount(): Int = teams.size

    fun updateData(newTeams: List<Team>) {
        teams = newTeams
        Cons.playedMatch += 1
        notifyDataSetChanged()
    }

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val teamName: TextView = itemView.findViewById(R.id.teamName)
        private val teamPlayed: TextView = itemView.findViewById(R.id.teamPlayed)
        private val teamDraws: TextView = itemView.findViewById(R.id.teamDraws)
        private val teamGoalsFor: TextView = itemView.findViewById(R.id.teamGoalsFor)
        private val teamGoalsAgainst: TextView = itemView.findViewById(R.id.teamGoalsAgainst)
        private val teamPoints: TextView = itemView.findViewById(R.id.teamPoints)
        private val teamWins: TextView = itemView.findViewById(R.id.teamWins)
        private val teamDifference: TextView = itemView.findViewById(R.id.teamDifference)
        private val teamLosses: TextView = itemView.findViewById(R.id.teamLosses)

        fun bind(team: Team) {
            teamName.text = team.name
            teamPlayed.text = "${team.played}"
            teamWins.text = "${team.wins}"
            teamDraws.text = "${team.draws}"
            teamLosses.text = "${team.losses}"
            teamGoalsFor.text = "${team.goalsFor}"
            teamGoalsAgainst.text = "${team.goalsAgainst}"
            teamDifference.text = "${team.goalsFor - team.goalsAgainst}"
            teamPoints.text = "${team.points}"
        }
    }
}