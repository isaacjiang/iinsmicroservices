package com.iins.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;

public class ScheduleService {


    @Autowired
    private ApplicationContext applicationContext;

    public void start() {

    }

    @Scheduled(initialDelay = 500, fixedDelay = 200000)
    public void snmpServicceSchedule() {

        // System.out.println("SNMP works ... ");
    }


}
