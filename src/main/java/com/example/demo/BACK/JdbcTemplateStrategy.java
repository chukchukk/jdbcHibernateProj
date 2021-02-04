package com.example.demo.BACK;

import com.example.demo.MODEL.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcTemplateStrategy implements DBStrategy {
    @Autowired
    private JdbcTemplate template;

    @Override
    public ResponseEntity<String> getAll(){
        List<Person> list = template.query("select * from persons", new BeanPropertyRowMapper<>(Person.class));
        ObjectMapper mapper = new ObjectMapper();
        try {
            if(!list.isEmpty())
                return ResponseEntity.ok(mapper.writeValueAsString(list));
            else
                throw new JsonProcessingException("This table is empty"){};

        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_GATEWAY);
        }
    }

    @Override
    public ResponseEntity<String> insert(String json){
        ObjectMapper mapper = new ObjectMapper();
        List<Person> listPersons = null;
        try {

            listPersons = mapper.readValue(json, new TypeReference<>() {});

        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_GATEWAY
            );
        }

        for(Person person: listPersons) {
            template.execute("insert into persons (email, first_name, gender, second_name) values('" + person.getEmail()
                    + "','" + person.getFirstName() + "','" + person.getGender() + "','" + person.getSecondName() + "')");
        }
        return ResponseEntity.ok("successfully");
    }
}
