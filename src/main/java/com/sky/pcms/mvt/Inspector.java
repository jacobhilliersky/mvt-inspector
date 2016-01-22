package com.sky.pcms.mvt;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import spark.ResponseTransformer;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.valueOf;
import static spark.Spark.*;

public class Inspector {

    private static final JsonTransformer JSON_TRANSFORMER = new JsonTransformer();

    public static void main(String[] args) {
        setPort();

        staticFileLocation("/public");

        get("/", (request, response) -> {
            Map<String, String> headers = new HashMap<>();
            request.headers().stream().forEach(key -> headers.put(key, request.headers(key)));

            Map<String, Object> attributes = new HashMap<>();
            request.attributes().forEach(key -> attributes.put(key, request.attribute(key)));

            Map<String, String> queryParams = new HashMap<>();
            request.queryParams().forEach(key -> queryParams.put(key, request.queryParams(key)));

            Map<String, Object> details = new HashMap<>();
            details.put("headers", headers);
            details.put("attributes", attributes);
            details.put("queryParams", queryParams);
            details.put("cookies", request.cookies());
            details.put("body", request.body());
            details.put("scheme", request.scheme());
            details.put("url", request.url());
            details.put("uri", request.uri());
            details.put("host", request.host());
            details.put("ip", request.ip());
            details.put("port", request.port());

            return details;
        }, JSON_TRANSFORMER);

        get("/signup", (request, response) -> {
            return ImmutableMap.of("buttonClass", "btn-info");
        }, JSON_TRANSFORMER);

        get("/signup-variant-1", (request, response) -> {
            return ImmutableMap.of("buttonClass", "btn-success");
        }, JSON_TRANSFORMER);

        get("/signup-variant-2", (request, response) -> {
            return ImmutableMap.of("buttonClass", "btn-danger");
        }, JSON_TRANSFORMER);

        get("/sports", (request, response) -> {
            return ImmutableMap.of("imageUrl", "https://d2kmm3vx031a1h.cloudfront.net/JIZoEV6gQOSNf6CMBT5q_485185692.jpg");
        }, JSON_TRANSFORMER);

        get("/sports-variant-1", (request, response) -> {
            return ImmutableMap.of("imageUrl", "https://d2kmm3vx031a1h.cloudfront.net/r8xt9OyFTGKWqO3TpqP5_501242676.jpg");
        }, JSON_TRANSFORMER);

        get("/sports-variant-2", (request, response) -> {
            return ImmutableMap.of("imageUrl", "https://d2kmm3vx031a1h.cloudfront.net/DgSxHj5uSt2Xk0UPLq43_452936468.jpg");
        }, JSON_TRANSFORMER);
    }

    private static void setPort() {
        String portSystemProperty = System.getProperty("server.port");
        if (portSystemProperty != null) {
            port(valueOf(portSystemProperty));
        }
    }

    private static class JsonTransformer implements ResponseTransformer {

        private Gson gson = new Gson();

        @Override
        public String render(Object model) {
            return gson.toJson(model);
        }
    }
}
