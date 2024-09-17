## Task requirements
You are working in a sports data company, and we would like you to develop a new Live Football World Cup Scoreboard library (or frontend application) that shows all the ongoing matches and their scores.
The scoreboard supports the following operations:
1. Start a new game, assuming initial score 0 – 0 and adding it the scoreboard. This should capture following parameters:
    * Home team
    * Away team
2. Update score. This should receive a pair of absolute scores: home team score and away team score.
3. Finish game currently in progress. This removes a match from the scoreboard.
4. Get a summary of games in progress ordered by their total score. The games with the same
   total score will be returned ordered by the most recently started match in the scoreboard.

## Assumptions
1. A team cannot appear twice on the scoreboard.
2. A match consist of a single team against itself.
3. Team name must not be `null` or blank.
4. Team must not have a negative score.
5. In cases where a match is not found, an `IllegalArgumentException` is thrown.
6. Returned summary should not be modifiable.

## Acceptance criteria
For example, if following matches are started in the specified order and their scores respectively updated:
* Mexico 0 - Canada 5
* Spain 10 - Brazil 2
* Germany 2 - France 2
* Uruguay 6 - Italy 6
* Argentina 3 - Australia 1

The summary should be as follows:
1. Uruguay 6 - Italy 6
2. Spain 10 - Brazil 2
3. Mexico 0 - Canada 5
4. Argentina 3 - Australia 1
5. Germany 2 - France 2

## Additional info
* Models in my solution are immutable to achieve data consistency and thread safety. To modify a model a builder must be
  created from the instance.
* Operations on storage are designed in a thread safe manner.
