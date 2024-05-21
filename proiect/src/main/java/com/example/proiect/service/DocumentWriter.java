package com.example.proiect.service;

import com.example.proiect.entity.Plant;

import java.util.List;

public interface DocumentWriter {
    public abstract void writeFile(List<Plant> list);
    public abstract void openFile();
}
