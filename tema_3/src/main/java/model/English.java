package model;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class English implements Language {
    @Override
    public HashMap<String, String> getLanguageConfig() {
        HashMap<String, String> dict = new HashMap<>();
        File file = new File("english.json");

        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                String[] tokens = data.split(":");
                if (tokens.length >= 2) {
                    tokens[0] = tokens[0].strip();
                    tokens[0] = tokens[0].replace("\"", "");
                    tokens[1] = tokens[1].strip();
                    tokens[1] = tokens[1].replace("\"", "");
                    tokens[1] = tokens[1].replace(",", "");

                    dict.put(tokens[0], tokens[1]);
                }
            }
            myReader.close();
        } catch(Exception ex) {
            System.out.println("Some error occured: " + ex.getMessage());
        }

        return dict;
    }
}
