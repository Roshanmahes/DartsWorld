package mprog.nl.a10973710.dartsworld;

/**
 * Created by Roshan Mahes on 9-6-2017.
 */

class Player {

    String born;
    int champ;
    String country;
    int currPos;
    String darts;
    String fullName;
    float highAvg;
    int majors;
    String money;
    String nickName;
    int nineDarts;
    int nineDartsTelevised;
    int prevPos;
    String twitter;
    String walkOn;

    // Default constructor for FireBase
    public Player() {}

    String getFullName() { return fullName; }

    int getCurrPos() { return currPos; }
    int getPrevPos() { return prevPos; }
}
