package com.nc_edu;

import org.springframework.context.annotation.Bean;
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

    private IService service = new ServiceImpl();

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
                sc.close();
                //todo release connection, closing app (in launcher)
            }
            if (SEL_CMD.equalsIgnoreCase(cmd)) {
                System.out.println("Type id : ");
                String arg = sc.next();
//                execSelById(arg, conn);
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
//                execInsert(params, conn);
                service.insert(params);
            }
            if (DEL_CMD.equalsIgnoreCase(cmd)) {
                System.out.println("Type id : ");
                String arg = sc.next();
//                execDelById(arg, conn);
                service.delete(arg);
            }
            if (FIND_CMD.equalsIgnoreCase(cmd)) {
                System.out.println("Type dept or name : ");
                String arg = sc.next();
//                execFind(arg, conn);
                service.find(arg);
            }
        }
    }

    @Override
    public void stop() {

    }
}
