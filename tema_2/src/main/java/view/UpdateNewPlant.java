package view;

import net.sds.mvvm.bindings.Bind;
import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingType;
import viewmodel.PlantVM;

import javax.swing.*;

import java.awt.event.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class UpdateNewPlant extends JFrame {
    private JPanel mainPanel;
    private JButton saveButton;
    private JButton cancelButton;
    @Bind(value = "text", target = "name.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField textFieldName;
    @Bind(value = "text", target = "species.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField textFieldSpecies;
    @Bind(value = "text", target = "type.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField textFieldType;
    @Bind(value = "text", target = "carnivorous.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField textFieldCarnivorous;
    @Bind(value = "text", target = "endangered.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField textFieldEndangered;
    @Bind(value = "text", target = "location.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField textFieldLocation;
    @Bind(value = "text", target = "id.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField idToUpdate;
    @Bind(value = "text", target = "operation.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField operation;
    @Bind(value = "text", target = "role.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField role;
    private PlantVM plantVM;

    public UpdateNewPlant(PlantVM plantVM, String idToUpdate, String operation, String role, JTable tableAll) {
        this.role = new JTextField();
        this.operation = new JTextField();
        this.idToUpdate = new JTextField(idToUpdate);
        this.role.setText(role);
        this.operation.setText(operation);

        setTitle("Plant page");
        setContentPane(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

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
                if (operation.equals("update")) {
                    if (plantVM.commandEditPlant.execute()) {
                        setMessage("Success!");
                        plantVM.commandViewPlants.execute();
                        dispose();
                    }
                    else {
                        setMessage("Error!");
                    }
                }
                else {
                    if (plantVM.commandAddPlant.execute()) {
                        setMessage("Success!");
                        plantVM.commandViewPlants.execute();
                        dispose();
                    }
                    else {
                        setMessage("Error!");
                    }
                }
            }
        });

        this.plantVM = new PlantVM();
        textFieldName.setText(plantVM.getPlantName());
        textFieldSpecies.setText(plantVM.getSpecies());
        textFieldType.setText(plantVM.getPlantType());
        textFieldCarnivorous.setText(plantVM.getCarnivorous());
        textFieldEndangered.setText(plantVM.getEndangered());
        textFieldLocation.setText(plantVM.getLocation());
        try {
            Binder.bind(this, plantVM);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void setMessage(String message) {
        showMessageDialog(this, message);
    }

}
