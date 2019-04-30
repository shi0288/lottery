package com.mcp.lottery.task;


import com.mcp.lottery.service.TermService;
import com.mcp.lottery.util.cons.Cons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TermTask {

    @Autowired
    private TermService termService;



//    @Scheduled(fixedDelay = 1000)
//    public void resetShishicai() {
//        termService.resetTerm(Cons.Game.CQSSC,5000);
//    }

    @Scheduled(fixedDelay = 1000)
    public void resetFenfencai() {
        termService.resetTerm(Cons.Game.TXFFC,500);
    }

}
