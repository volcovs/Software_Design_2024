package presenter;

import model.Plant;
import model.repository.PlantRepository;
import view.*;

import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static javax.swing.JOptionPane.showConfirmDialog;

public class PlantPresenter {
    private IPlantGUI IPlantGUI;
    private PlantRepository plantRepository;

    public PlantPresenter(IPlantGUI IPlantGUI) {
        this.IPlantGUI = IPlantGUI;
        this.plantRepository = new PlantRepository("gradina_botanica");
    }

    public void addPlant() {
        Plant plant = this.validInformation();
        if (plant != null) {
            boolean flag = this.plantRepository.addPlant(plant);
            if (flag) {
                this.IPlantGUI.setMessage("Success!");
                this.resetFields();
            }
            else {
                this.IPlantGUI.setMessage("Failure!");
            }
        }
    }


    public void deletePlant() {
        if (this.IPlantGUI.getSelectedRow() != null) {
            String id = this.IPlantGUI.getSelectedID();
            boolean flag = this.plantRepository.deletePlant(id);

            if (flag) {
                this.resetFields();
                this.IPlantGUI.setMessage("Success!");
            }
            else {
                this.IPlantGUI.setMessage("Failure!");
            }
        }
    }

    public void updatePlant() {
        if (this.IPlantGUI.getSelectedRow() != null) {
            String id = this.IPlantGUI.getSelectedID();
            Plant newPlant = this.validInformation();
            if (newPlant != null) {
                boolean flag = this.plantRepository.updatePlant(id, newPlant);
                if (flag) {
                    this.resetFields();
                    this.IPlantGUI.setMessage("Success!");
                } else {
                    this.IPlantGUI.setMessage("Failure!");
                }
            }
        }
    }

    public void searchPlantByNameOrSpecies() {
        String search = this.IPlantGUI.getSearchedInformation();
        String filterBy = "Nume";

        List<Plant> plants = this.plantRepository.plantListFiltered(filterBy, search);

        if (plants != null && plants.size() != 0) {
            this.setTableList(plants);
            this.resetFields();
        }
        else {
            filterBy = "Specie";
            plants = this.plantRepository.plantListFiltered(filterBy, search);

            if (plants != null && plants.size() != 0) {
                this.setTableList(plants);
                this.resetFields();
            }
            else {
                plants = new ArrayList<Plant>();
                plants.add(new Plant("", "", "", "", "", "", ""));
                this.setTableList(plants);
                this.resetFields();
                this.IPlantGUI.setMessage("Empty list!");
            }
        }
    }

    public Plant getPlant() {
        String id = this.IPlantGUI.getPlantID();
        return this.plantRepository.searchPlant(id);
    }

    public void plantList() {
        String sortBy = this.IPlantGUI.getSelectedCriterion();
        String filterBy = this.IPlantGUI.getSelectedFilter();
        String search = this.IPlantGUI.getSearchedInformation();

        List<Plant> plants = this.plantRepository.plantListFiltered(filterBy, search);

        if (sortBy.equals("Tip")) {
            Collections.sort(plants, new Comparator<Plant>() {
                @Override
                public int compare(Plant o1, Plant o2) {
                    return o1.getPlantType().compareTo(o2.getPlantType());
                }
            });
        }
        else if (sortBy.equals("Specie")) {
            Collections.sort(plants, new Comparator<Plant>() {
                @Override
                public int compare(Plant o1, Plant o2) {
                    return o1.getSpecies().compareTo(o2.getSpecies());
                }
            });
        }
        // else -> nicio modificare a listei

        if (plants != null && plants.size() != 0) {
            this.setTableList(plants);
            this.resetFields();
        }
        else {
            plants = new ArrayList<Plant>();
            plants.add(new Plant("", "", "", "", "", "", ""));
            this.setTableList(plants);
            this.resetFields();
            this.IPlantGUI.setMessage("Empty list!");
        }
    }

    private void setTableList(List<Plant> list) {
        List<String> columnNames = new ArrayList<String>();
        int iter = 0;

        for (Field field : list.get(0).getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                columnNames.add(field.getName());
                iter++;

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        Object[][] tableData = new Object[list.size()][iter];
        int i = 0;
        for (Plant obj : list) {
            int j = 0;
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    tableData[i][j++] = field.get(obj);
                } catch (IllegalAccessException exc) {
                    System.out.println("Access error");
                }
            }
            i++;
        }

