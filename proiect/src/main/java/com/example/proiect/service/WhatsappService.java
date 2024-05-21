package com.example.proiect.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WhatsappService {


    public void sendMessage(String to, String message) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://graph.facebook.com/v13.0/284659184739150/messages"))
                            .header("Authorization", "Bearer EAAUcViHRkucBO3KbStwtXYMgykYOaEteOZANPZAIcMa2PnhPPim9eEdxc1zbDAaoiprIrd8ysDB495YoDKCD1RyzH3lyUX6ELdMDzUZAYMHAaJBan9xeIfd81YVLF5WkT0ZCxNROuvDkJP4eG7ZBOiLaZCT3q7eMgDRTZBbA3likZC7ZCvLABfwmKkw3ALCxuNHVZCRTr423q3kiYdJR4qwg0ZD")
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString("{\"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\":\"" + to + "\", \"type\":\"text\", \"text\": {\"preview_url\": false, \"body\":\"" + message + "\"}}"))
                            .build();
            HttpClient http = HttpClient.newHttpClient();
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
