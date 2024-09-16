package com.sportradar.interview;

import com.sportradar.interview.model.Match;
import com.sportradar.interview.model.Team;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ScoreBoardTest {

    private ScoreBoard scoreBoard = new ScoreBoard();

    @Nested
    class StartNewGameTests {

        @Test
        void startsNewMatch() {
            Match match = scoreBoard.startNewGame("HOME", "AWAY");

            assertNotNull(match);
            assertEquals(0, match.id());
            assertTeam("HOME", 0, match.homeTeam());
            assertTeam("AWAY", 0, match.awayTeam());
        }

        private void assertTeam(String name, int score, Team actual) {
            assertEquals(name, actual.name());
            assertEquals(score, actual.score());
        }

    }

}
