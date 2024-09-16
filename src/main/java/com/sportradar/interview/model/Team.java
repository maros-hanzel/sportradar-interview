package com.sportradar.interview.model;

public record Team(String name, int score) {

    public Team {
        assertValidName(name);
        assertValidScore(score);
    }

    private void assertValidName(String name) {
        if (null == name || name.isBlank()) {
            throw new IllegalArgumentException("Team name cannot be blank");
        }
    }

    private void assertValidScore(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("Team score cannot be negative");
        }
    }

    public static class Builder {

        private String name;
        private int score = 0;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder score(int score) {
            this.score = score;
            return this;
        }

        public Team build() {
            return new Team(name, score);
        }

    }

}
