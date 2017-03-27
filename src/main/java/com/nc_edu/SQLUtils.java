package com.nc_edu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by anch0317 on 27.03.2017.
 */
final class SQLUtils {

    private SQLUtils() {
    }

    static void execFind(String arg, Connection conn) throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "SELECT * FROM emp JOIN dept ON emp.deptno=dept.deptno " +
                        "WHERE dept.dname LIKE ? OR emp.ename LIKE ?");
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
        rs.close();
    }

    static void execDelById(String arg, Connection conn) throws SQLException {

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

    static void execInsert(List<String> params, Connection conn) throws SQLException {

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

    static void execSelById(String arg, Connection conn) throws SQLException {

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
            rs.close();
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("invalid params");
        }
    }
}
