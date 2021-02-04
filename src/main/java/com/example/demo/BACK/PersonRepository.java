package com.example.demo.BACK;

import com.example.demo.MODEL.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
