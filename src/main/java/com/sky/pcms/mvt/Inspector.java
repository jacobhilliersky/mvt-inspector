package com.sky.pcms.mvt;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import spark.ResponseTransformer;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static spark.Spark.get;
import static spark.Spark.port;

public class Inspector {

    private static final JsonTransformer JSON_TRANSFORMER = new JsonTransformer();

    public static void main(String[] args) {
        setPort();

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
            String buttonColour = "blue";
            if (equalsIgnoreCase(request.headers("exp_1"), "var_a")) {
                buttonColour = "red";
            } else if (equalsIgnoreCase(request.headers("exp_1"), "var_b")) {
                buttonColour = "green";
            }

            return ImmutableMap.of("buttonColour", buttonColour);
        }, JSON_TRANSFORMER);

        get("/sports", (request, response) -> {
            String imageUrl = "https://d2kmm3vx031a1h.cloudfront.net/JIZoEV6gQOSNf6CMBT5q_485185692.jpg";
            if (equalsIgnoreCase(request.headers("exp_2"), "var_a")) {
                imageUrl = "https://d2kmm3vx031a1h.cloudfront.net/r8xt9OyFTGKWqO3TpqP5_501242676.jpg";
            } else if (equalsIgnoreCase(request.headers("exp_2"), "var_b")) {
                imageUrl = "https://d2kmm3vx031a1h.cloudfront.net/DgSxHj5uSt2Xk0UPLq43_452936468.jpg";
            }

            return ImmutableMap.of("imageUrl", imageUrl);
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
