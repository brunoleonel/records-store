package br.com.cashback.recordstore.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/records")
public class RecordsController {

    @RequestMapping(method = RequestMethod.GET)
    public String getRecords() {
        return "getRecords";
    }

    @RequestMapping(path = "/{id}")
    public String getRecord(@PathVariable int id) {
        return "getRecord: " + id ;
    }
}
