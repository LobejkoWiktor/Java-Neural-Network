package example;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {



    public static void main(String[] args) {

        List<Data> trainData = new ArrayList<>();
        List<Data> testData = new ArrayList<>();


        byte[] trainLabels;
        byte[] trainImages;
        byte[] testLabels;
        byte[] testImages;
        try {

            Path tempPath1 = Paths.get("res/train-labels-idx1-ubyte");
            trainLabels = Files.readAllBytes(tempPath1);
            ByteBuffer bufferLabels = ByteBuffer.wrap(trainLabels);
            int magicLabels = bufferLabels.getInt();
            int numberOfItems = bufferLabels.getInt();

            Path tempPath = Paths.get("res/train-images-idx3-ubyte");
            trainImages = Files.readAllBytes(tempPath);
            ByteBuffer bufferImages = ByteBuffer.wrap(trainImages);
            int magicImages = bufferImages.getInt();
            int numberOfImageItems = bufferImages.getInt();
            int rows = bufferImages.getInt();
            int cols = bufferImages.getInt();

            for(int i = 0; i < numberOfItems /*numberOfItems*/; i++) {
                int t = bufferLabels.get();
                double[] target = createTargets(t, 10);
                //double[] target = new double[]{bufferLabels.get()};
                double[] inputs = new double[rows*cols];
                for(int j = 0; j < inputs.length; j++) {
                    inputs[j] = bufferImages.get();
                }
                Data tobj = new Data(inputs, target);
                trainData.add(tobj);
            }

            tempPath = Paths.get("res/t10k-labels-idx1-ubyte");
            testLabels = Files.readAllBytes(tempPath);
            ByteBuffer testLabelBuffer = ByteBuffer.wrap(testLabels);
            int testMagicLabels = testLabelBuffer.getInt();
            int numberOfTestLabels = testLabelBuffer.getInt();

            tempPath = Paths.get("res/t10k-images-idx3-ubyte");
            testImages = Files.readAllBytes(tempPath);
            ByteBuffer testImageBuffer = ByteBuffer.wrap(testImages);
            int testMagicImages = testImageBuffer.getInt();
            int numberOfTestImages = testImageBuffer.getInt();
            int testRows = testImageBuffer.getInt();
            int testCols = testImageBuffer.getInt();

            for(int i = 0; i < numberOfTestImages /*numberOfItems*/; i++) {
                //int t = testLabelBuffer.get();
                //double[] target = createTargets(t, 10);

                double[] target = new double[]{testLabelBuffer.get()};
                double[] inputs = new double[testRows*testCols];
                for(int j = 0; j < inputs.length; j++) {
                    inputs[j] = testImageBuffer.get();
                }
                Data tobj = new Data(inputs, target);
                testData.add(tobj);
            }

            NeuralNetwork neuralNetwork = new NeuralNetwork(784,64,10);

            int len = trainData.size();
            Random randomGenerator = new Random();
            for(int i = 0; i < 400000; i++) {
               int randomInt = randomGenerator.nextInt(len);
                neuralNetwork.train(trainData.get(randomInt).getInputs(), trainData.get(randomInt).getTargets());
            }

            float rightAnswers = 0;

            for(Data testObj : testData) {
                double[] output = neuralNetwork.feedforward(testObj.getInputs());
                double[] answer = testObj.getTargets();
                List<Double> tempList = new ArrayList<>();

                for(double d : output)
                {
                    tempList.add(d);
                }

                double max = Collections.max(tempList);
                int index = tempList.indexOf(max);
                if(index == answer[0]) {
                    rightAnswers++;
                }

            }

            float percentage = rightAnswers / 100f;
            System.out.println(percentage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*

        List<Data> data = new ArrayList<>();

        Data ob1 = new Data(new double[]{0,0},new double[]{0});
        Data ob2 = new Data(new double[]{0,1},new double[]{1});
        Data ob3 = new Data(new double[]{1,0},new double[]{1});
        Data ob4 = new Data(new double[]{1,1},new double[]{0});

        data.add(ob1);
        data.add(ob2);
        data.add(ob3);
        data.add(ob4);

        NeuralNetwork nn = new NeuralNetwork(2,4,1);

        Random random = new Random();

        for(int i = 0; i < 100000; i++) {
            int n = random.nextInt(3);
            example.Data temp = data.get(n);
            nn.train(temp.getInputs(), temp.getTargets());
        }

        for(int i = 0; i < 100; i++) {
            int n = random.nextInt(3);
            example.Data temp = data.get(n);
            double[] output = nn.feedforward(temp.getInputs());
            System.out.println(Arrays.toString(output) + " with expected " + Arrays.toString(temp.getTargets()));
        } */





    }

    public static double[] createTargets(int number, int size) {
        double[] result = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        result[number] = 1;
        return  result;

    }

}
