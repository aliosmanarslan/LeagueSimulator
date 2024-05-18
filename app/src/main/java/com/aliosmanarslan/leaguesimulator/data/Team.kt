package com.aliosmanarslan.leaguesimulator.data

data class Team(
    var name: String,
    var strength: Int,
    var points: Int = 0,
    var played: Int = 0,
    var draws: Int = 0,
    var goalsFor: Int = 0,
    var goalsAgainst: Int = 0,
    var wins: Int = 0,
    var losses: Int = 0
)

