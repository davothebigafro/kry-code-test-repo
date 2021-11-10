package com.vertx.example;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

import java.io.*;
import java.net.URLDecoder;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

// Main verticle class to start server
public class ServerVerticle extends AbstractVerticle{

    private HashMap<String, Service> serviceMap = new HashMap<>();
    private UrlJSONHelper urlhelper;
    private ServicePoller poller;
    private static final String changelog_file_name = "changelog.txt";

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
        urlhelper = new UrlJSONHelper(serviceMap);
        poller = new ServicePoller();

        // Create new file to store services if doesn't exist
        File url_file = new File(urlhelper.url_file_name);
        if(!url_file.isFile()) {
            try {
                url_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Create new file to store services if doesn't exist
        File changelog = new File(changelog_file_name);
        if(!changelog.isFile()) {
            try {
                changelog.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        urlhelper.readUrlsFromJSONFile();
    }

    @Override
    public void start(Future<Void> fut) {

        HttpServer httpServer = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(io.vertx.core.http.HttpMethod.GET)
                .allowedMethod(io.vertx.core.http.HttpMethod.POST)
                .allowedMethod(io.vertx.core.http.HttpMethod.DELETE)
                .allowedMethod(io.vertx.core.http.HttpMethod.OPTIONS)
                .allowedHeader("Access-Control-Request-Method")
                .allowedHeader("Access-Control-Allow-Credentials")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("Access-Control-Allow-Headers")
                .allowedHeader("Content-Type"));


        // Initialise get, post, delete routes
        initialiseGetRoute(router);
        initialisePostRoute(router);
        initialiseDeleteRoute(router);
        initialiseGetLatestChangeRoute(router);

        // poll services periodically
        vertx.setPeriodic(1000, id -> {
            poller.pollServices(serviceMap);
            UrlJSONHelper urlhelper = new UrlJSONHelper(serviceMap);
            urlhelper.writeUrlsToJSONFile();
        });

        httpServer.requestHandler(router::accept).listen(8080);
    }

    // Handle post request
    private void initialisePostRoute(Router router) {
        router.post("/addservice").handler(BodyHandler.create());
        router.post("/addservice").handler(routing_context -> {
            JsonObject jsonBody = routing_context.getBodyAsJson();

            // Get the url name and body
            String url_body = jsonBody.getString("url");
            String url_name = jsonBody.getString("name");

            HttpServerResponse response = routing_context.response();
            response.putHeader("content-type", "text/plain");
            response.end("Added " + url_name);

            // Add the new service with this name and url
            serviceMap.put(url_name, new Service(url_body, "OK"));

            String change = url_name + " was added";
            writeToChangeLog(change);
        });
    }

    // Handle delete request
    private void initialiseDeleteRoute(Router router) {
        router.delete("/deleteservice/:id").handler(BodyHandler.create());
        router.delete("/deleteservice/:id").handler(routing_context -> {
            try {
                String url_name = URLDecoder.decode(routing_context.request().getParam("id"), "UTF-8");
                serviceMap.remove(url_name);
                HttpServerResponse response = routing_context.response();
                response.putHeader("content-type", "text/plain");
                response.end("Deleted" + url_name);

                String change = url_name + " was deleted";
                writeToChangeLog(change);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
    }

    private void initialiseGetRoute(Router router) {
        router.get("/services").handler(BodyHandler.create());
        router.get("/services").handler(routing_context -> {
            JsonArray serviceList = urlhelper.getServiceList(serviceMap);
            HttpServerResponse response = routing_context.response();
            response.putHeader("content-type", "application/json");
            response.end(serviceList.encode());
            System.out.println("Successfully got services.");
        });

    }

    private void initialiseGetLatestChangeRoute(Router router) {
        router.get("/latestchange").handler(BodyHandler.create());
        router.get("/latestchange").handler(routing_context -> {
            HttpServerResponse response = routing_context.response();
            response.putHeader("content-type", "text/plain");

            BufferedReader input = null;
            try {
                input = new BufferedReader(new FileReader(changelog_file_name));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String last = "", line = "";

            while (true) {
                try {
                    if (!((line = input.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                last = line;
            }
            response.end(last);
            System.out.println("Successfully got latest change.");
        });

    }


    private void writeToChangeLog(String change) {
        try {
            FileWriter myWriter = new FileWriter(changelog_file_name, true);
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

            // I.e. 'change' was added at 'time'
            String change_with_time = change + " at " + timeStamp;
            myWriter.write(change_with_time);
            myWriter.write("\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}


