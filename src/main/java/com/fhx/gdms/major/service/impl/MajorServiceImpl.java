package com.fhx.gdms.major.service.impl;

import com.fhx.gdms.major.repository.MajorRepository;
import com.fhx.gdms.major.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    private MajorRepository majorRepository;
}
