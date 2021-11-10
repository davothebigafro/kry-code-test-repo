package com.vertx.example;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ServicePollerTest {

    @Test
    void pollServices() {
        ServicePoller poller = new ServicePoller();
        HashMap<String, Service> services = new HashMap<>();
        Service google = new Service("https://www.google.com/", "UNKNOWN");
        Service bad_google = new Service("https://www.goo444gle.com/", "UNKNOWN");

        services.put("google", google);
        services.put("bad_google", bad_google);

        poller.pollServices(services);

        assertEquals(services.get("google").getStatus(), "OK");
        assertEquals(services.get("bad_google").getStatus(), "FAIL");
    }
}