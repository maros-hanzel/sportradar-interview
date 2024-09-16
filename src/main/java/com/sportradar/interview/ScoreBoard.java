package com.sportradar.interview;

import com.sportradar.interview.model.Match;
import com.sportradar.interview.model.Team;
import com.sportradar.interview.repository.MatchRepository;

import java.util.List;

public class ScoreBoard {

    private final MatchRepository matchRepository;

    public ScoreBoard(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Match startNewGame(String homeTeam, String awayTeam) {
        Team home = new Team(homeTeam, 0);
        Team away = new Team(awayTeam, 0);
        return matchRepository.save(home, away);
    }

    public void updateScore(int id, int homeScore, int awayScore) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public List<Match> getSummary() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
