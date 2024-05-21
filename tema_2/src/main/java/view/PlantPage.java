package view;


import net.sds.mvvm.bindings.Bind;
import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingType;
import viewmodel.PlantVM;

import javax.swing.*;
import java.awt.event.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class PlantPage extends JFrame {
    @Bind(value = "model", target = "table.value", type = BindingType.BI_DIRECTIONAL)
    private JTable tableAll;
    @Bind(value = "text", target = "search.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField plantSearch;
    private JButton searchButton;
    private JButton editButton;
    @Bind(value = "text", target = "filter.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField filter;
    @Bind(value = "text", target = "sort.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField sort;
    private JComboBox comboBoxFilter;
    private JComboBox comboBoxSort;
    private JPanel mainPanel;
    private JComboBox comboBoxFileFormat;
    private JLabel labelFormat;
    @Bind(value = "text", target = "role.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField role;
    @Bind(value = "text", target = "operation.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField operation;
    @Bind(value = "text", target = "format.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField format;
    private PlantVM plantVM;

    public PlantPage(String role) {
        this.role = new JTextField();
        this.operation = new JTextField();
        this.format = new JTextField();
        this.role.setText(role);
        setTitle("Plant page");

        setContentPane(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

        filter = new JTextField("-");
        sort = new JTextField("-");
        this.initialize();
        this.createMenu();

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plantVM.commandSearchPlant.execute();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation.setText("update");
                plantVM.commandNewPage.execute();
            }
        });

        editButton.setVisible(role.equals("employee"));

        tableAll.setDefaultEditor(Object.class, null);
        tableAll.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                plantVM.setPlantID(tableAll.getValueAt(tableAll.getSelectedRow(), 0).toString());

                if (e.getClickCount() == 2 && role.equals("employee")) {
                    //dublu-click = trebuie stearsa intrarea selectata din tabel
                    showMessageDialog(mainPanel, "Deleting selected plant...");
                    if (plantVM.commandDeletePlant.execute()) {
                        showMessageDialog(mainPanel, "Plant deleted successfully!");
                    }
                    else {
                        showMessageDialog(mainPanel, "Error!");
                    }

                    plantVM.commandViewPlants.execute();
                }
            }
        });

        plantVM = new PlantVM();
        try {
            Binder.bind(this, plantVM);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        plantVM.commandViewPlants.execute();
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

        comboBoxFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter.setText(comboBoxFilter.getSelectedItem().toString());
                plantVM.commandViewPlants.execute();
            }
        });

        comboBoxSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sort.setText(comboBoxSort.getSelectedItem().toString());
                plantVM.commandViewPlants.execute();
            }
        });

        comboBoxFileFormat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                format.setText(comboBoxFileFormat.getSelectedItem().toString());
                showMessageDialog(mainPanel, "Saving to file");
                if (plantVM.commandSaveToFile.execute()) {
                    showMessageDialog(mainPanel, "File saved successfully");
                }
                else {
                    showMessageDialog(mainPanel, "Error writing to file");
                }
            }
        });

        comboBoxFileFormat.setVisible(role.getText().equals("employee"));
        labelFormat.setVisible(role.getText().equals("employee"));
    }

    private void createMenu() {
        JMenuItem addPlant;
        JMenuItem logIn;
        JMenuItem userManagement;
        JMenuItem plants;
        JMenuItem addUser;
        JMenu menu;

        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        logIn = new JMenuItem("Autentificare");
        logIn.setMnemonic(KeyEvent.VK_D);
        addPlant = new JMenuItem("Adaugare planta");
        addPlant.setMnemonic(KeyEvent.VK_D);
        userManagement = new JMenuItem("Administrare utilizatori");
        userManagement.setMnemonic(KeyEvent.VK_D);
        plants = new JMenuItem("Vizualizare plante");
        plants.setMnemonic(KeyEvent.VK_D);
        addUser = new JMenuItem("Adaugare utilizator");
        addUser.setMnemonic(KeyEvent.VK_D);

        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                plantVM.commandNewLogIn.execute();
            }
        });

        addPlant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation.setText("create");
                plantVM.commandNewPage.execute();
            }
        });

        userManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                plantVM.commandViewUsersFromMenu.execute();
            }
        });

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                operation.setText("create");
                plantVM.commandViewUsersFromMenu.execute();
                plantVM.commandNewUserFromMenu.execute();
            }
        });

        menu.add(logIn);
        menu.add(plants);

        if (role.getText().equals("administrator")) {
            menu.add(userManagement);
            menu.add(addUser);
        }
        else if (role.getText().equals("employee")) {
            menu.add(addPlant);
        }

        this.setJMenuBar(menuBar);
    }

}
