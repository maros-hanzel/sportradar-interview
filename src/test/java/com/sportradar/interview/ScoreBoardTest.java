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

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardTest {

    private final MatchRepository matchRepository = new MatchRepository();
    private final ScoreBoard scoreBoard = new ScoreBoard(matchRepository);

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

        @Test
        void updatesScoreOfNotExistingMatch() {
            assertThrows(IllegalArgumentException.class, () -> scoreBoard.updateScore(0, 0, 0));
        }

        @ParameterizedTest
        @CsvSource({ "1,-1", "-1,1", "-1,-1" })
        void updatesMatchWithNegativeScore(int homeTeamScore, int awayTeamScore) {
            scoreBoard.startNewGame("HOME", "AWAY");
            assertThrows(IllegalArgumentException.class, () -> scoreBoard.updateScore(0, homeTeamScore, awayTeamScore));
        }

    }

    @Nested
    class FinishGameTests {

        @Test
        void finishesMatch() {
            scoreBoard.startNewGame("HOME", "AWAY");
            assertTrue(matchRepository.getById(0).isPresent());

            scoreBoard.finishGame(0);

            assertTrue(matchRepository.getById(0).isEmpty());
        }

        @Test
        void finishesNotExistingMatch() {
            assertThrows(IllegalArgumentException.class, () -> scoreBoard.finishGame(0));
        }

    }

    @Nested
    class GetSummaryTests {

        @Test
        void getsSummaryOfGamesWithEqualTotalScore() {
            List<Match> expected = List.of(
                new Match.Builder(1)
                    .homeTeam("TEAM_3")
                    .awayTeam("TEAM_4")
                    .build(),
                new Match.Builder(0)
                    .homeTeam("TEAM_1")
                    .awayTeam("TEAM_2")
                    .build()
            );
            scoreBoard.startNewGame("TEAM_1", "TEAM_2");
            scoreBoard.startNewGame("TEAM_3", "TEAM_4");

            List<Match> actual = scoreBoard.getSummary();

            assertEquals(expected, actual);
        }

        @Test
        void getsSummaryOfGamesWithDifferentTotalScore() {
            List<Match> expected = List.of(
                new Match.Builder(1)
                    .homeTeam("TEAM_3")
                    .homeTeamScore(1)
                    .awayTeam("TEAM_4")
                    .awayTeamScore(2)
                    .build(),
                new Match.Builder(0)
                    .homeTeam("TEAM_1")
                    .awayTeam("TEAM_2")
                    .build()
            );
            scoreBoard.startNewGame("TEAM_1", "TEAM_2");
            scoreBoard.startNewGame("TEAM_3", "TEAM_4");
            scoreBoard.updateScore(1, 1, 2);

            List<Match> actual = scoreBoard.getSummary();

            assertEquals(expected, actual);
        }

        @Test
        void finishedGameIsNotInSummary() {
            List<Match> expected = List.of(
                new Match.Builder(1)
                    .homeTeam("TEAM_3")
                    .awayTeam("TEAM_4")
                    .build()
            );
            scoreBoard.startNewGame("TEAM_1", "TEAM_2");
            scoreBoard.startNewGame("TEAM_3", "TEAM_4");
            assertEquals(2, scoreBoard.getSummary().size());

            scoreBoard.finishGame(0);

            assertEquals(expected, scoreBoard.getSummary());
        }

    }

}
