package piotr.kedra.adhoc.ahp.entity.ahpdata;

public class Objective {

    private String name;
    private int index;

    public Objective(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
