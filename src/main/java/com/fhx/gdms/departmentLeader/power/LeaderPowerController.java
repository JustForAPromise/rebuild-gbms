package com.fhx.gdms.departmentLeader.power;

import com.fhx.gdms.power.service.PowerService;
import com.fhx.gdms.user.service.UserService;
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
