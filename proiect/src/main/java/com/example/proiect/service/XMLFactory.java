package com.example.proiect.service;

public class XMLFactory implements DocumentFactory {
    @Override
    public DocumentWriter factoryMethod() {
        return new XMLWriter();
    }
}
