package model;

import java.util.ArrayList;
import java.util.List;

public class ParallelThreads {

    // creating number of available processors threads and waiting for them to complete then again repeat steps.
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

