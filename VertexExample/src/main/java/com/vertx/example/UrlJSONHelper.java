package com.vertx.example;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;


public class UrlJSONHelper {


    private HashMap<String, Service> serviceMap;
    public static final String url_file_name = "urls.json";

    public UrlJSONHelper(HashMap<String, Service> serviceMap) {
        this.serviceMap = serviceMap;
    }

    public void readUrlsFromJSONFile() {
        Path path = Paths.get(url_file_name);

        try {
            String content = new String(Files.readAllBytes(path));
            if (content.isEmpty()) {
                return;
            }
            JsonArray service_list = new JsonArray(content);
            service_list.forEach(service -> parseServiceObject((JsonObject) service));
        }

        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + url_file_name + "'");
        }

    }

    private void parseServiceObject(JsonObject service) {

        //Get service name
        String name = (String) service.getString("name");

        //Get service url
        String url = (String) service.getString("url");

        //Get service status
        String status = (String) service.getString("status");

        serviceMap.put(name, new Service(url, status));
    }


    public void writeUrlsToJSONFile() {
        JsonArray serviceList = getServiceList(serviceMap);
        try {
            FileWriter myWriter = new FileWriter(url_file_name);
            myWriter.write(serviceList.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Get a JSONArray of services out of the hashmap
    public JsonArray getServiceList(HashMap<String, Service> map) {
        JsonArray serviceList = new JsonArray();
        map.forEach((name, service) -> {
            JsonObject service_details = new JsonObject();
            service_details.put("name", name);
            service_details.put("url", service.getUrl());
            service_details.put("status", service.getStatus());
            JsonObject serviceObject = new JsonObject();
            serviceObject.put("service", service_details);
            serviceList.add(service_details);
        });
        return serviceList;
    }

}
