package com.sportradar.interview.repository;

import com.sportradar.interview.model.Match;
import com.sportradar.interview.model.Team;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class MatchRepository {

    private final AtomicInteger idCounter = new AtomicInteger(0);
    private final Map<Integer, Match> matches = new ConcurrentHashMap<>();
    private final Supplier<Integer> idSupplier = idCounter::getAndIncrement;

    public Match save(Team home, Team away) {
        Match match = new Match(idSupplier.get(), home, away);
        matches.put(match.id(), match);
        return match;
    }

}
