package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String  url = "https://api.nasa.gov/planetary/apod?" +
                "api_key=JfsUe24z2n4Tk9G84lDTzbsKl9RByB1NKormE9Jo";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper mapper = new ObjectMapper();

        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(request);
        Nasa answer = mapper.readValue(response.getEntity().getContent(), Nasa.class);

        HttpGet imageGet = new HttpGet(answer.hdurl);

        String[] urlSplited = answer.url.split("/");
        String filename = urlSplited[urlSplited.length -1];

        CloseableHttpResponse image = httpClient.execute(imageGet);
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        image.getEntity().writeTo(fileOutputStream);
    }
}
