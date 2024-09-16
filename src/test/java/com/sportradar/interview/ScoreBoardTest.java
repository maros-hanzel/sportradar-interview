package com.sportradar.interview;

import com.sportradar.interview.model.Match;
import com.sportradar.interview.model.Team;
import com.sportradar.interview.repository.MatchRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardTest {

    private ScoreBoard scoreBoard = new ScoreBoard(new MatchRepository());

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

        @ParameterizedTest
        @MethodSource("startNewGameIllegalArgumentArguments")
        void startsMatchWithIllegalNames(String homeTeam, String awayTeam) {
            assertThrows(IllegalArgumentException.class, () -> scoreBoard.startNewGame(homeTeam, awayTeam));
        }

        private static Stream<Arguments> startNewGameIllegalArgumentArguments() {
            return Stream.of(
                Arguments.of("HOME", null),
                Arguments.of(null, "AWAY"),
                Arguments.of(null, null),
                Arguments.of("HOME", ""),
                Arguments.of("", "AWAY"),
                Arguments.of("", "")
            );
        }

        private void assertTeam(String name, int score, Team actual) {
            assertEquals(name, actual.name());
            assertEquals(score, actual.score());
        }

    }

}
