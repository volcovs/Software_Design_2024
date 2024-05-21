package com.example.proiect.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.io.IOException;

@RestController
@RequestMapping("/lang")
public class LangController {

    @GetMapping("/get")
    @ResponseBody
    public String getLangConfig(@RequestParam String lang) {
        try {
            JSONParser parser = new JSONParser();
            //Use JSONObject for simple JSON and JSONArray for array of JSON.
            JSONObject data = (JSONObject) parser.parse(
                    new FileReader(lang + ".json"));//path to the JSON file.

            String json = data.toJSONString();
            return json;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return "Error";
    }
}
