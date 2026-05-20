package com.simeon.picoinspector.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PicoBlockClient {

    private final HttpClient client;

    public PicoBlockClient() {
        this.client = HttpClient.newHttpClient();
    }

    public byte[] fetchBlockData(String blockUrl) throws IOException, InterruptedException {
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(blockUrl))
                .GET()
                .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() == 200) {
            System.out.println("HTTP Status Code: " + response.statusCode());
            return response.body();
        } else {
            throw new IOException("Failed to fetch block data. HTTP status code: " + response.statusCode());
        }
    }
}
