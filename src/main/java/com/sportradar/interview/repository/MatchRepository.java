package com.sportradar.interview.repository;

import com.sportradar.interview.model.Match;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MatchRepository {

    private final AtomicInteger idCounter = new AtomicInteger(0);
    private final Map<Integer, Match> matches = new ConcurrentHashMap<>();
    private final Supplier<Integer> idSupplier = idCounter::getAndIncrement;

    public Match save(String homeTeam, String awayTeam) {
        Match match = new Match.Builder(idSupplier.get())
            .homeTeam(homeTeam)
            .awayTeam(awayTeam)
            .build();
        return save(match);
    }

    public Match save(Match match) {
        assertMatchNotNull(match);

        matches.put(match.id(), match);
        return match;
    }

    public Optional<Match> getById(int id) {
        return Optional.ofNullable(matches.get(id));
    }

    public Collection<Match> getAll() {
        return Collections.unmodifiableCollection(matches.values());
    }

    public Optional<Match> delete(int id) {
        return Optional.ofNullable(matches.remove(id));
    }

    public List<Match> getByPredicate(Predicate<Match> predicate) {
        return matches.values().stream().filter(predicate).toList();
    }

    private void assertMatchNotNull(Match match) {
        if (null == match) {
            throw new IllegalArgumentException("Match cannot be null");
        }
    }

}
