package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Objects;

public class MainPage extends JFrame {

    private JPanel mainPane;
    private JSpinner spinnerRowsFirst;
    private JSpinner spinnerColumnsFirst;
    private JSpinner spinnerMinValue;
    private JSpinner spinnerMaxValue;
    private JButton startTestButton;
    private JComboBox comboBoxTimeUnit;
    private JTextField textFieldNd4j;
    private JTextField textFieldApache;
    private JTextField textFieldLa4j;
    private JTextField textFieldColt;
    private JTextField textFieldEjml;
    private JTextField textFieldNoTheads;
    private JTextField textFieldWithThreads;
    private JLabel processors;
    private JSpinner spinnerProcessrs;
    private JSpinner spinnerColumnsSecond;
    private JSpinner spinnerRowsSecond;

    public MainPage() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPane);
        this.setTitle("Matrix Multiplication Tester");
        this.setSize(1000, 600);
        this.setPreferredSize(new Dimension(1000, 600));
        this.pack();
        this.setResizable(false);
        this.centerView();
        this.configureSpinners();
        this.configureComboBox();
        this.setProcessors();

    }

    private void centerView() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();

        this.setLocation((screen.width - frameSize.width) >> 1,
                (screen.height - frameSize.height) >> 1);
    }

    private void configureSpinners(){
        SpinnerNumberModel modelRowsFirst = new SpinnerNumberModel(1, 1, 5000, 1);
        SpinnerNumberModel modelColumnsFirst = new SpinnerNumberModel(1, 1, 5000, 1);
        this.spinnerRowsFirst.setModel(modelRowsFirst);
        this.spinnerColumnsFirst.setModel(modelColumnsFirst);
        SpinnerNumberModel modelRowsSecond = new SpinnerNumberModel(1, 1, 5000, 1);
        SpinnerNumberModel modelColumnsSecond = new SpinnerNumberModel(1, 1, 5000, 1);
        this.spinnerRowsSecond.setModel(modelRowsSecond);
        this.spinnerColumnsSecond.setModel(modelColumnsSecond);
        SpinnerNumberModel modelMin = new SpinnerNumberModel(0, -10000, 9999, 10);
        SpinnerNumberModel modelMax = new SpinnerNumberModel(0, -9999, 10000, 10);
        this.spinnerMinValue.setModel(modelMin);
        this.spinnerMaxValue.setModel(modelMax);
        SpinnerNumberModel modelProcessors = new SpinnerNumberModel(1, 1, 64, 1);
        this.spinnerProcessrs.setModel(modelProcessors);
    }

    private void configureComboBox(){
        comboBoxTimeUnit.removeAllItems();
        String[] new_entries=new String[] {"nanoseconds","microseconds","milliseconds","seconds"};
        for (String s : new_entries) {
            comboBoxTimeUnit.insertItemAt(s, comboBoxTimeUnit.getItemCount());
        }
        comboBoxTimeUnit.setSelectedIndex(0);
    }

    public String getTimeUnit() {
        String unit = Objects.requireNonNull(comboBoxTimeUnit.getSelectedItem()).toString();
        return switch (unit) {
            case "nanoseconds" -> " ns";
            case "milliseconds" -> " ms";
            case "microseconds" -> " μs";
            case "seconds" -> " s";
            default -> throw new IllegalStateException("Unexpected value: " + unit);
        };
    }

    public int getMinValue(){
        return (int) spinnerMinValue.getValue();
    }

    public int getMaxValue(){
        return (int) spinnerMaxValue.getValue();
    }

    public int getRowsFirst(){
        return (int) spinnerRowsFirst.getValue();
    }

    public int getColumnsFirst(){
        return (int) spinnerColumnsFirst.getValue();
    }

    public int getRowsSecond(){
        return (int) spinnerRowsSecond.getValue();
    }

    public int getColumnsSecond(){
        return (int) spinnerColumnsSecond.getValue();
    }

    public void setTextFieldNd4j(String time) {
        this.textFieldNd4j.setText(time + getTimeUnit());
    }

    public void setTextFieldApache(String time) {
        this.textFieldApache.setText(time + getTimeUnit());
    }

    public void setTextFieldLa4j(String time) {
        this.textFieldLa4j.setText(time + getTimeUnit());
    }

    public void setTextFieldColt(String time) {
        this.textFieldColt.setText(time + getTimeUnit());
    }

    public void setTextFieldEjml(String time) {
        this.textFieldEjml.setText(time + getTimeUnit());
    }

    public void setTextFieldNoThreads(String time) {
        this.textFieldNoTheads.setText(time + getTimeUnit());
    }

    public void setTextFieldWithThreads(String time) {
        this.textFieldWithThreads.setText(time + getTimeUnit());
    }

    public void setProcessors() {
        this.processors.setText(String.valueOf(Runtime.getRuntime().availableProcessors()));
    }

    public int getProcessors(){
        return (int) this.spinnerProcessrs.getValue();
    }

    /**
     * This method will execute a method in the Controller named actionPerformed
     * if the startTestButton is clicked
     * @param listenForStartTestButton object handling startTestButton clicked
     */
    public void addStartTestListener(ActionListener listenForStartTestButton){

        startTestButton.addActionListener(listenForStartTestButton);
    }

    /**
     * This method will execute a method in the Controller named actionPerformed
     * if the item in combo box is changed
     * @param listenForCombo object handling startTestButton clicked
     */
    public void addComboListener(ItemListener listenForCombo){

        comboBoxTimeUnit.addItemListener(listenForCombo);
    }

    public void showMatricesSizeError(){
        JOptionPane.showMessageDialog(null,
                "Multiplication of matrix AB is possible if matrix A has as many columns as matrix B has rows",
                "Matrices size error",
                JOptionPane.ERROR_MESSAGE);
    }

    public void showValuesError(){
        JOptionPane.showMessageDialog(null,
                "The minimum value cannot be greater than or equal to the maximum value",
                "Values error",
                JOptionPane.ERROR_MESSAGE);
    }

    public void showLongerTimeInfo(){
        JOptionPane.showMessageDialog(null,
                "This action will take a little longer. Please be patient");
    }

    public void showDoneInfo(){
        JOptionPane.showMessageDialog(null,
                "Done!");
    }
}
