package example;

public class Data {

    private double[] inputs;
    private double[] targets;

    public Data(double[] inputs, double[] targets) {
        this.inputs = inputs;
        this.targets = targets;
    }

    public double[] getInputs() {
        return inputs;
    }

    public double[] getTargets() {
        return targets;
    }
}
