package web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.SuccesskillService;

/**
 * Created by admin on 2016/6/8.
 */
@Controller
@RequestMapping("/successKilled")
public class SuccessKilledController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SuccesskillService successkillService;



}
