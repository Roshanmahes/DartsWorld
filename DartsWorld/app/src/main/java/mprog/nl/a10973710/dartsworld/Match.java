/*
 * Created by Roshan Mahes on 15-6-2017.
 */

package mprog.nl.a10973710.dartsworld;

class Match {
    private String homeScore;
    private String awayScore;
    private String homeTeam;
    private String awayTeam;

    Match(String homeScore, String awayScore, String homeTeam, String awayTeam) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    String getHomeScore() { return homeScore; }

    String getAwayScore() { return awayScore; }

    String getHomeTeam() { return homeTeam; }

    String getAwayTeam() { return awayTeam; }
}
