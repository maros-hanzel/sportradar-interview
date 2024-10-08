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
            .orElseThrow(() -> notFound(id));
        return matchRepository.save(updated);
    }

    public void finishGame(int id) {
        if (matchRepository.delete(id).isEmpty()) {
            throw notFound(id);
        }
    }

    public List<Match> getSummary() {
        return matchRepository.getAll().stream()
            .sorted(Match.COMPARATOR)
            .toList();
    }

    private IllegalArgumentException notFound(int id) {
        return new IllegalArgumentException("Match with ID [" + id + "] not found");
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
