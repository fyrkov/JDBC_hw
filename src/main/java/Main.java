import java.sql.*;
import java.util.*;

import oracle.jdbc.*;


public class Main {

    private static final String EXIT_CMD = "exit";
    private static final String SEL_CMD = "select";
    private static final String UPD_CMD = "update";
    private static final String DEL_CMD = "delete";
    private static final String FIND_CMD = "find";

    public static void main(String[] args) throws SQLException {

        DriverManager.registerDriver(new OracleDriver());
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@sqledu.netcracker.com:1251:XE", "unc17i_achirkov", "2pWzGbXq");
//        System.out.println(conn.getMetaData().getDatabaseProductVersion());

        boolean done = false;

        while (!done) {
            System.out.println("\n================================================" +
                    "\nUse commands: " + EXIT_CMD + ", " + SEL_CMD + ", " + UPD_CMD + ", " + DEL_CMD + ", " + FIND_CMD);
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
            if (UPD_CMD.equalsIgnoreCase(cmd)) {
                Map<String,String> params = new HashMap<>();
                String[] args_names = {"EMPNO", "ENAME", "JOB", "MGR", "HIREDATE", "SAL", "COMM", "DEPTNO"};
                Arrays.asList(args_names).forEach(k -> {
                    System.out.println("Type in " + k + " param :");
                    String arg = sc.next();
                    params.put(k,arg);
                });
                execUpd(params, conn);
            }
            if (DEL_CMD.equalsIgnoreCase(cmd)) {

            }
        }

        conn.close();
    }

    private static void execUpd(Map<String, String> params, Connection conn) {

    }

    private static void execSelById(String cmd, Connection conn) throws SQLException {

        PreparedStatement st = conn.prepareStatement("SELECT * FROM emp, dept WHERE emp.deptno=dept.deptno AND empno=?");
        st.setString(1, cmd);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            StringBuilder result = new StringBuilder();
            result.append("Name: ").append(rs.getString("ENAME"));
            result.append("\nDepartment: ").append(rs.getString("DNAME"));
            result.append("\nPosition: ").append(rs.getString("JOB"));
            //TODO format date
            result.append("\nHire Date: ").append(rs.getString("HIREDATE"));
            System.out.println(result);
        } else System.out.println("row not found");
    }

}
