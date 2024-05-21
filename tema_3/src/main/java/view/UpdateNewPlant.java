package view;

import javax.swing.*;
import java.awt.event.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class UpdateNewPlant extends JFrame {
    private JPanel mainPanel;
    private JButton saveButton;
    private JButton cancelButton;
    private JTextField textFieldName;
    private JTextField textFieldSpecies;
    private JTextField textFieldType;
    private JTextField textFieldCarnivorous;
    private JTextField textFieldEndangered;
    private JTextField textFieldLocation;
    private JPanel imagePanel;
    private JLabel imageLabel;
    private JLabel nameLabel;
    private JLabel speciesLabel;
    private JLabel typeLabel;
    private JLabel carnivLabel;
    private JLabel endangLabel;
    private JLabel locLabel;

    public UpdateNewPlant() {
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    public JButton getCancelButton() {
        return this.cancelButton;
    }

    public JButton getSaveButton() {
        return this.saveButton;
    }

    public JTextField getTextFieldName() {
        return this.textFieldName;
    }

    public JTextField getTextFieldSpecies() {
        return this.textFieldSpecies;
    }

    public JTextField getTextFieldType() {
        return this.textFieldType;
    }

    public JTextField getTextFieldCarnivorous() {
        return this.textFieldCarnivorous;
    }

    public JTextField getTextFieldEndangered() {
        return this.textFieldEndangered;
    }

    public JTextField getTextFieldLocation() {
        return this.textFieldLocation;
    }

    public JLabel getImageLabel() {
        return this.imageLabel;
    }

    public JLabel getNameLabel() {
        return this.nameLabel;
    }

    public JLabel getSpeciesLabel() {
        return this.speciesLabel;
    }

    public JLabel getTypeLabel() {
        return this.typeLabel;
    }

    public JLabel getCarnivLabel() {
        return this.carnivLabel;
    }

    public JLabel getEndangLabel() {
        return this.endangLabel;
    }

    public JLabel getLocLabel() {
        return this.locLabel;
    }

    public void setMessage(String message) {
        showMessageDialog(mainPanel, message);
    }

}
