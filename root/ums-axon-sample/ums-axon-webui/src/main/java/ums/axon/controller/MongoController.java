// ============================================================================
//
// Copyright (C) 2014-2015 liushuangyi@126.com
//
// ============================================================================

package ums.axon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ums.axon.init.InitMongoService;

/**
 * invoke mongo db init service from controller
 * 
 * @author liushuangyi@126.com
 */

@Controller
@RequestMapping("/data")
public class MongoController {

    @Autowired
    public InitMongoService initDbService;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String initializeMongo(Model model) {
        initDbService.init();
        model.addAttribute("info", "Mongo database is initialized.");
        return "redirect:/users";
    }
}
