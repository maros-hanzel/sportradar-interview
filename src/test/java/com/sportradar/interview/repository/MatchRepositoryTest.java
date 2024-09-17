package com.sportradar.interview.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MatchRepositoryTest {

    private final MatchRepository matchRepository = new MatchRepository();

    @Test
    void savesNullMatch() {
        assertThrows(IllegalArgumentException.class, () -> matchRepository.save(null));
    }

}
