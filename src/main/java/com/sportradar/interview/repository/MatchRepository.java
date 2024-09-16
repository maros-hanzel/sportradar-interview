package com.sportradar.interview.repository;

import com.sportradar.interview.model.Match;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class MatchRepository {

    private final AtomicInteger idCounter = new AtomicInteger(0);
    private final Map<Integer, Match> matches = new ConcurrentHashMap<>();
    private final Supplier<Integer> idSupplier = idCounter::getAndIncrement;

    public Match save(String homeTeam, String awayTeam) {
        matches.values().stream()
            .filter(match -> teamAlreadyStored(match, homeTeam, awayTeam))
            .findFirst()
            .ifPresent(match -> {
                throw new IllegalStateException(String.format(
                    "At least one of the teams %s/%s already stored in match %s",
                    homeTeam,
                    awayTeam,
                    match
                ));
            });

        Match match = new Match.Builder()
            .id(idSupplier.get())
            .homeTeam(homeTeam)
            .awayTeam(awayTeam)
            .build();
        matches.put(match.id(), match);
        return match;
    }

    private boolean teamAlreadyStored(Match match, String homeTeam, String awayTeam) {
        return match.homeTeam().name().equalsIgnoreCase(homeTeam)
            || match.homeTeam().name().equalsIgnoreCase(awayTeam)
            || match.awayTeam().name().equalsIgnoreCase(homeTeam)
            || match.awayTeam().name().equalsIgnoreCase(awayTeam);
    }

}
