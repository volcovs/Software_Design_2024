package com.example.proiect.service;

public class DOCFactory implements DocumentFactory{
    @Override
    public DocumentWriter factoryMethod() {
        return new DOCWriter();
    }
}
