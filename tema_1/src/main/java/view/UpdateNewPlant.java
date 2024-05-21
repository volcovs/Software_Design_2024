package view;

import presenter.PlantPresenter;

import javax.swing.*;

import java.awt.event.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class UpdateNewPlant extends JFrame implements IPlantGUI {
    private JPanel mainPanel;
    private JButton saveButton;
    private JButton cancelButton;
    private JTextField textFieldName;
    private JTextField textFieldSpecies;
    private JTextField textFieldType;
    private JTextField textFieldCarnivorous;
    private JTextField textFieldEndangered;
    private JTextField textFieldLocation;
    private PlantPresenter presenter;
    private String idToUpdate;
    private String operation;
    private String role;

    public UpdateNewPlant(String idToUpdate, String operation, String role) {
        this.role = role;
        this.idToUpdate = idToUpdate;
        this.operation = operation;

        presenter = new PlantPresenter(this);
        setTitle("Plant page");

        setContentPane(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        presenter.addCancelListener(this.role);
        presenter.initializeFields(idToUpdate);
        presenter.addSaveListener(this.operation, this.role);
        presenter.createMenuBar(role);

        setVisible(true);
    }

    @Override
    public void setMessage(String message) {
        showMessageDialog(this, message);
    }

    @Override
    public String getPlantID() {
        if (operation.equals("update")) {
            return this.idToUpdate;
        }
        else {
            return "1";
        }
    }

    @Override
    public String getPlantName() {
        return this.textFieldName.getText();
    }

    @Override
    public void setPlantName(String name) {
        this.textFieldName.setText(name);
    }

    @Override
    public String getSpecies() {
        return this.textFieldSpecies.getText();
    }

    @Override
    public void setSpecies(String species) {
        this.textFieldSpecies.setText(species);
    }

    @Override
    public String getPlantType() {
        return this.textFieldType.getText();
    }

    @Override
    public void setPlantType(String type) {
        this.textFieldType.setText(type);
    }

    @Override
    public String getCarnivorous() {
        return this.textFieldCarnivorous.getText();
    }

    @Override
    public void setCarnivorous(String carnivorous) {
        this.textFieldCarnivorous.setText(carnivorous);
    }

    @Override
    public String getEndangered() {
        return this.textFieldEndangered.getText();
    }

    @Override
    public void setEndangered(String endangered) {
        this.textFieldEndangered.setText(endangered);
    }

    @Override
    public String getPlantLocation() {
        return this.textFieldLocation.getText();
    }

    @Override
    public void setPlantLocation(String location) {
        this.textFieldLocation.setText(location);
    }

    @Override
    public String getSelectedCriterion() {
        return textFieldName.getText();
    }

    @Override
    public String getSelectedFilter() {
        return textFieldSpecies.getText();
    }

    @Override
    public String getSearchedInformation() {
        return textFieldSpecies.getText();
    }

    @Override
    public Integer getSelectedRow() {
        return 1;
    }

    @Override
    public void setSelectedRow(MouseEvent e) {
        e.getPoint();
    }

    @Override
    public String getSelectedID() {
        return idToUpdate;
    }

    @Override
    public void setSearched(String text) {
        textFieldName.setText(text);
    }

    @Override
    public void setTableContent(JTable table) {
        this.textFieldName.setText("Some error occured");
    }

    @Override
    public void setEditButton(boolean value) {
        saveButton.setVisible(value);
    }

    @Override
    public void setEditListener(ActionListener actionListener) {
        saveButton.addActionListener(actionListener);
    }

    @Override
    public void setSearchListener(ActionListener actionListener) {
        saveButton.addActionListener(actionListener);
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public void setTableListener(MouseAdapter adapter) {
        saveButton.addActionListener((ActionListener) adapter);
    }

    @Override
    public String getRole() {
        return this.role;
    }

    @Override
    public void setCancelListener(ActionListener actionListener) {
        cancelButton.addActionListener(actionListener);
    }

    @Override
    public void setSaveListener(ActionListener actionListener) {
        saveButton.addActionListener(actionListener);
    }

    @Override
    public void setFilterListener(ActionListener actionListener) {
        saveButton.addActionListener(actionListener);
    }

    @Override
    public void setSortListener(ActionListener actionListener) {
        saveButton.addActionListener(actionListener);
    }

    @Override
    public void setMenuBar(JMenuBar menuBar) {
        this.setJMenuBar(menuBar);
    }

    @Override
    public void disposePage() {
        this.dispose();
    }

}
