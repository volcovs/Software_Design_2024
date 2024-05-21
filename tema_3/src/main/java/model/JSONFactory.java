package model;

public class JSONFactory implements DocumentFactory {
    @Override
    public DocumentWriter factoryMethod() {
        return new JSONWriter();
    }
}
