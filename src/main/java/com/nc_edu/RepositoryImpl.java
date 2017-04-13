package com.nc_edu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by anch0317 on 13.04.2017.
 */
public class RepositoryImpl implements IRepository {

    private Connection conn;
    private Logger log = LoggerFactory.getLogger(LauncherImpl.class);

    @Override
    public void exit() {

    }

    @Override
    public void select(String arg) {
        /*PreparedStatement st = conn.prepareStatement("SELECT * FROM emp, dept WHERE emp.deptno=dept.deptno AND empno=?");
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
        }*/
    }

    @Override
    public void insert(List<String> params) {

    }

    @Override
    public void delete(String arg) {

    }

    @Override
    public void find(String arg) {

    }
}
