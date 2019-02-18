package com.fhx.gdms.taskbook.service.impl;

import com.fhx.gdms.taskbook.repository.TaskBookRepository;
import com.fhx.gdms.taskbook.service.TaskBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskBookServiceImpl implements TaskBookService {
    @Autowired
    private TaskBookRepository taskBookRepository;
}
