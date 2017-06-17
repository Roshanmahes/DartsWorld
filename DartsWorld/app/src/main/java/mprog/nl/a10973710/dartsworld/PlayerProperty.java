package mprog.nl.a10973710.dartsworld;

/**
 * Created by Roshan Mahes on 12-6-2017.
 */

public class PlayerProperty {
    private String id;
    private String value;

    public PlayerProperty(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
