package view;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public interface IUserGUI extends IModelGUI{
    public String getUserID();
    public void setUserID(String id);
    public String getPerson();
    public void setPerson(String personID);
    public String getFirstName();
    public void setFirstName(String name);
    public String getLastName();
    public void setLastName(String lastName);
    public void setStatus(String status);
    public String getStatus();
    public String getDOB();
    public void setDOB(String dateOfBirth);
    public String getAdress();
    public void setAddress(String address);
    public String getPhoneNumber();
    public void setPhoneNumber(String phoneNumber);
    public String getEmail();
    public void setEmail(String email);
    public String getUsername();
    public void setUsername(String username);
    public String getPassword();
    public void setPassword(String password);
}
