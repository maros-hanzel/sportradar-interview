package com.sportradar.interview;

import com.sportradar.interview.model.Match;
import com.sportradar.interview.model.Team;
import com.sportradar.interview.repository.MatchRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreBoardAcceptanceTest {

    @Test
    void matches_hasCorrectOrder() {
        ScoreBoard scoreBoard = given6MatchesOnScoreBoard();

        List<Match> summary = scoreBoard.getSummary();

        assertCorrectOrder(summary);
    }

    private void assertCorrectOrder(List<Match> summary) {
        List<Match> expectedResults = List.of(
            match(3, "Uruguay", 6, "Italy", 6),
            match(1, "Spain", 10, "Brazil", 2),
            match(0, "Mexico", 0, "Canada", 5),
            match(4, "Argentina", 3, "Australia", 1),
            match(2, "Germany", 2, "France", 2)
        );

        assertEquals(expectedResults, summary);
    }

    private Match match(int id, String homeTeam, int homeScore, String awayTeam, int awayScore) {
        Team home = new Team(homeTeam, homeScore);
        Team away = new Team(awayTeam, awayScore);
        return new Match(id, home, away);
    }

    private ScoreBoard given6MatchesOnScoreBoard() {
        ScoreBoard scoreBoard = new ScoreBoard(new MatchRepository());
        simulateMatch(scoreBoard, "Mexico", 0, "Canada", 5);
        simulateMatch(scoreBoard, "Spain", 10, "Brazil", 2);
        simulateMatch(scoreBoard, "Germany", 2, "France", 2);
        simulateMatch(scoreBoard, "Uruguay", 6, "Italy", 6);
        simulateMatch(scoreBoard, "Argentina", 3, "Australia", 1);
        return scoreBoard;
    }

    private void simulateMatch(
        ScoreBoard scoreBoard,
        String homeTeam,
        int homeScore,
        String awayTeam,
        int awayScore
    ) {
        Match match = scoreBoard.startNewGame(homeTeam, awayTeam);
        scoreBoard.updateScore(match.id(), homeScore, awayScore);
    }

}
