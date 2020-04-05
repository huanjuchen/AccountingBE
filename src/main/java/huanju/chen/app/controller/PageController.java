package huanju.chen.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author HuanJu
 */
@Controller
public class PageController {

    @RequestMapping({"/","/index"})
    public String toIndex(){
        return "index";
    }

}
