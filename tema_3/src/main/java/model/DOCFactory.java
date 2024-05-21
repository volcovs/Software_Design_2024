package model;

public class DOCFactory implements DocumentFactory{
    @Override
    public DocumentWriter factoryMethod() {
        return new DOCWriter();
    }
}
