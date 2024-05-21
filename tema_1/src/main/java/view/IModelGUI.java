package view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public interface IModelGUI extends IGUI {
    public void setTableListener(MouseAdapter adapter);
    public void setTableContent(JTable table);
    public JPanel getMainPanel();
    public void setCancelListener(ActionListener actionListener);
    public void setSaveListener(ActionListener actionListener);
    public String getRole();
    public Integer getSelectedRow();
    public void setSelectedRow(MouseEvent e);
    public String getSelectedID();
    public void setEditListener(ActionListener actionListener);
}
