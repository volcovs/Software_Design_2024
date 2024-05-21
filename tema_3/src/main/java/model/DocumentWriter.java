package model;

import java.util.List;

public interface DocumentWriter {
    public abstract void writeFile(List<Plant> list);
    public abstract void openFile();
}
