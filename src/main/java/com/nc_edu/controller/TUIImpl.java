package com.nc_edu.controller;

import com.nc_edu.service.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by anch0317 on 13.04.2017.
 */
@Component
public class TUIImpl implements TUI {

    @Autowired
    private IService service;
    private Logger log = LoggerFactory.getLogger(TUIImpl.class);

    private static final String EXIT_CMD = "exit";
    private static final String SEL_CMD = "select";
    private static final String INS_CMD = "insert";
    private static final String DEL_CMD = "delete";
    private static final String FIND_CMD = "find";

    @Override
    public void start() {

        boolean done = false;

        while (!done) {
            System.out.println("\n================================================" +
                    "\nUse commands: " + EXIT_CMD + ", " + SEL_CMD + ", " + INS_CMD + ", " + DEL_CMD + ", " + FIND_CMD);
            System.out.println("Type command : ");
            Scanner sc = new Scanner(System.in);
            String cmd;
            cmd = sc.next();
            if (EXIT_CMD.equalsIgnoreCase(cmd)) {
                done = true;
                service.exit();
                sc.close();
            }
            if (SEL_CMD.equalsIgnoreCase(cmd)) {
                System.out.println("Type id : ");
                String arg = sc.next();
                service.select(arg);
            }
            if (INS_CMD.equalsIgnoreCase(cmd)) {
                List<String> params = new LinkedList<>();
                String[] args_names = {"EMPNO", "ENAME", "JOB", "MGR", "SAL", "COMM", "DEPTNO"};
                Arrays.asList(args_names).forEach(k -> {
                    System.out.println("Type in " + k + " param :");
                    String arg = sc.next();
                    params.add(arg);
                });
                service.insert(params);
            }
            if (DEL_CMD.equalsIgnoreCase(cmd)) {
                System.out.println("Type id : ");
                String arg = sc.next();
                service.delete(arg);
            }
            if (FIND_CMD.equalsIgnoreCase(cmd)) {
                System.out.println("Type dept or name : ");
                String arg = sc.next();
                service.find(arg);
            }
        }
    }

    @Override
    public void stop() {

    }
}
