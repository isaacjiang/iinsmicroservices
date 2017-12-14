package com.iins.web;

import com.iins.applications.ApplicationModel;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;


@RestController
public class ApplicationsRouter {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/app/application/get",method = RequestMethod.GET,produces = "application/json")
    public String getId(@RequestParam(value="name", defaultValue="World") String name) {
        ApplicationModel am = new ApplicationModel(counter.incrementAndGet(), String.format(template, name));
        JSONObject result = new JSONObject();
        result.put(String.valueOf(am.getId()),am.getContent());
        return result.toString();
    }
}