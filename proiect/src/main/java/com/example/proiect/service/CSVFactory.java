package com.example.proiect.service;

public class CSVFactory implements DocumentFactory {
    @Override
    public DocumentWriter factoryMethod() {
        return new CSVWriter();
    }
}
