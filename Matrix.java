package example;

public class Matrix {

    private int rows;
    private int columns;
    private double[][] data;

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        data = new double[rows][columns];
    }

    public static Matrix subtract(Matrix a, Matrix b) {
        if (a.rows != b.rows || a.columns != b.columns) {
            throw new DifferentMatrixSizesException("Matrices must be the same size");
        } else {
            Matrix result = new Matrix(a.rows, a.columns);
            for(int i = 0; i < result.rows; i++) {
                for(int j = 0; j < result.columns; j++) {
                    result.data[i][j] = a.data[i][j] - b.data[i][j];
                }
            }
            return result;
        }
    }

    public static Matrix fromArray(double[] array) {
        Matrix result = new Matrix(array.length, 1);
        for(int i = 0; i < array.length; i++) {
            result.data[i][0] = array[i];
        }
        return result;
    }

    public static Matrix transpose(Matrix matrix) {
        Matrix result = new Matrix(matrix.columns, matrix.rows);
        for(int i = 0; i < matrix.rows; i++) {
            for(int j = 0; j < matrix.columns; j++) {
                result.data[j][i] = matrix.data[i][j];
            }
        }
        return result;
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        if(a.columns != b.rows) {
            //or other way around, or both?
            throw new DifferentMatrixSizesException("Number of columns of matrix 'a' must equal number of rows of matrix 'b'");
        }

        Matrix result = new Matrix(a.rows, b.columns);
        for (int i = 0; i < result.rows; i ++) {
            for(int j = 0; j < result.columns; j++) {
                double sum = 0;
                for (int k = 0; k < a.columns; k++){
                    sum += a.data[i][k] * b.data[k][j];
                }
                result.data[i][j] = sum;
            }
        }
        return result;
    }

    public void multiply(Matrix matrix) {
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.columns; j++) {
                this.data[i][j] *= matrix.data[i][j];
            }
        }
    }

    public void multiply(double n) {
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.columns; j++) {
                this.data[i][j] *= n;
            }
        }
    }

    private static double sigmoidValues(double x) {
        return 1 / (1 +  Math.exp(-x));
    }

    private static double deltaSigmoidValues(double x) {
        return x * (1 - x);
    }

    public void sigmoid() {
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.columns; j++) {
                double val = this.data[i][j];
                this.data[i][j] = sigmoidValues(val);
            }
        }
    }

    public void deltaSigmoid() {
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.columns; j++) {
                double val = this.data[i][j];
                this.data[i][j] = deltaSigmoidValues(val);
            }
        }
    }

    public static Matrix sigmoid(Matrix matrix) {
        Matrix result = new Matrix(matrix.rows, matrix.columns);
        for(int i = 0; i < matrix.rows; i++) {
            for(int j = 0; j < matrix.columns; j++) {
                double val = matrix.data[i][j];
                result.data[i][j] = sigmoidValues(val);
            }
        }

        return result;
    }

    public static Matrix deltaSigmoid(Matrix matrix) {
        Matrix result = new Matrix(matrix.rows, matrix.columns);
        for(int i = 0; i < matrix.rows; i++) {
            for(int j = 0; j < matrix.columns; j++) {
                double val = matrix.data[i][j];
                result.data[i][j] = deltaSigmoidValues(val);
            }
        }

        return result;
    }

    public void add(Matrix matrix){
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.columns; j++) {
                this.data[i][j] += matrix.data[i][j];
            }
        }
    }

    public void add(int n) {
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.columns; j++) {
                this.data[i][j] += n;
            }
        }
    }

    public void randomize() {
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.columns; j++) {
                this.data[i][j] = Math.random() * 2 - 1;
            }
        }
    }

    public double[] toArray() {
        double[] result = new double[this.rows * this.columns];
        int index = 0;
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.columns; j++) {
                result[index] = this.data[i][j];
                index ++;
            }
        }

        return result;
    }

}
