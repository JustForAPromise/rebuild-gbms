package com.fhx.gdms.taskbook.controllers;

import com.fhx.gdms.taskbook.service.TaskBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/taskBook")
public class TaskBookController {
    @Autowired
    private TaskBookService taskBookService;
}
