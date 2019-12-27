package piotr.kedra.adhoc.ahp.entity;

public class IncompleteEquation {

    private double[][] matrixM;
    private double[] vectorR;

    private IncompleteEquation(Builder builder) {
        this.matrixM = builder.matrixM;
        this.vectorR = builder.vectorR;
    }

    public double[][] getMatrixM() {
        return matrixM;
    }

    public double[] getVectorR() {
        return vectorR;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private double[][] matrixM;
        private double[] vectorR;

        public Builder matrixM(double[][] matrixM) {
            this.matrixM = matrixM;
            return this;
        }

        public Builder vectorR(double[] vectorR) {
            this.vectorR = vectorR;
            return this;
        }

        public IncompleteEquation build(){
            return new IncompleteEquation(this);
        }
    }
}
