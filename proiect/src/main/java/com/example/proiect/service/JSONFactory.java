package com.example.proiect.service;

public class JSONFactory implements DocumentFactory {
    @Override
    public DocumentWriter factoryMethod() {
        return new JSONWriter();
    }
}
