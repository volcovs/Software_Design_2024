package view;

import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;


public class PlantPage extends JFrame {
    private JTable tableAll;
    private JTextField plantSearch;
    private JButton searchButton;
    private JButton editButton;
    private JComboBox comboBoxFilter;
    private JComboBox comboBoxSort;
    private JPanel mainPanel;
    private JComboBox comboBoxFileFormat;
    private JLabel labelFormat;
    private JButton statisticsButton;
    private JLabel labelSort;
    private JLabel labelFilter;


    public PlantPage() {
        this.initialize();
    }

    public JTable getTableAll() {
        return this.tableAll;
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    public JTextField getPlantSearch() {
        return this.plantSearch;
    }

    public JButton getSearchButton() {
        return this.searchButton;
    }
    public JButton getStatisticsButton() {
        return this.statisticsButton;
    }

    public JButton getEditButton() {
        return this.editButton;
    }

    public JComboBox getComboBoxFilter() {
        return this.comboBoxFilter;
    }

    public JComboBox getComboBoxSort() {
        return this.comboBoxSort;
    }

    public JComboBox getComboBoxFileFormat() {
        return this.comboBoxFileFormat;
    }

    public JLabel getLabelFormat() {
        return this.labelFormat;
    }

    private void initialize() {
        comboBoxFilter.addItem("-");
        comboBoxFilter.addItem("Tip");
        comboBoxFilter.addItem("Specie");
        comboBoxFilter.addItem("Carnivore");
        comboBoxFilter.addItem("In pericol de disparitie");
        comboBoxFilter.addItem("Locatie");

        comboBoxSort.addItem("-");
        comboBoxSort.addItem("Tip");
        comboBoxSort.addItem("Specie");

        comboBoxFileFormat.addItem("-");
        comboBoxFileFormat.addItem(".csv");
        comboBoxFileFormat.addItem(".json");
        comboBoxFileFormat.addItem(".xml");
        comboBoxFileFormat.addItem(".doc");
    }

    public void setMessage(String message) {
        showMessageDialog(mainPanel, message);
    }

    public JLabel getLabelSort() {
        return this.labelSort;
    }

    public JLabel getLabelFilter() {
        return this.labelFilter;
    }

}
