package com.sportradar.interview.repository;

import com.sportradar.interview.model.Match;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatchRepositoryTest {

    private final MatchRepository matchRepository = new MatchRepository();

    @Test
    void savesNullMatch() {
        assertThrows(IllegalArgumentException.class, () -> matchRepository.save(null));
    }

    @Test
    void getAllMatchesListIsUnmodifiable() {
        Match matchToAdd = new Match.Builder(1)
            .homeTeam("OTHER_HOME")
            .awayTeam("OTHER_AWAY")
            .build();
        matchRepository.save("HOME", "AWAY");
        Collection<Match> matches = matchRepository.getAll();
        assertEquals(1, matches.size());

        assertThrows(UnsupportedOperationException.class, () -> matches.add(matchToAdd));
    }

}
