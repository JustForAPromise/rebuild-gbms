package com.fhx.gdms.service.departmentLeader.power;

import com.fhx.gdms.service.power.service.PowerService;
import com.fhx.gdms.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/leaderPower")
public class LeaderPowerController {
    @Autowired
    private PowerService powerService;

    @Autowired
    private UserService userService;


}
