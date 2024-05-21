package model;

public class CSVFactory implements DocumentFactory {
    @Override
    public DocumentWriter factoryMethod() {
        return new CSVWriter();
    }
}
