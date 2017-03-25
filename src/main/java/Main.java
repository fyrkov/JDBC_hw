import java.sql.*;
import java.util.*;

import oracle.jdbc.*;


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
                             "jdbc:oracle:thin:@sql.edu-netcracker.com:1251:XE",
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
            e.printStackTrace();
        }

    }

    private static void execFind(String arg, Connection conn) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT * FROM emp JOIN dept ON emp.deptno=dept.deptno WHERE dept.dname LIKE ? OR emp.ename LIKE ?");
        st.setString(1, "%" + arg.toUpperCase() + "%");
        st.setString(2, "%" + arg.toUpperCase() + "%");
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            do {
                StringBuilder result = new StringBuilder();
                result.append("Id: ").append(rs.getString("EMPNO"));
                result.append("\nName: : ").append(rs.getString("ENAME"));
                result.append("\nDepartment: ").append(rs.getString("DNAME"));
                result.append("\nPosition: ").append(rs.getString("JOB"));
                result.append("\n ----------");
                System.out.println(result);
            } while (rs.next());
        } else System.out.println("rows not found");
    }

    private static void execDelById(String arg, Connection conn) throws SQLException {

        PreparedStatement st = conn.prepareStatement("DELETE FROM emp WHERE empno = ?");
        st.setString(1, arg);
        try {
            int rs = st.executeUpdate();
            System.out.println(rs + " row deleted");
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("invalid params");
        }
    }

    private static void execInsert(List<String> params, Connection conn) throws SQLException {

        PreparedStatement st = conn.prepareStatement("INSERT INTO emp VALUES (?,?,?,?,NULL,?,?,?)");
        for (int i = 0; i < params.size(); i++) {
            st.setObject(i + 1, params.get(i));
        }
        try {
            int rs = st.executeUpdate();
            System.out.println(rs + " rows affected");
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("invalid params");
        }
    }

    private static void execSelById(String arg, Connection conn) throws SQLException {

        PreparedStatement st = conn.prepareStatement("SELECT * FROM emp, dept WHERE emp.deptno=dept.deptno AND empno=?");
        st.setString(1, arg);
        try {
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                StringBuilder result = new StringBuilder();
                result.append("Id : ").append(rs.getString("EMPNO"));
                result.append("\nName: ").append(rs.getString("ENAME"));
                result.append("\nDepartment: ").append(rs.getString("DNAME"));
                result.append("\nPosition: ").append(rs.getString("JOB"));
                System.out.println(result);
            } else System.out.println("row not found");
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("invalid params");
        }
    }

}
