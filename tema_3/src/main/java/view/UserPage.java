package view;
import javax.swing.*;

public class UserPage extends JFrame {
    private JPanel mainPanel;
    private JTable tableUsers;
    private JButton editButton;
    private JRadioButton doarAdministratoriRadioButton;
    private JRadioButton doarAngajatiRadioButton;

    public UserPage() {
    }

    public JButton getEditButton() {
        return this.editButton;
    }

    public JTable getTableUsers() {
        return this.tableUsers;
    }

    public JRadioButton getDoarAdministratoriRadioButton() {
        return this.doarAdministratoriRadioButton;
    }

    public JRadioButton getDoarAngajatiRadioButton() {
        return this.doarAngajatiRadioButton;
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }
}
