# Matrix Multiplication Tester

The goal of this project was to become familiar with the concept of multithreading in java. It was my first encounter with this issue. This application multiplies a matrix of user-specified dimensions using several popular libraries and my own implementation of this problem, and then compares execution times of this multiplication. It is possible to select a specific number of simultaneously running threads to find the most efficient number.

## GUI design

![image](https://user-images.githubusercontent.com/43811151/129688985-f0258c6b-5b0c-450b-a930-5feab1d2c980.png)

## Sample ActionListener class

public class StartListener implements ActionListener {
```java
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (mainPage.getColumnsFirst() != mainPage.getRowsSecond()) {
                    mainPage.showMatricesSizeError();
                } else if (mainPage.getMinValue() >= mainPage.getMaxValue()) {
                    mainPage.showValuesError();
                } else {
                    matricesMultiplication = new MatricesMultiplication(
                            mainPage.getRowsFirst(),
                            mainPage.getColumnsFirst(),
                            mainPage.getMinValue(),
                            mainPage.getMaxValue(),
                            mainPage.getRowsSecond(),
                            mainPage.getColumnsSecond());

                    if (mainPage.getRowsFirst() > 500 && mainPage.getColumnsFirst() > 500
                            && mainPage.getRowsSecond() > 500 && mainPage.getColumnsSecond() > 500){
                        mainPage.showLongerTimeInfo();
                        setTimesMap(matricesMultiplication);
                        setTimesTextFields();
                        mainPage.showDoneInfo();

                    }
                    else{
                        setTimesMap(matricesMultiplication);
                        setTimesTextFields();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
```    

## Class responsible for matrix multiplication using multiple threads

```java
public class ParallelThreads {

    public static double[][] multiply(double[][] matrix1, double[][] matrix2, int numberThreads) {
        double[][] result = new double[matrix1.length][matrix2[0].length];
        List threads = new ArrayList<>();
        int rows = matrix1.length;
        if(rows > numberThreads){
            for (int i = 0; i < rows; i++) {
                MultiplyRow task = new MultiplyRow(result, matrix1, matrix2, i);
                Thread thread = new Thread(task);
                thread.start();
                threads.add(thread);
                if (threads.size() % numberThreads == 0) {
                    waitForThreads(threads);
                }
            }
        }
        else if(rows < numberThreads){
            for (int i = 0; i < rows; i++) {
                MultiplyRow task = new MultiplyRow(result, matrix1, matrix2, i);
                Thread thread = new Thread(task);
                thread.start();
                threads.add(thread);
                waitForThreads(threads);

            }
        }
        return result;
    }

    private static void waitForThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threads.clear();
    }
}
```
