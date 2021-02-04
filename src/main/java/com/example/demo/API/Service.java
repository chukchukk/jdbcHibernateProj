package com.example.demo.API;


import com.example.demo.BACK.DBStrategy;
import com.example.demo.BACK.HibernateStrategy;
import com.example.demo.BACK.JdbcTemplateStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    @Qualifier("hibernateStrategy")
    private DBStrategy currentDBStrategy;

    @Autowired
    private ApplicationContext context;

    public ResponseEntity<String> getAll(){
        return currentDBStrategy.getAll();
    }

    public ResponseEntity<String > insert(String json){
        return currentDBStrategy.insert(json);
    }

    public String changeStrategy(String nameStrategy){
        nameStrategy = nameStrategy.toLowerCase();

        if(nameStrategy.contains("jdbc")){
            currentDBStrategy = context.getBean(JdbcTemplateStrategy.class);
        }
        else{
            currentDBStrategy = context.getBean(HibernateStrategy.class);
        }
        return "Current DBStrategy is " + currentDBStrategy.getClass().getSimpleName();
    }
}