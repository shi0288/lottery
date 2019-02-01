package com.mcp.lottery.task;


import com.mcp.lottery.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TermTask {

    @Autowired
    private TermService termService;



    @Scheduled(fixedDelay = 1000)
    public void resetTerm() {
        termService.resetTerm();
    }

}
