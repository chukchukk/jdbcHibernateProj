package com.example.demo.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class PersonController {
    @Autowired
    private Service service;

    @GetMapping("/findAll")
    public ResponseEntity<String> findAll(){
        return service.getAll();
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestParam String json){
        return service.insert(json);
    }

    @PostMapping("/change")
    public String change(@RequestParam String name){
        return service.changeStrategy(name);
    }
}

