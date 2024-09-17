package com.sportradar.interview;

import com.sportradar.interview.model.Match;
import com.sportradar.interview.repository.MatchRepository;

import java.util.List;

public class ScoreBoard {

    private final MatchRepository matchRepository;

    public ScoreBoard(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Match startNewGame(String homeTeam, String awayTeam) {
        assertTeamsNotPlaying(homeTeam, awayTeam);

        return matchRepository.save(homeTeam, awayTeam);
    }

    public Match updateScore(int id, int homeScore, int awayScore) {
        Match updated = matchRepository.getById(id)
            .map(match -> match.toBuilder()
                .homeTeamScore(homeScore)
                .awayTeamScore(awayScore)
                .build())
            .orElseThrow(() -> new IllegalArgumentException("Match with ID [" + id + "] not found"));
        return matchRepository.save(updated);
    }

    public void finishGame(int id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public List<Match> getSummary() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private void assertTeamsNotPlaying(String homeTeam, String awayTeam) {
        if (!matchRepository.getByPredicate(match -> teamAlreadyPlaying(match, homeTeam, awayTeam)).isEmpty()) {
            throw new IllegalStateException(String.format(
                "At least one of the teams %s/%s is already playing a match", homeTeam, awayTeam
            ));
        }
    }

    private boolean teamAlreadyPlaying(Match match, String homeTeam, String awayTeam) {
        return match.homeTeam().name().equalsIgnoreCase(homeTeam)
            || match.homeTeam().name().equalsIgnoreCase(awayTeam)
            || match.awayTeam().name().equalsIgnoreCase(homeTeam)
            || match.awayTeam().name().equalsIgnoreCase(awayTeam);
    }

}