        JTable table = new JTable(tableData, columnNames.toArray());
        this.IPlantGUI.setTableContent(table);
    }

    private void resetFields() {
        this.IPlantGUI.setPlantName("");
        this.IPlantGUI.setSpecies("");
        this.IPlantGUI.setPlantType("");
        this.IPlantGUI.setCarnivorous("");
        this.IPlantGUI.setEndangered("");
        this.IPlantGUI.setPlantLocation("");
    }

    private Plant validInformation() {
        String plantId = this.IPlantGUI.getPlantID();
        if (plantId == null || plantId.length() == 0) {
            return null;
        }

        String plantName = this.IPlantGUI.getPlantName();
        if (plantName == null || plantName.length() == 0) {
            return null;
        }

        String species = this.IPlantGUI.getSpecies();
        if (species == null || species.length() == 0) {
            return null;
        }

        String type = this.IPlantGUI.getPlantType();
        if (type == null || type.length() == 0) {
            return null;
        }

        String carnivorous = this.IPlantGUI.getCarnivorous();
        if (carnivorous == null || carnivorous.length() == 0) {
            carnivorous = "no";
        }

        String endangered = this.IPlantGUI.getEndangered();
        if (endangered == null || endangered.length() == 0) {
            endangered = "no";
        }

        String location = this.IPlantGUI.getPlantLocation();
        if (location == null || location.length() == 0) {
            return null;
        }

        return new Plant(plantId, plantName, species, type, carnivorous, endangered, location);
    }


    public void createMenuBar(String role) {
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
                IPlantGUI.disposePage();
                LogInPage logInPage = new LogInPage(role);
            }
        });

        addPlant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IPlantGUI.disposePage();
                UpdateNewPlant updateNewPlant = new UpdateNewPlant("", "create", role);
            }
        });

        userManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IPlantGUI.disposePage();
                UserPage users = new UserPage(role);
            }
        });

        plants.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IPlantGUI.disposePage();
                PlantPage plantPage = new PlantPage(role);
            }
        });

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IPlantGUI.disposePage();
                UpdateNewUser updateNewUser = new UpdateNewUser("", "create", role);
            }
        });

        menu.add(logIn);
        menu.add(plants);
        this.IPlantGUI.setEditButton(false);
        if (role.equals("administrator")) {
            menu.add(userManagement);
            menu.add(addUser);
        }
        else if (role.equals("employee")) {
            menu.add(addPlant);
            this.IPlantGUI.setEditButton(true);
        }

        this.IPlantGUI.setMenuBar(menuBar);
    }

    public void addEditListener(String role) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IPlantGUI.disposePage();
                UpdateNewPlant updateNewPlant = new UpdateNewPlant(IPlantGUI.getSelectedID(), "update", role);
            }
        };

        IPlantGUI.setEditListener(actionListener);
    }

    public void addSearchListener() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPlantByNameOrSpecies();
            }
        };

        IPlantGUI.setSearchListener(actionListener);
    }

    public void addTableListener() {
        MouseAdapter actionListener =
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        IPlantGUI.setSelectedRow(e);

                        if (e.getClickCount() == 2 && IPlantGUI.getRole().equals("employee")) {
                            //dublu-click = trebuie stearsa intrarea selectata din tabel
                            int option = showConfirmDialog(IPlantGUI.getMainPanel(), "Confirmati stergerea intrarii?");
                            if (option == JOptionPane.YES_OPTION) {
                                deletePlant();
                                plantList();
                            }
                        }
                    }
                };

        IPlantGUI.setTableListener(actionListener);
    }

    public void addCancelListener(String role) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IPlantGUI.disposePage();
                PlantPage plantPage = new PlantPage(role);
            }
        };

        IPlantGUI.setCancelListener(actionListener);
    }

    public void addSaveListener(String operation, String role) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (operation.equals("update")) {
                    updatePlant();
                }
                else {
                    addPlant();
                }
                IPlantGUI.disposePage();
                PlantPage plantPage = new PlantPage(role);
            }
        };

        IPlantGUI.setSaveListener(actionListener);
    }

    public void initializeFields(String idToUpdate) {
        if (idToUpdate != null && !idToUpdate.equals("")) {
            Plant plant = getPlant();

            IPlantGUI.setPlantName(plant.getPlantName());
            IPlantGUI.setSpecies(plant.getSpecies());
            IPlantGUI.setPlantType(plant.getPlantType());
            IPlantGUI.setCarnivorous(plant.getCarnivorous());
            IPlantGUI.setEndangered(plant.getEndangered());
            IPlantGUI.setPlantLocation(plant.getLocation());
        }

    }

    public void addFilterListener() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plantList();
            }
        };

        IPlantGUI.setFilterListener(actionListener);
    }

    public void addSortListener() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plantList();
            }
        };

        IPlantGUI.setSortListener(actionListener);
    }

}
