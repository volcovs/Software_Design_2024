package view;

import presenter.PlantPresenter;

import javax.swing.*;
import java.awt.event.*;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class PlantPage extends JFrame implements IPlantGUI {
    private JTable tableAll;
    private JTextField plantSearch;
    private JButton searchButton;
    private JButton editButton;
    private JComboBox comboBoxFilter;
    private JComboBox comboBoxSort;
    private JPanel mainPanel;
    private PlantPresenter presenter;
    private int row;
    private String role;

    public PlantPage(String role) {
        this.role = role;
        presenter = new PlantPresenter(this);
        setTitle("Plant page");

        setContentPane(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        this.presenter.plantList();

        comboBoxFilter.addItem("-");
        comboBoxFilter.addItem("Tip");
        comboBoxFilter.addItem("Specie");
        comboBoxFilter.addItem("Carnivore");
        comboBoxFilter.addItem("In pericol de disparitie");
        comboBoxFilter.addItem("Locatie");

        comboBoxSort.addItem("-");
        comboBoxSort.addItem("Tip");
        comboBoxSort.addItem("Specie");

        presenter.addEditListener(this.role);
        presenter.addSearchListener();
        presenter.addTableListener();
        presenter.addFilterListener();
        presenter.addSortListener();

        presenter.createMenuBar(role);
        setVisible(true);
    }

    @Override
    public String getPlantID() {
        return (String) tableAll.getValueAt(row, 0);
    }

    @Override
    public String getPlantName() {
        return (String) tableAll.getValueAt(row, 1);
    }

    @Override
    public void setPlantName(String name) {
        tableAll.editCellAt(row, 2);
    }

    @Override
    public String getSpecies() {
        return (String) tableAll.getValueAt(row, 2);
    }

    @Override
    public void setSpecies(String species) {
        tableAll.editCellAt(row, 2);
    }

    @Override
    public String getPlantType() {
        return (String) tableAll.getValueAt(row, 3);
    }

    @Override
    public void setPlantType(String type) {
        tableAll.editCellAt(row, 3);
    }

    @Override
    public String getCarnivorous() {
        return (String) tableAll.getValueAt(row, 4);
    }

    @Override
    public void setCarnivorous(String carnivorous) {
        tableAll.editCellAt(row, 4);
    }

    @Override
    public String getEndangered() {
        return (String) tableAll.getValueAt(row, 5);
    }

    @Override
    public void setEndangered(String endangered) {
        tableAll.editCellAt(row, 5);
    }

    @Override
    public String getPlantLocation() {
        return (String) tableAll.getValueAt(row, 6);
    }

    @Override
    public void setPlantLocation(String location) {
        tableAll.editCellAt(row, 6);
    }

    @Override
    public String getSelectedCriterion() {
        if (comboBoxSort.getSelectedItem() != null) {
            return comboBoxSort.getSelectedItem().toString();
        }
        else {
            return "-";
        }
    }

    @Override
    public String getSelectedFilter() {
        if (comboBoxFilter.getSelectedItem() != null) {
            return comboBoxFilter.getSelectedItem().toString();
        }
        else {
            return "-";
        }
    }

    @Override
    public String getSearchedInformation() {
        return this.plantSearch.getText();
    }

    @Override
    public Integer getSelectedRow() {
       return row;
    }

    @Override
    public void setSelectedRow(MouseEvent e) {
        row = tableAll.rowAtPoint(e.getPoint());
    }

    @Override
    public String getSelectedID() {
        return tableAll.getValueAt(row, 0).toString();
    }

    @Override
    public void setSearched(String text) {
        this.plantSearch.setText(text);
    }

    @Override
    public void setTableContent(JTable table) {
        tableAll.setModel(table.getModel());
        tableAll.setDefaultEditor(Object.class, null);
    }

    @Override
    public void setEditButton(boolean value) {
        this.editButton.setVisible(value);
    }

    @Override
    public void setMessage(String message) {
        showMessageDialog(this, message);
    }

    @Override
    public void setMenuBar(JMenuBar menuBar) {
        this.setJMenuBar(menuBar);
    }

    @Override
    public void disposePage() {
        this.dispose();
    }

    @Override
    public void setEditListener(ActionListener actionListener) {
        editButton.addActionListener(actionListener);
    }

    @Override
    public void setSearchListener(ActionListener actionListener) {
        searchButton.addActionListener(actionListener);
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public void setTableListener(MouseAdapter adapter) {
        tableAll.addMouseListener(adapter);
    }

    @Override
    public String getRole() {
        return this.role;
    }

    @Override
    public void setCancelListener(ActionListener actionListener) {
        searchButton.addActionListener(actionListener);
    }

    @Override
    public void setSaveListener(ActionListener actionListener) {
        editButton.addActionListener(actionListener);
    }

    @Override
    public void setFilterListener(ActionListener actionListener) {
        comboBoxFilter.addActionListener(actionListener);
    }

    @Override
    public void setSortListener(ActionListener actionListener) {
        comboBoxSort.addActionListener(actionListener);
    }

}
