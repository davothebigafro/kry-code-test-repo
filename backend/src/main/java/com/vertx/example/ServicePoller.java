package com.vertx.example;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;

public class ServicePoller {

    public void pollServices(HashMap<String, Service> services) {
        services.forEach((name, service) -> {
                    String url = service.getUrl();
                    boolean reachable = false;
            try {
                final URLConnection connection = new URL(url).openConnection();
                connection.connect();
                reachable = true;
            } catch (final MalformedURLException e) {
                throw new IllegalStateException("Bad URL: " + url, e);
            } catch (final IOException e) {
                reachable = false;
            }
            if (reachable) {
                service.setStatus("OK");
                System.out.println(name + " reachable");
            }
            else {
                service.setStatus("FAIL");
                System.out.println(name + " not reachable");
            }
                }
        );

    }
}