package mprog.nl.a10973710.dartsworld;

/**
 * Created by Roshan Mahes on 15-6-2017.
 */

class Match {
    private String homeScore;
    private String awayScore;
    private String homeTeam;
    private String awayTeam;
    private String startTime;

    Match(String homeScore, String awayScore, String homeTeam, String awayTeam, String startTime) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = startTime;
    }

    String getHomeScore() { return homeScore; }

    String getAwayScore() { return awayScore; }

    String getHomeTeam() { return homeTeam; }

    String getAwayTeam() { return awayTeam; }

    String getStartTime() { return startTime; }
}
