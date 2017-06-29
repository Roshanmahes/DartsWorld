/*
 * Created by Roshan Mahes on 19-6-2017.
 */

package mprog.nl.a10973710.dartsworld;

import java.util.HashMap;

class Tournament {
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

    HashMap<String, String> getChamps() {
        return champs;
    }

    String getCountry() {
        return country;
    }

    String getDefendingChamp() {
        return defendingChamp;
    }

    int getEstablished() {
        return established;
    }

    String getFormat() {
        return format;
    }

    String getLocation() {
        return location;
    }

    String getLogo() {
        return logo;
    }

    String getPrizeMoney() {
        return prizeMoney;
    }

    String getSponsor() {
        return sponsor;
    }

    String getVenue() {
        return venue;
    }
}
