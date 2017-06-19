package mprog.nl.a10973710.dartsworld;

import java.util.HashMap;

/**
 * Created by Gebruiker on 19-6-2017.
 */

public class Tournament {
    private HashMap<String, String> champs;
    private String country;
    private String defendingChamp;
    private int established;
    private String format;
    private String location;
    private String logo;
    private String prizeMoney;
    private String sponsor;
    private String venue;

    public Tournament() {}

    public Tournament(HashMap champs, String country, String defendingChamp, int established,
                      String format, String location, String logo, String prizeMoney,
                      String sponsor, String venue) {
        this.champs = champs;
        this.country = country;
        this.defendingChamp = defendingChamp;
        this.established = established;
        this.format = format;
        this.location = location;
        this.logo = logo;
        this.prizeMoney = prizeMoney;
        this.sponsor = sponsor;
        this.venue = venue;
    }

    public HashMap<String, String> getChamps() {
        return champs;
    }

    public void setChamps(HashMap<String, String> champs) {
        this.champs = champs;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDefendingChamp() {
        return defendingChamp;
    }

    public void setDefendingChamp(String defendingChamp) {
        this.defendingChamp = defendingChamp;
    }

    public int getEstablished() {
        return established;
    }


    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(String prizeMoney) {
        this.prizeMoney = prizeMoney;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
