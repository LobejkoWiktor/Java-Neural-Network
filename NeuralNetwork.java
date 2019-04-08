package example;

public class NeuralNetwork {

    private int inputNodes;
    private int hiddenNodes;
    private int outputNodes;
    private Matrix weights_ih;
    private Matrix weights_ho;
    private Matrix bias_h;
    private Matrix bias_o;
    private double learningRate;

    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes) {
        this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;

        this.weights_ih = new Matrix(hiddenNodes, inputNodes);
        this.weights_ho = new Matrix(outputNodes, hiddenNodes);
        this.weights_ih.randomize();
        this.weights_ho.randomize();

        this.bias_h = new Matrix(hiddenNodes, 1);
        this.bias_o = new Matrix(outputNodes, 1);
        this.bias_h.randomize();
        this.bias_o.randomize();
        this.learningRate = 0.01;

    }

    public double[] feedforward(double[] inputArray) {
        Matrix inputs = Matrix.fromArray(inputArray);
        Matrix hidden = Matrix.multiply(this.weights_ih, inputs);
        hidden.add(this.bias_h);

        hidden.sigmoid();

        Matrix output = Matrix.multiply(this.weights_ho, hidden);
        output.add(this.bias_o);
        output.sigmoid();

        return output.toArray();
    }

    public void train(double[] inputArray, double[] targetArray) {
        Matrix inputs = Matrix.fromArray(inputArray);
        Matrix hidden = Matrix.multiply(this.weights_ih, inputs);
        hidden.add(this.bias_h);
        hidden.sigmoid();

        Matrix outputs = Matrix.multiply(this.weights_ho, hidden);
        outputs.add(this.bias_o);
        outputs.sigmoid();

        Matrix targets = Matrix.fromArray(targetArray);

        // Errors = targets - outputs
        Matrix outputErrors = Matrix.subtract(targets, outputs);

        // Gradients
        Matrix gradients = Matrix.deltaSigmoid(outputs);
        gradients.multiply(outputErrors);
        gradients.multiply(this.learningRate);

        Matrix hidden_transposed = Matrix.transpose(hidden);
        Matrix weights_ho_deltas = Matrix.multiply(gradients, hidden_transposed);

        // Change the weights accordingly
        this.weights_ho.add(weights_ho_deltas);
        // Change bias
        this.bias_o.add(gradients);

        // Hidden layer errors
        Matrix weights_ho_transpoed = Matrix.transpose(this.weights_ho);
        Matrix hiddenErrors = Matrix.multiply(weights_ho_transpoed, outputErrors);

        // Hidden Gradients
        Matrix hidden_gradient = Matrix.deltaSigmoid(hidden);
        hidden_gradient.multiply(hiddenErrors);
        hidden_gradient.multiply(this.learningRate);

        Matrix inputs_transposed = Matrix.transpose(inputs);
        Matrix weights_ih_deltas = Matrix.multiply(hidden_gradient, inputs_transposed);

        // Change weights accordingly
        this.weights_ih.add(weights_ih_deltas);
        this.bias_h.add(hidden_gradient);
    }

}
