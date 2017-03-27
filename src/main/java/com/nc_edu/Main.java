package com.nc_edu;

import java.sql.*;
import java.util.*;
import oracle.jdbc.*;
import static com.nc_edu.SQLUtils.*;

public class Main {

    private static final String EXIT_CMD = "exit";
    private static final String SEL_CMD = "select";
    private static final String INS_CMD = "insert";
    private static final String DEL_CMD = "delete";
    private static final String FIND_CMD = "find";

    public static void main(String[] args) {

        Locale.setDefault(Locale.ENGLISH);
        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn =
                     DriverManager.getConnection(
//                             "jdbc:oracle:thin:@sql.edu-netcracker.com:1251:XE",  //for connection outside NC network
                             "jdbc:oracle:thin:@sqledu.netcracker.com:1251:XE",  //for connection inside NC network
                             "unc17i_achirkov", "2pWzGbXq")) {

            //        System.out.println(conn.getMetaData().getDatabaseProductVersion());

            boolean done = false;

            while (!done) {
                System.out.println("\n================================================" +
                        "\nUse commands: " + EXIT_CMD + ", " + SEL_CMD + ", " + INS_CMD + ", " + DEL_CMD + ", " + FIND_CMD);
                System.out.println("Type command : ");
                Scanner sc = new Scanner(System.in);
                String cmd;
                cmd = sc.next();
                if (EXIT_CMD.equalsIgnoreCase(cmd)) done = true;
                if (SEL_CMD.equalsIgnoreCase(cmd)) {
                    System.out.println("Type id : ");
                    String arg = sc.next();
                    execSelById(arg, conn);
                }
                if (INS_CMD.equalsIgnoreCase(cmd)) {
                    List<String> params = new LinkedList<>();
                    String[] args_names = {"EMPNO", "ENAME", "JOB", "MGR", "SAL", "COMM", "DEPTNO"};
                    Arrays.asList(args_names).forEach(k -> {
                        System.out.println("Type in " + k + " param :");
                        String arg = sc.next();
                        params.add(arg);
                    });
                    execInsert(params, conn);
                }
                if (DEL_CMD.equalsIgnoreCase(cmd)) {
                    System.out.println("Type id : ");
                    String arg = sc.next();
                    execDelById(arg, conn);
                }
                if (FIND_CMD.equalsIgnoreCase(cmd)) {
                    System.out.println("Type dept or name : ");
                    String arg = sc.next();
                    execFind(arg, conn);
                }
            }
        } catch (SQLException e) {
            System.out.println("Could not establish connection to DB");
//            e.printStackTrace();
        }
    }
}
