package com.iins.applications;


import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicLong;


public class ApplicationController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    public String getId(String name) {
        ApplicationModel am = new ApplicationModel(counter.incrementAndGet(), String.format(template, name));
        JSONObject result = new JSONObject();
        result.put(String.valueOf(am.getId()),am.getContent());
        return result.toString();
    }
}