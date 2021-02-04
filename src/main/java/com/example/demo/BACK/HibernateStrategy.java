package com.example.demo.BACK;

import com.example.demo.MODEL.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HibernateStrategy implements DBStrategy {
    @Autowired
    private PersonRepository repository;

    @Override
    public ResponseEntity<String> getAll(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Person> listPersons = (List<Person>) repository.findAll();

            if(!listPersons.isEmpty())
                return ResponseEntity.ok(mapper.writeValueAsString(listPersons));
            else
                throw new JsonProcessingException("This table is empty"){};

        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_GATEWAY);
        }
    }

    @Override
    public ResponseEntity<String> insert(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Person> list = null;
        try {
            list = objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_GATEWAY
            );
        }
        repository.saveAll(list);
        return ResponseEntity.ok("successfully");
    }
}
