package com.fhx.gdms.defence.service.impl;

import com.fhx.gdms.defence.repository.DefenceRepository;
import com.fhx.gdms.defence.service.DefenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefenceServiceImpl implements DefenceService {

    @Autowired
    private DefenceRepository defenceRepository;
}
