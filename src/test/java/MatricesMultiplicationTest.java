import model.MatricesMultiplication;
import model.ParallelThreads;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatricesMultiplicationTest {

    MatricesMultiplication multiplication;
    private final double[][] firstMatrixSquare =
            new double[][]{
                    new double[]{1d, 5d, 3d},
                    new double[]{2d, 3d, 4d},
                    new double[]{1d, 7d, 2d}
            };
    private final double[][] secondMatrixSquare = new double[][]{
            new double[]{1d, 2d, 4d},
            new double[]{5d, 2d, 5d},
            new double[]{5d, 2d, 2d}
    };

    private final double[][] productSquare = new double[][]{
            new double[]{41d, 18d, 35d},
            new double[]{37d, 18d, 31d},
            new double[]{46d, 20d, 43d}
    };

    private final double[][] firstMatrixNotSquare =
            new double[][]{
                    new double[]{1d, 5d, 3d},
                    new double[]{2d, 3d, 4d}
            };
    private final double[][] secondMatrixNotSquare = new double[][]{
            new double[]{1d, 2d},
            new double[]{5d, 2d},
            new double[]{5d, 2d}
    };

    private final double[][] productNotSquare = new double[][]{
            new double[]{41d, 18d},
            new double[]{37d, 18d}
    };

    @BeforeEach
    public void init(){
        System.out.println("BeforeEach init() method called");
        this.multiplication = new MatricesMultiplication(this.firstMatrixSquare, this.secondMatrixSquare);
    }

    @Test
    void ejmlMultiplication() {
        multiplication.ejmlMultiplication();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(multiplication.getResultEjml()[i][j], this.productSquare[i][j], 0.00001);
            }
        }
    }

    @Test
    void nd4jMultiplication() {
        multiplication.nd4jMultiplication();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(multiplication.getResultNd4j()[i][j], this.productSquare[i][j], 0.00001);
            }
        }
    }

    @Test
    void apacheMultiplication() {
        multiplication.apacheMultiplication();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(multiplication.getResultApache()[i][j], this.productSquare[i][j], 0.00001);
            }
        }
    }

    @Test
    void la4jMultiplication() {
        multiplication.la4jMultiplication();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(multiplication.getResultLa4j()[i][j], this.productSquare[i][j], 0.00001);
            }
        }
    }

    @Test
    void coltMultiplication() {
        multiplication.coltMultiplication();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(multiplication.getResultColt()[i][j], this.productSquare[i][j], 0.00001);
            }
        }
    }

    @Test
    void multiplyNoThreads() {
        double[][] resultSquare = MatricesMultiplication.multiplyMatrices(firstMatrixSquare, secondMatrixSquare);

        for (int i = 0; i < resultSquare.length; i++) {
            for (int j = 0; j < resultSquare[0].length; j++) {
                assertEquals(resultSquare[i][j], this.productSquare[i][j], 0.00001);
            }
        }

        double[][] resultNotSquare = MatricesMultiplication.multiplyMatrices(firstMatrixNotSquare, secondMatrixNotSquare);

        for (int i = 0; i < resultNotSquare.length; i++) {
            for (int j = 0; j < resultNotSquare[0].length; j++) {
                assertEquals(resultNotSquare[i][j], this.productNotSquare[i][j], 0.00001);
            }
        }
    }

    @Test
    void multiplyWithThreads() {
        double[][] resultSquare = ParallelThreads.multiply(firstMatrixSquare, secondMatrixSquare, 8);

        for (int i = 0; i < resultSquare.length; i++) {
            for (int j = 0; j < resultSquare[0].length; j++) {
                assertEquals(resultSquare[i][j], this.productSquare[i][j], 0.00001);
            }
        }

        double[][] resultNotSquare = ParallelThreads.multiply(firstMatrixNotSquare, secondMatrixNotSquare, 8);

        for (int i = 0; i < resultNotSquare.length; i++) {
            for (int j = 0; j < resultNotSquare[0].length; j++) {
                assertEquals(resultNotSquare[i][j], this.productNotSquare[i][j], 0.00001);
            }
        }
    }
}