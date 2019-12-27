package piotr.kedra.adhoc.ahp.entity.ahpdata;

public class Eigenvector {

    private String name;
    private double[] eigenvector;

    public Eigenvector(String name, double[] eigenvector) {
        this.name = name;
        this.eigenvector = eigenvector;
    }

    public String getName() {
        return name;
    }

    public double[] getEigenvector() {
        return eigenvector;
    }
}
