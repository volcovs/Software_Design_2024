package com.example.proiect.controller;


import com.example.proiect.entity.Plant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import com.example.proiect.service.PlantService;

import java.io.*;
import java.util.List;
import java.util.Observable;
import java.util.Optional;

@RestController
@RequestMapping("/plant")
public class PlantController extends Observable {
    @Autowired
    private PlantService plantService;
    private String format;

    public String getFormat() {
        return format;
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Plant> plantList() {
        return this.plantService.plantList();
    }

    @GetMapping("/sort")
    @ResponseBody
    public List<Plant> plantListSorted(@RequestParam String sortBy) {
        return this.plantService.plantListSorted(sortBy);
    }

    @GetMapping("/findByName")
    @ResponseBody
    public Plant findByName(@RequestParam String name) {
        return this.plantService.findByNameOrSpecies(name);
    }

    @GetMapping("/filter")
    @ResponseBody
    public List<Plant> filterList(@RequestParam String criterion, Optional<String> value) {
        return this.plantService.filterList(criterion, value);
    }

    @GetMapping("/save")
    @ResponseBody
    public void saveToFile(HttpServletRequest request, HttpServletResponse response, @RequestParam String format) throws IOException {
        this.format = format;
        this.addObserver(plantService);
        synchronized (this) {
            notify();
        }
        //nu se va face explicit apelul, deoarece exista Observer
        //this.plantService.saveToFile(format);
        String EXTERNAL_FILE_PATH = "plantFile.";
        File file = new File(EXTERNAL_FILE_PATH + format);
        if (file.exists()) {
            //String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            String mimeType = null;
            if (mimeType == null) {
                //unknown mimetype so set the mimetype to application/octet-stream
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

            //Here we have mentioned it to show as attachment
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

            response.setContentLength((int) file.length());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
    }

    @GetMapping("/findById")
    @ResponseBody
    public Plant findById(@RequestParam Integer id) {
        return this.plantService.findById(id);
    }

    @PostMapping("/add")
    @ResponseBody
    public Plant addPlant(@RequestBody Plant plant) {
        return this.plantService.addPlant(plant);
    }

    @PutMapping("/update")
    @ResponseBody
    public Plant updatePlant(@RequestBody Plant plant) {
        return this.plantService.updatePlant(plant);
    }

    @DeleteMapping("/deleteById")
    @ResponseBody
    public String deleteById(@RequestParam Integer id) {
        return this.plantService.deletePlant(id);
    }

    @PostMapping("/photo")
    public ResponseEntity<String> uploadPhoto(@RequestParam("id") String id, @RequestParam("file") String file) {
        return ResponseEntity.ok().body(this.plantService.uploadPhoto(Integer.valueOf(id), file));
    }

    @GetMapping("/pie")
    @ResponseBody
    public List<String> pieChartData() {
        return this.plantService.pieChart();
    }

    @GetMapping("/bar1")
    @ResponseBody
    public List<String> firstBarChartData() {
        return this.plantService.firstBarChart();
    }

    @GetMapping("/bar2")
    @ResponseBody
    public List<String> secondBarChartData() {
        return this.plantService.secondBarChart();
    }
}
