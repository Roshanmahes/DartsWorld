package mprog.nl.a10973710.dartsworld;

/**
 * Created by Roshan Mahes on 15-6-2017.
 */

public class Match {
    private String homeScore;
    private String awayScore;
    private String homeTeam;
    private String awayTeam;
    private String startTime;

    public Match(String homeScore, String awayScore, String homeTeam, String awayTeam, String startTime) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = startTime;
    }

    public String getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(String homeScore) {
        this.homeScore = homeScore;
    }

    public String getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(String awayScore) {
        this.awayScore = awayScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getStartTime() { return startTime; }
}
