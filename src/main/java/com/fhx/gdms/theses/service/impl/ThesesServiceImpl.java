package com.fhx.gdms.theses.service.impl;

import com.fhx.gdms.theses.repository.ThesesRepository;
import com.fhx.gdms.theses.service.ThesesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThesesServiceImpl implements ThesesService {
    @Autowired
    private ThesesRepository thesesRepository;
}
