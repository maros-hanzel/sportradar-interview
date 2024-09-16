package com.sportradar.interview.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MatchTest {

    @ParameterizedTest
    @MethodSource("createsMatchWithNullTeamArguments")
    void createsMatchWithNullTeam(Team homeTeam, Team awayTeam) {
        assertThrows(IllegalArgumentException.class, () -> new Match(0, homeTeam, awayTeam));
    }

    private static Stream<Arguments> createsMatchWithNullTeamArguments() {
        return Stream.of(
            Arguments.of(null, null),
            Arguments.of(new Team("HOME", 0), null),
            Arguments.of(null, new Team("AWAY", 0))
        );
    }

}
