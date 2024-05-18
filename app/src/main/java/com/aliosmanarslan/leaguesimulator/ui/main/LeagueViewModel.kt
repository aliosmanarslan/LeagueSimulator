package com.aliosmanarslan.leaguesimulator.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aliosmanarslan.leaguesimulator.data.Match
import com.aliosmanarslan.leaguesimulator.data.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class LeagueViewModel : ViewModel() {


    suspend fun fetchTopFourTeams(): List<String> {
        return withContext(Dispatchers.IO) {
            val doc = Jsoup.connect("https://www.premierleague.com/tables").get()
            val teams = doc.select(".league-table__team-name.league-table__team-name--long.long")
                .map { it.text() }
                .take(4)

            teams
        }
    }

    val _leagueTable = MutableLiveData<List<Team>>()
    val leagueTable: LiveData<List<Team>> get() = _leagueTable

    private val _matches = MutableLiveData<List<Match>>()
    val matches: LiveData<List<Match>> get() = _matches

    init {
        _leagueTable.value = listOf(
            Team("Team A", 0),
            Team("Team B", 0),
            Team("Team C", 0),
            Team("Team D", 0)
        )
        CoroutineScope(Dispatchers.IO).launch {
            val topFourTeams = fetchTopFourTeams()
            _leagueTable.postValue(topFourTeams.map { Team(it, (1..5).random()) })
        }
        _matches.value = listOf()
    }


    fun simulateMatches() {
        val teams = _leagueTable.value?.toMutableList() ?: return

        // Her hafta takımların güçlerini rastgele değiştir
        teams.forEach { it.strength = (1..5).random() }

        val matches = mutableListOf<Match>()
        teams.shuffle() // Takımları rastgele karıştır

        while (teams.size > 1) {
            val team1 = teams.removeAt(0)
            val team2 = teams.removeAt(0)
            matches.add(Match(team1, team2))
        }

        // Eğer tek bir takım kalırsa onu atlayabiliriz
        _matches.value = matches

        for (match in matches) {
            val team1Goals = (0..match.team1.strength).random()
            val team2Goals = (0..match.team2.strength).random()
            match.team1Score = team1Goals
            match.team2Score = team2Goals
            updateTeamStats(match.team1, match.team2, team1Goals, team2Goals)
        }

        _leagueTable.value = _leagueTable.value?.sortedByDescending { it.points }
    }

    private fun updateTeamStats(team1: Team, team2: Team, team1Goals: Int, team2Goals: Int) {
        team1.played += 1
        team2.played += 1
        team1.goalsFor += team1Goals
        team1.goalsAgainst += team2Goals
        team2.goalsFor += team2Goals
        team2.goalsAgainst += team1Goals

        when {
            team1Goals > team2Goals -> {
                team1.points += 3
                team1.wins += 1
                team2.losses += 1
            }

            team1Goals < team2Goals -> {
                team2.points += 3
                team2.wins += 1
                team1.losses += 1
            }

            else -> {
                team1.draws += 1
                team2.draws += 1
                team1.points += 1
                team2.points += 1
            }
        }
    }
}