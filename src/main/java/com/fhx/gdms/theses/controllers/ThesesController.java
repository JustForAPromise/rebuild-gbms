package com.fhx.gdms.theses.controllers;

import com.fhx.gdms.theses.service.ThesesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/theses")
public class ThesesController {
    @Autowired
    private ThesesService thesesService;
}
