package huanju.chen.app.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    private static final Logger logger= LoggerFactory.getLogger(TestController.class);

    @GetMapping("/hello")
    public Map<String,Object> helloLog(){
        logger.info("Hello Log HHH");
        Map<String,Object> map=new HashMap<>();
        map.put("status", HttpStatus.BAD_REQUEST);
        map.put("body",HttpStatus.BAD_REQUEST);
        map.put("headers","hhhhh");



        return map;
    }
}
