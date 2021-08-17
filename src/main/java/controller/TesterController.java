
package controller;

import model.MatricesMultiplication;
import view.MainPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class TesterController {

    private final MainPage mainPage;
    private MatricesMultiplication matricesMultiplication;
    private Map<String, Long> times = new HashMap<>();


    public TesterController(MainPage mainPage) {
        this.mainPage = mainPage;

        // Tell the View that when ever the startTest button is clicked to execute
        // the actionPerformed method in the StartListener inner class
        this.mainPage.addStartTestListener(new StartListener());

        // Tell the View that when ever the item in combo box is changed to execute
        // the actionPerformed method in the ItemChangeListener inner class
        this.mainPage.addComboListener(new ItemChangeListener());
    }

    public class StartListener implements ActionListener {

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

    public class ItemChangeListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                try{
                    setTimesTextFields();
                }catch (NullPointerException | IllegalArgumentException ignored){

                }
            }
        }
    }

    private void setTimesMap(MatricesMultiplication matricesMultiplication) {

        Instant startEjml = Instant.now();
        matricesMultiplication.ejmlMultiplication();
        Instant finishEjml = Instant.now();
        long timeElapsedEjml = Duration.between(startEjml, finishEjml).toNanos();
        times.put("EJML", timeElapsedEjml);


        Instant startNd4j = Instant.now();
        matricesMultiplication.nd4jMultiplication();
        Instant finishNd4j = Instant.now();
        long timeElapsedNd4j = Duration.between(startNd4j, finishNd4j).toNanos();
        times.put("ND4J", timeElapsedNd4j);


        Instant startApache = Instant.now();
        matricesMultiplication.apacheMultiplication();
        Instant finishApache = Instant.now();
        long timeElapsedApache = Duration.between(startApache, finishApache).toNanos();
        times.put("Apache", timeElapsedApache);


        Instant startLa4j = Instant.now();
        matricesMultiplication.la4jMultiplication();
        Instant finishLa4j = Instant.now();
        long timeElapsedLa4j = Duration.between(startLa4j, finishLa4j).toNanos();
        times.put("LA4J", timeElapsedLa4j);


        Instant startColt = Instant.now();
        matricesMultiplication.coltMultiplication();
        Instant finishColt = Instant.now();
        long timeElapsedColt = Duration.between(startColt, finishColt).toNanos();
        times.put("Colt", timeElapsedColt);

        Instant startNoThreads = Instant.now();
        matricesMultiplication.multiplyNoThreads();
        Instant finishNoThreads = Instant.now();
        long timeElapsedNoThreads = Duration.between(startNoThreads, finishNoThreads).toNanos();
        times.put("NoThreads", timeElapsedNoThreads);

        Instant startWithThreads = Instant.now();
        matricesMultiplication.multiplyWithThreads(mainPage.getProcessors());
        Instant finishWithThreads = Instant.now();
        long timeElapsedWithThreads = Duration.between(startWithThreads, finishWithThreads).toNanos();
        times.put("WithThreads", timeElapsedWithThreads);
    }

    public void setTimesTextFields() {

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        switch (mainPage.getTimeUnit()) {
            case " ns" -> {
                mainPage.setTextFieldEjml(decimalFormat.format(times.get("EJML")));
                mainPage.setTextFieldNd4j(decimalFormat.format(times.get("ND4J")));
                mainPage.setTextFieldLa4j(decimalFormat.format(times.get("LA4J")));
                mainPage.setTextFieldColt(decimalFormat.format(times.get("Colt")));
                mainPage.setTextFieldApache(decimalFormat.format(times.get("Apache")));
                mainPage.setTextFieldNoThreads(decimalFormat.format(times.get("NoThreads")));
                mainPage.setTextFieldWithThreads(decimalFormat.format(times.get("WithThreads")));
            }
            case " s" -> {
                mainPage.setTextFieldEjml(decimalFormat.format(nanoToSeconds(times.get("EJML"))));
                mainPage.setTextFieldNd4j(decimalFormat.format(nanoToSeconds(times.get("ND4J"))));
                mainPage.setTextFieldLa4j(decimalFormat.format(nanoToSeconds(times.get("LA4J"))));
                mainPage.setTextFieldColt(decimalFormat.format(nanoToSeconds(times.get("Colt"))));
                mainPage.setTextFieldApache(decimalFormat.format(nanoToSeconds(times.get("Apache"))));
                mainPage.setTextFieldNoThreads(decimalFormat.format(nanoToSeconds(times.get("NoThreads"))));
                mainPage.setTextFieldWithThreads(decimalFormat.format(nanoToSeconds(times.get("WithThreads"))));
            }
            case " μs" -> {
                mainPage.setTextFieldEjml(decimalFormat.format(nanoToMicro(times.get("EJML"))));
                mainPage.setTextFieldNd4j(decimalFormat.format(nanoToMicro(times.get("ND4J"))));
                mainPage.setTextFieldLa4j(decimalFormat.format(nanoToMicro(times.get("LA4J"))));
                mainPage.setTextFieldColt(decimalFormat.format(nanoToMicro(times.get("Colt"))));
                mainPage.setTextFieldApache(decimalFormat.format(nanoToMicro(times.get("Apache"))));
                mainPage.setTextFieldNoThreads(decimalFormat.format(nanoToMicro(times.get("NoThreads"))));
                mainPage.setTextFieldWithThreads(decimalFormat.format(nanoToMicro(times.get("WithThreads"))));
            }
            case " ms" -> {
                mainPage.setTextFieldEjml(decimalFormat.format(nanoToMilli(times.get("EJML"))));
                mainPage.setTextFieldNd4j(decimalFormat.format(nanoToMilli(times.get("ND4J"))));
                mainPage.setTextFieldLa4j(decimalFormat.format(nanoToMilli(times.get("LA4J"))));
                mainPage.setTextFieldColt(decimalFormat.format(nanoToMilli(times.get("Colt"))));
                mainPage.setTextFieldApache(decimalFormat.format(nanoToMilli(times.get("Apache"))));
                mainPage.setTextFieldNoThreads(decimalFormat.format(nanoToMilli(times.get("NoThreads"))));
                mainPage.setTextFieldWithThreads(decimalFormat.format(nanoToMilli(times.get("WithThreads"))));
            }
        }
    }

    private double nanoToMilli(long nanos) {
        return (double) nanos / 1000000;

    }

    private double nanoToMicro(long nanos) {
        return (double) nanos / 1000;
    }

    private double nanoToSeconds(long nanos) {
        return (double) nanos / 1000000000;

    }

}
