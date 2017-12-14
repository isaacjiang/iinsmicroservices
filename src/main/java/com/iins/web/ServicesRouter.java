package com.iins.web;

import com.iins.services.WorkflowService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ServicesRouter {

    private WorkflowService  workflowService = new WorkflowService();
    private final AtomicLong counter = new AtomicLong();
    @RequestMapping(value = "/service/workflow/get_id",method = RequestMethod.GET,produces = "application/json")
    public String getId(@RequestParam(value="id", defaultValue="0") String id) {
        JSONObject result = new JSONObject();
        result.put(id,workflowService.get_id(id));
        return result.toString();
    }
}

