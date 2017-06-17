package mprog.nl.a10973710.dartsworld;

/**
 * Created by Roshan Mahes on 9-6-2017.
 */

class Player {

    String born; int champ; String country; int currPos; String darts; String fullName;
    float highAvg; int majors; String money; String nickName; int nineDarts;
    int nineDartsTelevised; int prevPos; String twitter; String walkOn;

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

    String getFullName() {
        return fullName;
    }
}
