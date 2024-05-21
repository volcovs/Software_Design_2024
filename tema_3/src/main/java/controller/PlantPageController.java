package controller;

import model.*;
import model.repository.PlantRepository;
import view.PlantPage;
import view.Statistics;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.util.*;

public class PlantPageController implements Observer {
    private PlantPage plantPage;
    private String role;
    private String rowToEdit;
    private List<Plant> plantList;
    private DocumentWriter writer;
    private PlantPageController aux;
    private String lang;
    private Language language;


    public PlantPageController(String role, String lang) {
        this.role = role;
        this.plantPage = new PlantPage();
        this.aux = this;
        this.lang = lang;

        switch (lang) {
            case "romanian" -> { this.language = new Romanian();  }
            case "english" -> { this.language = new English();  }
            case "french" -> { this.language = new French();  }
            case "russian" -> { this.language = new Russian(); }
            default -> { System.out.println("Not a valid language choice");}
        }

        HashMap<String, String> languageConfig = this.language.getLanguageConfig();

        PlantRepository plantRepository = new PlantRepository("gradina_botanica", this);
        plantList = plantRepository.plantList();
        listToTable(plantList);
        createMenu();

        this.plantPage.getEditButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateNewPlantController updateNewPlantController = new UpdateNewPlantController(role, "update", rowToEdit, aux, lang);
                updateNewPlantController.getView().setVisible(true);
                plantPage.dispose();
            }
        });

        this.plantPage.getStatisticsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StatisticsController statisticsController = new StatisticsController(role, lang);
                for (Statistics s: statisticsController.getViews()) {
                    s.setVisible(true);
                }
            }
        });

        this.plantPage.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlantRepository plantRepository = new PlantRepository("gradina_botanica", aux);
                String filterBy = "Nume";
                String search = plantPage.getPlantSearch().getText();

                plantList = plantRepository.plantListFiltered(filterBy, search);
                if (plantList == null || plantList.size() == 0) {
                    filterBy = "Specie";
                    plantList = plantRepository.plantListFiltered(filterBy, search);
                }

                listToTable(plantList);
            }
        });

        this.plantPage.getComboBoxSort().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlantRepository plantRepository = new PlantRepository("gradina_botanica", aux);
                String sortBy = plantPage.getComboBoxSort().getSelectedItem().toString();

                if (sortBy.equals("Tip")) {
                    Collections.sort(plantList, new Comparator<Plant>() {
                        @Override
                        public int compare(Plant o1, Plant o2) {
                            return o1.getPlantType().compareTo(o2.getPlantType());
                        }
                    });
                }
                else if (sortBy.equals("Specie")) {
                    Collections.sort(plantList, new Comparator<Plant>() {
                        @Override
                        public int compare(Plant o1, Plant o2) {
                            return o1.getSpecies().compareTo(o2.getSpecies());
                        }
                    });
                }

                listToTable(plantList);
            }
        });

        this.plantPage.getComboBoxFilter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlantRepository plantRepository = new PlantRepository("gradina_botanica", aux);
                String filterBy = plantPage.getComboBoxFilter().getSelectedItem().toString();
                String search = plantPage.getPlantSearch().getText();

                plantList = plantRepository.plantListFiltered(filterBy, search);
                listToTable(plantList);
            }
        });


        this.plantPage.getComboBoxFileFormat().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String format = plantPage.getComboBoxFileFormat().getSelectedItem().toString();
                plantPage.setMessage(languageConfig.get("Saving msg"));
                List<Plant> plants = plantRepository.plantList();

                DocumentFactory factory = null;
                switch (format) {
                    case ".csv" -> { factory = new CSVFactory();  }
                    case ".json" -> { factory = new JSONFactory();  }
                    case ".xml" -> { factory = new XMLFactory();  }
                    case ".doc" -> { factory = new DOCFactory(); }
                    default -> { System.out.println("Not a valid format choice");}
                }

                if (factory != null) {
                    writer = factory.factoryMethod();
                    writer.writeFile(plants);
                    plantPage.setMessage(languageConfig.get("Open msg"));
                    writer.openFile();
                }
                else {
                    plantPage.setMessage(languageConfig.get("Fail msg"));
                }
            }
        });

        this.plantPage.getTableAll().setDefaultEditor(Object.class, null);
        this.plantPage.getTableAll().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                rowToEdit = plantPage.getTableAll().getValueAt(plantPage.getTableAll().getSelectedRow(), 0).toString();

                if (e.getClickCount() == 2 && role.equals("employee")) {
                    //dublu-click = trebuie stearsa intrarea selectata din tabel
                    PlantRepository plantRepository = new PlantRepository("gradina_botanica", aux);

                    if (plantRepository.deletePlant(rowToEdit)) {
                        plantPage.setMessage(languageConfig.get("Success msg"));
                    }
                    else {
                        plantPage.setMessage(languageConfig.get("Fail msg"));
                    }

                    plantList = plantRepository.plantList();
                    //nu mai este necesar daca se foloseste Observer -> modificarea va fi detectata din model
                    //listToTable(plantList);
                }
            }
        });

        plantPage.getStatisticsButton().setText(languageConfig.get("Statistics"));
        plantPage.getSearchButton().setText(languageConfig.get("Search"));
        plantPage.getEditButton().setText(languageConfig.get("Edit"));
        plantPage.getLabelFilter().setText(languageConfig.get("Filter") + ":");
        plantPage.getLabelSort().setText(languageConfig.get("Sort") + ":");
        plantPage.getLabelFormat().setText(languageConfig.get("Format") + ":");
        plantPage.setTitle(languageConfig.get("Plant page"));
    }

    public PlantPage getView() {
        this.plantPage.setContentPane(plantPage.getMainPanel());

        this.plantPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.plantPage.setSize(800, 600);

        this.plantPage.getEditButton().setVisible(role.equals("employee"));
        this.plantPage.getStatisticsButton().setVisible(role.equals("employee"));
        this.plantPage.getComboBoxFileFormat().setVisible(role.equals("employee"));
        this.plantPage.getLabelFormat().setVisible(role.equals("employee"));
        this.plantPage.getTableAll().setRowHeight(50);

        return this.plantPage;
    }

    @Override
    public void update(Observable o, Object arg) {
        PlantRepository plantRepository = (PlantRepository) o;
        listToTable(plantRepository.plantList());
    }


    private void listToTable(List<Plant> plantList) {
        if (plantList.size() == 0) {
            plantList.add(new Plant("", "", "", "", "", "", "", new byte[0]));
        }

        List<String> columnNames = new ArrayList<String>();
        int iter = 0;

        for (Field field : plantList.get(0).getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                columnNames.add(field.getName());
                iter++;

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return;
            }
        }

        Object[][] tableData = new Object[plantList.size()][iter];
        int i = 0;
        for (Plant obj : plantList) {
            int j = 0;
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    tableData[i][j++] = field.get(obj);
                } catch (IllegalAccessException exc) {
                    System.out.println("Access error");
                    return;
                }
            }
            i++;
        }

        DefaultTableModel table = new DefaultTableModel(tableData, columnNames.toArray()) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 7) return ImageIcon.class;
                return Object.class;
            }
        };

        plantPage.getTableAll().setModel(table);
    }


    private void createMenu() {
        JMenuItem addPlant;
        JMenuItem logIn;
        JMenuItem userManagement;
        JMenuItem plants;
        JMenuItem addUser;
        JMenu menu;
        JMenu langM;
        JMenuItem romana;
        JMenuItem english;
        JMenuItem francais;
        JMenuItem russian;

        HashMap<String, String> languageConfig = this.language.getLanguageConfig();

        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu(languageConfig.get("Menu"));
        langM = new JMenu(languageConfig.get("Language"));
        menu.setMnemonic(KeyEvent.VK_A);
        langM.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);
        menuBar.add(langM);

        logIn = new JMenuItem(languageConfig.get("Log in"));
        logIn.setMnemonic(KeyEvent.VK_D);
        addPlant = new JMenuItem(languageConfig.get("Add plant"));
        addPlant.setMnemonic(KeyEvent.VK_D);
        userManagement = new JMenuItem(languageConfig.get("Manage users"));
        userManagement.setMnemonic(KeyEvent.VK_D);
        plants = new JMenuItem(languageConfig.get("View plants"));
        plants.setMnemonic(KeyEvent.VK_D);
        addUser = new JMenuItem(languageConfig.get("Add user"));
        addUser.setMnemonic(KeyEvent.VK_D);
        romana = new JMenuItem("Română");
        romana.setMnemonic(KeyEvent.VK_D);
        english = new JMenuItem("English");
        english.setMnemonic(KeyEvent.VK_D);
        francais = new JMenuItem("Francais");
        francais.setMnemonic(KeyEvent.VK_D);
        russian = new JMenuItem("Русский");
        russian.setMnemonic(KeyEvent.VK_D);

        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plantPage.dispose();
                LogInPageController logInPageController = new LogInPageController(role, lang);
                logInPageController.getView().setVisible(true);
            }
        });

        addPlant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plantPage.dispose();
                UpdateNewPlantController updateNewPlantController = new UpdateNewPlantController(role, "create", rowToEdit, aux, lang);
                updateNewPlantController.getView().setVisible(true);
            }
        });

        userManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               plantPage.dispose();
               UserPageController userPageController = new UserPageController(role, lang);
               userPageController.getView().setVisible(true);
            }
        });

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plantPage.dispose();
                UpdateNewUserController updateNewUserController = new UpdateNewUserController(role, "create", rowToEdit, null, lang);
                updateNewUserController.getView().setVisible(true);
            }
        });

        menu.add(logIn);
        menu.add(plants);

        if (role.equals("administrator")) {
            menu.add(userManagement);
            menu.add(addUser);
        }
        else if (role.equals("employee")) {
            menu.add(addPlant);
        }

        romana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plantPage.dispose();
                PlantPageController pageController = new PlantPageController(role, "romanian");
                pageController.getView().setVisible(true);
            }
        });

        english.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plantPage.dispose();
                PlantPageController pageController = new PlantPageController(role, "english");
                pageController.getView().setVisible(true);
            }
        });

        francais.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plantPage.dispose();
                PlantPageController pageController = new PlantPageController(role, "french");
                pageController.getView().setVisible(true);
            }
        });

        russian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plantPage.dispose();
                PlantPageController pageController = new PlantPageController(role, "russian");
                pageController.getView().setVisible(true);
            }
        });

        langM.add(romana);
        langM.add(english);
        langM.add(francais);
        langM.add(russian);

        this.plantPage.setJMenuBar(menuBar);
    }

}
