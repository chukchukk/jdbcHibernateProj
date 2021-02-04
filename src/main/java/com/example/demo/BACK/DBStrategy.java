package com.example.demo.BACK;

import com.example.demo.MODEL.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DBStrategy {
    ResponseEntity<String> getAll();
    ResponseEntity<String> insert(String json);
}
