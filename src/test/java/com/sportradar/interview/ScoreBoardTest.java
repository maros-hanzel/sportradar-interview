package com.sportradar.interview;

import com.sportradar.interview.model.Match;
import com.sportradar.interview.model.Team;
import com.sportradar.interview.repository.MatchRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScoreBoardTest {

    private ScoreBoard scoreBoard = new ScoreBoard(new MatchRepository());

    @Nested
    class StartNewGameTests {

        @Test
        void startsNewMatch() {
            Match expected = new Match.Builder(0)
                .homeTeam(new Team("HOME", 0))
                .awayTeam(new Team("AWAY", 0))
                .build();

            Match match = scoreBoard.startNewGame("HOME", "AWAY");

            assertEquals(expected, match);
        }

        @ParameterizedTest
        @MethodSource("startNewGameIllegalArgumentArguments")
        void startsMatchWithIllegalNames(String homeTeam, String awayTeam) {
            assertThrows(IllegalArgumentException.class, () -> scoreBoard.startNewGame(homeTeam, awayTeam));
        }

        @ParameterizedTest
        @CsvSource({
            "HOME,OTHER_TEAM",
            "home,OTHER_TEAM",
            "OTHER_TEAM,AWAY",
            "OTHER_TEAM,away",
            "AWAY,OTHER_TEAM",
            "away,OTHER_TEAM",
            "OTHER_TEAM,HOME",
            "OTHER_TEAM,home",
            "HOME,AWAY",
            "home,away",
            "AWAY,HOME",
            "away,home",
        })
        void startsMatchWithATeamAlreadyOnScoreBoard(String homeTeam, String awayTeam) {
            scoreBoard.startNewGame("HOME", "AWAY");

            assertThrows(IllegalStateException.class, () -> scoreBoard.startNewGame(homeTeam, awayTeam));
        }

        @Test
        void startMatchWithTwoSameTeams() {
            assertThrows(IllegalArgumentException.class, () -> scoreBoard.startNewGame("TEAM", "TEAM"));
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

    }

    @Nested
    class UpdateScoreTests {

        @Test
        void updatesScore() {
            Match expected = new Match.Builder(0)
                .homeTeam(new Team("HOME", 1))
                .awayTeam(new Team("AWAY", 2))
                .build();

            Match match = scoreBoard.startNewGame("HOME", "AWAY");
            Match updatedMatch = scoreBoard.updateScore(match.id(), 1, 2);

            assertEquals(expected, updatedMatch);
        }

    }

}
