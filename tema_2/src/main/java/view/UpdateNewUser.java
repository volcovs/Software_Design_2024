package view;


import model.repository.EmployeeRepository;
import net.sds.mvvm.bindings.Bind;
import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingType;
import viewmodel.EmployeeVM;

import javax.swing.*;
import java.awt.event.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class UpdateNewUser extends JFrame {
    private JPanel mainPanel;
    private JButton saveButton;
    private JButton cancelButton;
    @Bind(value = "text", target = "firstName.value", type = BindingType.BI_DIRECTIONAL)
    private JTextArea textFieldFirst;
    @Bind(value = "text", target = "lastName.value", type = BindingType.BI_DIRECTIONAL)
    private JTextArea textFieldLast;
    @Bind(value = "text", target = "dob.value", type = BindingType.BI_DIRECTIONAL)
    private JTextArea textFieldDOB;
    @Bind(value = "text", target = "address.value", type = BindingType.BI_DIRECTIONAL)
    private JTextArea textFieldAddress;
    @Bind(value = "text", target = "phone.value", type = BindingType.BI_DIRECTIONAL)
    private JTextArea textFieldPhone;
    @Bind(value = "text", target = "email.value", type = BindingType.BI_DIRECTIONAL)
    private JTextArea textFieldEmail;
    @Bind(value = "text", target = "userField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField textFieldUsername;
    @Bind(value = "text", target = "passField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField textFieldPassword;
    @Bind(value = "text", target = "person.value", type = BindingType.BI_DIRECTIONAL)
    private JTextArea textFieldPerson;
    private JRadioButton statusButton;
    @Bind(value = "text", target = "userID.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField idToUpdate;
    @Bind(value = "text", target = "operation.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField operation;
    @Bind(value = "text", target = "role.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField role;
    @Bind(value = "text", target = "status.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField statusField;
    private EmployeeVM userVM;

    public UpdateNewUser(EmployeeVM userVM, String idToUpdate, String operation, String role) {
        this.role = new JTextField();
        this.operation = new JTextField();
        this.statusField = new JTextField();
        this.role.setText(role);
        setTitle("User administration page");

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

        this.idToUpdate = new JTextField(idToUpdate);
        this.operation.setText(operation);

        if (operation.equals("update")) {
            this.textFieldFirst.setEditable(false);
            this.textFieldLast.setEditable(false);
            this.textFieldAddress.setEditable(false);
            this.textFieldPerson.setEditable(false);
            this.textFieldDOB.setEditable(false);
            this.textFieldPhone.setEditable(false);
            this.textFieldEmail.setEditable(false);
        }

        setVisible(true);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (statusButton.isSelected()) {
                    statusField.setText("yes");
                } else {
                    statusField.setText("no");
                }

                if (operation.equals("update")) {
                    if (userVM.commandEditUser.execute()) {
                        setMessage("Success!");
                        userVM.commandViewUsers.execute();
                        dispose();
                    }
                    else {
                        setMessage("Error!");
                    }
                }
                else {
                    if (userVM.commandAddUser.execute()) {
                        setMessage("Success!");
                        userVM.commandViewUsers.execute();
                        dispose();
                    }
                    else {
                        setMessage("Error!");
                    }
                }
            }
        });

        this.userVM = new EmployeeVM();
        textFieldPerson.setText(userVM.getPerson());
        textFieldFirst.setText(userVM.getFirstName());
        textFieldLast.setText(userVM.getLastName());
        textFieldDOB.setText(userVM.getDOB());
        textFieldAddress.setText(userVM.getAddress());
        textFieldPhone.setText(userVM.getPhoneNumber());
        textFieldEmail.setText(userVM.getEmail());
        textFieldUsername.setText(userVM.getUsername());
        textFieldPassword.setText(userVM.getPassword());
        if (userVM.getStatus() != null) {
            statusButton.setSelected(userVM.getStatus().equals("yes"));
        }
        try {
            Binder.bind(this, userVM);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        userVM.commandViewUsers.execute();
    }

    private void setMessage(String s) {
        showMessageDialog(this, s);
    }
}
