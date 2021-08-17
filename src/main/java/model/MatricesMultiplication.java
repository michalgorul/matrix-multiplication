package model;

import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.ejml.simple.SimpleMatrix;
import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Random;

public class MatricesMultiplication {

    private double[][] firstArray;
    private double[][] secondArray;
    private double[][] resultNoThreads;
    private double[][] resultWithThreads;
    private final int rowsFirst;
    private final int columnsFirst;
    private final int rowsSecond;
    private final int columnsSecond;

    //  EJML
    private final SimpleMatrix firstEjml;
    private final SimpleMatrix secondEjml;
    private SimpleMatrix resultEjml;
    //  Apache
    private final RealMatrix firstApache;
    private final RealMatrix  secondApache;
    private RealMatrix  resultApache;
    //  ND4J
    private final INDArray firstNd4j;
    private final INDArray secondNd4j;
    private INDArray resultNd4j;
    //  LA4J
    private final Matrix firstLa4j;
    private final Matrix secondLa4j;
    private Matrix resultLa4j;
    //  Colt
    private final DoubleMatrix2D firstColt;
    private final DoubleMatrix2D secondColt;
    private DoubleMatrix2D resultColt;
    DoubleFactory2D doubleFactory2D = DoubleFactory2D.dense;

    public MatricesMultiplication(int rowsFirst, int columnsFirst, int min, int max, int rowsSecond, int columnsSecond) {
        this.rowsFirst = rowsFirst;
        this.columnsFirst = columnsFirst;
        this.rowsSecond = rowsSecond;
        this.columnsSecond = columnsSecond;
        initializeArrays(min, max);
        this.firstEjml = new SimpleMatrix(this.firstArray);
        this.firstNd4j = Nd4j.create(this.firstArray);
        this.firstApache = new Array2DRowRealMatrix(this.firstArray);
        this.firstLa4j = new Basic2DMatrix(this.firstArray);
        this.firstColt = doubleFactory2D.make(this.firstArray);
        this.secondEjml = new SimpleMatrix(this.secondArray);
        this.secondNd4j = Nd4j.create(this.secondArray);
        this.secondApache = new Array2DRowRealMatrix(this.secondArray);
        this.secondLa4j = new Basic2DMatrix(this.secondArray);
        this.secondColt = doubleFactory2D.make(this.secondArray);
    }

    public MatricesMultiplication(double[][] first, double[][] second) {

        this.firstArray = first;
        this.secondArray = second;
        this.rowsFirst = first.length;
        this.columnsFirst = first[0].length;
        this.rowsSecond = second.length;
        this.columnsSecond = second[0].length;
        this.firstEjml = new SimpleMatrix(first);
        this.firstNd4j = Nd4j.create(first);
        this.firstApache = new Array2DRowRealMatrix(first);
        this.firstLa4j = new Basic2DMatrix(first);
        this.firstColt = doubleFactory2D.make(first);
        this.secondEjml = new SimpleMatrix(second);
        this.secondNd4j = Nd4j.create(second);
        this.secondApache = new Array2DRowRealMatrix(second);
        this.secondLa4j = new Basic2DMatrix(second);
        this.secondColt = doubleFactory2D.make(second);
    }

    public double[][] getFirstArray() {
        return firstArray;
    }

    public double[][] getSecondArray() {
        return secondArray;
    }

    public double[][] getResultEjml() {
        double[][] array = new double[resultEjml.numRows()][resultEjml.numCols()];
        for (int r = 0; r < resultEjml.numRows(); r++) {
            for (int c = 0; c < resultEjml.numCols(); c++) {
                array[r][c] = resultEjml.get(r, c);
            }
        }
        return array;
    }

    public double[][] getResultApache() {
        return resultApache.getData();
    }

    public double[][] getResultNd4j() {
        return resultNd4j.toDoubleMatrix();
    }

    public double[][] getResultLa4j() {
        double[][] array = new double[resultLa4j.rows()][resultLa4j.columns()];
        for (int r = 0; r < resultLa4j.rows(); r++) {
            for (int c = 0; c < resultLa4j.columns(); c++) {
                array[r][c] = resultLa4j.get(r, c);
            }
        }
        return array;
    }

    public double[][] getResultColt() {
        return resultColt.toArray();
    }

    public void setFirstArray(double[][] firstArray) {
        this.firstArray = firstArray;
    }

    public void setSecondArray(double[][] secondArray) {
        this.secondArray = secondArray;
    }

    private void initializeArrays(int min, int max) {
        Random random = new Random();

        this.firstArray = new double[this.rowsFirst][this.columnsFirst];
        this.secondArray = new double[this.rowsSecond][this.columnsSecond];

        for (int i = 0; i < this.rowsFirst; i++) {
            this.firstArray[i] = random.doubles(this.columnsFirst, min, max).toArray();
        }
        for (int i = 0; i < this.rowsSecond; i++) {
            this.secondArray[i] = random.doubles(this.columnsSecond, min, max).toArray();
        }
    }

    public void ejmlMultiplication(){
        this.resultEjml = this.firstEjml.mult(this.secondEjml);
    }

    public void nd4jMultiplication(){
        this.resultNd4j = this.firstNd4j.mmul(this.secondNd4j);
    }

    public void apacheMultiplication(){
        this.resultApache = this.firstApache.multiply(this.secondApache);
    }

    public void la4jMultiplication(){
        this.resultLa4j = this.firstLa4j.multiply(this.secondLa4j);
    }

    public void coltMultiplication(){
        Algebra algebra = new Algebra();
        this.resultColt = algebra.mult(this.firstColt, this.secondColt);
    }

    public static double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }

        return result;
    }

    static double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }


    public void multiplyNoThreads() {
        this.resultNoThreads = new double[firstArray.length][secondArray[0].length];

        for (int row = 0; row < resultNoThreads.length; row++) {
            for (int col = 0; col < resultNoThreads[row].length; col++) {
                resultNoThreads[row][col] = multiplyMatricesCell(firstArray, secondArray, row, col);
            }
        }
    }

    public void multiplyWithThreads(int numThreads) {
        this.resultWithThreads = new double[firstArray.length][secondArray[0].length];

        resultNoThreads = ParallelThreads.multiply(firstArray, secondArray, numThreads);

    }
}
