package huanju.chen.app.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    private static final Logger logger= LoggerFactory.getLogger(TestController.class);

    @Autowired
    ApplicationContext context;

    @GetMapping("/hello")
    public Map<String,Object> helloLog(){
        logger.info("Hello Log HHH");
        Map<String,Object> map=new HashMap<>();
        map.put("headers","hhhhh");

        context.getId();


        return map;
    }


}
