package com.sportradar.interview.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TeamTest {

    @ParameterizedTest
    @NullAndEmptySource
    void createsTeamWithInvalidName(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Team(name, 0));
    }

    @Test
    void createsTeamWithInvalidScore() {
        assertThrows(IllegalArgumentException.class, () -> new Team("NAME", -1));
    }

}
