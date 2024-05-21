package view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public interface IPlantGUI extends IModelGUI{
    public String getPlantID();
    public String getPlantName();
    public void setPlantName(String name);
    public String getSpecies();
    public void setSpecies(String species);
    public String getPlantType();
    public void setPlantType(String type);
    public String getCarnivorous();
    public void setCarnivorous(String carnivorous);
    public String getEndangered();
    public void setEndangered(String endangered);
    public String getPlantLocation();
    public void setPlantLocation(String location);
    public String getSelectedCriterion();
    public String getSelectedFilter();
    public String getSearchedInformation();
    public void setSearched(String text);
    public void setEditButton(boolean value);
    public void setSearchListener(ActionListener actionListener);
    public void setFilterListener(ActionListener actionListener);
    public void setSortListener(ActionListener actionListener);
}
