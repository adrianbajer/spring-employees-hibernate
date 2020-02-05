package spring.hibernate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String indexGet() {
        return "/index";
    }

    @RequestMapping("/restoreData")
    public String restoreDatabaseButton() {
        DataSource.restoreDatabase();
        return "/index";
    }

}
