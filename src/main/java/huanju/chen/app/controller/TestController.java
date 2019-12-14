package huanju.chen.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static final Logger logger= LoggerFactory.getLogger(TestController.class);

    @GetMapping("/hello")
    public String helloLog(){
        logger.info("Hello Log HHH");


        return "ok";
    }
}
