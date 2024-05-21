package model;

public class XMLFactory implements DocumentFactory {
    @Override
    public DocumentWriter factoryMethod() {
        return new XMLWriter();
    }
}
