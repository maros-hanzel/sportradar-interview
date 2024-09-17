package com.sportradar.interview.repository;

import com.sportradar.interview.model.Match;

import java.util.List;
import java.util.Map;
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
        matches.put(match.id(), match);
        return match;
    }

    public List<Match> getByPredicate(Predicate<Match> predicate) {
        return matches.values().stream().filter(predicate).toList();
    }

}
