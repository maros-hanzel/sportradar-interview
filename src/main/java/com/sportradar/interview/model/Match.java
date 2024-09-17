package com.sportradar.interview.model;

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
            return homeTeam(new Team(homeTeam, 0));
        }

        public Builder homeTeam(Team homeTeam) {
            this.homeTeam = homeTeam;
            return this;
        }

        public Builder awayTeam(String awayTeam) {
            return awayTeam(new Team(awayTeam, 0));
        }

        public Builder awayTeam(Team awayTeam) {
            this.awayTeam = awayTeam;
            return this;
        }

        public Match build() {
            return new Match(id, homeTeam, awayTeam);
        }

    }

}
