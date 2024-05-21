package view;

import java.awt.event.ActionListener;

public interface ILogInGUI extends IGUI {
    public String accessUsername();
    public String accessPassword();

    public void setCancelListener(ActionListener actionListener);
    public void setLogInListener(ActionListener actionListener);
}
