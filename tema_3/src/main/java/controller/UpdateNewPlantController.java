package controller;

import model.*;
import model.repository.PlantRepository;
import view.PlantPage;
import view.UpdateNewPlant;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;

public class UpdateNewPlantController {
    private UpdateNewPlant updateNewPlant;
    private String role;
    private String lang;
    private Language language;
    private String path;


    public UpdateNewPlantController(String role, String operation, String id, PlantPageController toUpdate, String lang) {
        this.role = role;
        this.updateNewPlant = new UpdateNewPlant();
        this.lang = lang;

        switch (lang) {
            case "romanian" -> { this.language = new Romanian();  }
            case "english" -> { this.language = new English();  }
            case "french" -> { this.language = new French();  }
            case "russian" -> { this.language = new Russian(); }
            default -> { System.out.println("Not a valid language choice");}
        }

        HashMap<String, String> languageConfig = this.language.getLanguageConfig();

        if (operation.equals("update")) {
            PlantRepository plantRepository = new PlantRepository("gradina_botanica", toUpdate);
            Plant plant = plantRepository.searchPlant(id);

            this.updateNewPlant.getTextFieldName().setText(plant.getPlantName());
            this.updateNewPlant.getTextFieldSpecies().setText(plant.getSpecies());
            this.updateNewPlant.getTextFieldType().setText(plant.getPlantType());
            this.updateNewPlant.getTextFieldCarnivorous().setText(plant.getCarnivorous());
            this.updateNewPlant.getTextFieldEndangered().setText(plant.getEndangered());
            this.updateNewPlant.getTextFieldLocation().setText(plant.getLocation());
            this.updateNewPlant.getImageLabel().setIcon(plant.getImg());
            this.updateNewPlant.getImageLabel().setText("");
        }
        else {
            this.updateNewPlant.getImageLabel().setText(languageConfig.get("Choose"));
            this.updateNewPlant.getImageLabel().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JFileChooser chooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            "JPG & GIF Images", "jpg", "gif");
                    chooser.setFileFilter(filter);
                    int returnVal = chooser.showOpenDialog(updateNewPlant);
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        File image = chooser.getSelectedFile();
                        image.renameTo(new File("C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/images/" + chooser.getSelectedFile().getName()));
                        path = "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/images/" + chooser.getSelectedFile().getName();
                        System.out.println(path);
                        ImageIcon imageicon = new ImageIcon(path);
                        Image aux = imageicon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        imageicon = new ImageIcon(aux);

                        updateNewPlant.getImageLabel().setIcon(imageicon);
                        updateNewPlant.getImageLabel().setText("");
                    }
                }
            });
        }

        this.updateNewPlant.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlantPageController plantPageController = new PlantPageController("employee", lang);
                plantPageController.getView().setVisible(true);
                updateNewPlant.dispose();
            }
        });

        this.updateNewPlant.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Plant newPlant = validInfo();

                if (newPlant != null) {
                    PlantRepository plantRepository = new PlantRepository("gradina_botanica", toUpdate);
                    if (operation.equals("update")) {
                        if (plantRepository.updatePlant(id, newPlant)) {
                            updateNewPlant.setMessage(languageConfig.get("Success msg"));
                        }
                        else {
                            updateNewPlant.setMessage(languageConfig.get("Fail msg"));
                        }
                    } else {
                        System.out.println(path);
                        if (plantRepository.addPlant(newPlant, path)) {
                            updateNewPlant.setMessage(languageConfig.get("Success msg"));
                        }
                        else {
                            updateNewPlant.setMessage(languageConfig.get("Fail msg"));
                        }
                    }

                    PlantPageController plantPageController = new PlantPageController("employee", lang);
                    plantPageController.getView().setVisible(true);
                    updateNewPlant.dispose();
                }
            }
        });

        updateNewPlant.getNameLabel().setText(languageConfig.get("Plant name") + ":");
        updateNewPlant.getSpeciesLabel().setText(languageConfig.get("Plant species") + ":");
        updateNewPlant.getTypeLabel().setText(languageConfig.get("Plant type") + ":");
        updateNewPlant.getCarnivLabel().setText(languageConfig.get("Plant alim") + ":");
        updateNewPlant.getEndangLabel().setText(languageConfig.get("Plant end") + ":");
        updateNewPlant.getLocLabel().setText(languageConfig.get("Plant location") + ":");
        updateNewPlant.getSaveButton().setText(languageConfig.get("Save"));
        updateNewPlant.getCancelButton().setText(languageConfig.get("Cancel"));
        updateNewPlant.setTitle(languageConfig.get("Plant page"));

    }

    public UpdateNewPlant getView() {
        this.updateNewPlant.setContentPane(this.updateNewPlant.getMainPanel());

        this.updateNewPlant.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.updateNewPlant.setSize(800, 600);

        return this.updateNewPlant;
    }


    private Plant validInfo() {
        String plantName = updateNewPlant.getTextFieldName().getText();
        if (plantName == null || plantName.length() == 0) {
            return null;
        }

        String species = updateNewPlant.getTextFieldSpecies().getText();
        if (species == null || species.length() == 0) {
            return null;
        }

        String type = updateNewPlant.getTextFieldType().getText();
        if (type == null || type.length() == 0) {
            return null;
        }

        String carnivorous = updateNewPlant.getTextFieldCarnivorous().getText();
        if (carnivorous == null || carnivorous.length() == 0) {
            carnivorous = "no";
        }

        String endangered = updateNewPlant.getTextFieldEndangered().getText();
        if (endangered == null || endangered.length() == 0) {
            endangered = "no";
        }

        String location = updateNewPlant.getTextFieldLocation().getText();
        if (location == null || location.length() == 0) {
            return null;
        }

        Icon img = updateNewPlant.getImageLabel().getIcon();
        BufferedImage bi = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        img.paintIcon(null, g, 0, 0);
        g.setColor(Color.WHITE);
        g.drawString(updateNewPlant.getImageLabel().getText(), 10, 20);
        g.dispose();

        byte[] bytes = new byte[0];
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", os);
            InputStream fis = new ByteArrayInputStream(os.toByteArray());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum);
            }

            bytes = bos.toByteArray();
        } catch (IOException ex) {
            System.out.println("Error reading image");
        }

        return new Plant("1", plantName, species, type, carnivorous, endangered, location, bytes);
    }


}
