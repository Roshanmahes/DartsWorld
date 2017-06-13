package mprog.nl.a10973710.dartsworld;

/**
 * Created by Gebruiker on 9-6-2017.
 */

public class Player {

    public String born;
    public int champ;
    public String country;
    public int currPos;
    public String darts;
    public String fullName;
    public float highAvg;
    public int majors;
    public String money;
    public String nickName;
    public int nineDarts;
    public int nineDartsTelevised;
    public int prevPos;
    public String twitter;
    public String walkOn;

    // Default constructor for Firebase
    public Player() {}

    public Player(String born, int champ, String country, int currPos, String darts, String fullName,
                  float highAvg, int majors, String money, String nickName, int nineDarts,
                  int nineDartsTelevised, int prevPos, String twitter, String walkOn){
        this.born = born;
        this.champ = champ;
        this.country = country;
        this.currPos = currPos;
        this.darts = darts;
        this.fullName = fullName;
        this.highAvg = highAvg;
        this.majors = majors;
        this.money = money;
        this.nickName = nickName;
        this.nineDarts = nineDarts;
        this.nineDartsTelevised = nineDartsTelevised;
        this.prevPos = prevPos;
        this.twitter = twitter;
        this.walkOn = walkOn;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
