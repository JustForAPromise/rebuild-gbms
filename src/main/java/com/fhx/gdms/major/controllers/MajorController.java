package com.fhx.gdms.major.controllers;

import com.fhx.gdms.major.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/major")
public class MajorController {

    @Autowired
    private MajorService majorService;
}
