package com.roadm.manager.controller;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by IntelliJ IDEA.
 *
 * @author: zhangshuaibo
 * Date: 2019-06-10
 * Time: 10:08
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {
    private Logger log = Logger.getLogger(IndexController.class);

}
