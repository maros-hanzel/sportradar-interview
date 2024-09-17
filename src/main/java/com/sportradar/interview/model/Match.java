package com.sportradar.interview.model;

import java.util.Optional;

public record Match(int id, Team homeTeam, Team awayTeam) {

    public Match {
        assertValidTeam(homeTeam, "Home team cannot be null");
        assertValidTeam(awayTeam, "Away team cannot be null");
        assertTeamNamesAreDifferent(homeTeam.name(), awayTeam.name());
    }

    private void assertValidTeam(Team team, String message) {
        if (null == team) {
            throw new IllegalArgumentException(message);
        }
    }

    private void assertTeamNamesAreDifferent(String homeTeam, String awayTeam) {
        if (homeTeam.equalsIgnoreCase(awayTeam)) {
            throw new IllegalArgumentException("Match teams cannot be the same");
        }
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {

        private final int id;
        private Team homeTeam;
        private Team awayTeam;

        public Builder(int id) {
            this.id = id;
        }

        private Builder(Match match) {
            this(match.id);
            this.homeTeam = match.homeTeam;
            this.awayTeam = match.awayTeam;
        }

        public Builder homeTeam(String homeTeam) {
            Team team = new Team.Builder()
                .name(homeTeam)
                .build();
            return homeTeam(team);
        }

        public Builder homeTeam(Team homeTeam) {
            this.homeTeam = homeTeam;
            return this;
        }

        public Builder awayTeam(String awayTeam) {
            Team team = new Team.Builder()
                .name(awayTeam)
                .build();
            return awayTeam(team);
        }

        public Builder awayTeam(Team awayTeam) {
            this.awayTeam = awayTeam;
            return this;
        }

        public Builder homeTeamScore(int score) {
            Optional.ofNullable(homeTeam)
                .map(team -> team.toBuilder().score(score).build())
                .ifPresentOrElse(
                    this::homeTeam,
                    () -> {throw new IllegalStateException("Home team must be initialized to update score");}
                );
            return this;
        }

        public Builder awayTeamScore(int score) {
            Optional.ofNullable(awayTeam)
                .map(team -> team.toBuilder().score(score).build())
                .ifPresentOrElse(
                    this::awayTeam,
                    () -> {throw new IllegalStateException("Away team must be initialized to update score");}
                );
            return this;
        }

        public Match build() {
            return new Match(id, homeTeam, awayTeam);
        }

    }

}
