package com.nc_edu.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.*;
import java.util.List;

/**
 * Created by anch0317 on 13.04.2017.
 */
@Component
@PropertySource("classpath:db.properties")
public class RepositoryImpl implements IRepository {

    private Connection conn;
    private final Logger log = LoggerFactory.getLogger(RepositoryImpl.class);

    @Value("${db_host}")
    private String db_host;
    @Value("${db_user}")
    private String db_user;
    @Value("${db_password}")
    private String db_password;

    @PostConstruct
    final void init() {
        try {
            conn = DriverManager.getConnection(db_host, db_user, db_password);
            if (conn.isValid(0)) {
                log.info("Connection established");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    void destroy() {
        try {
            conn.close();
            log.info("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exit() {
        destroy();
    }

    @Override
    public void select(String arg) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM emp, dept WHERE emp.deptno=dept.deptno AND empno=?");
            st.setString(1, arg);
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
            st.close();
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("invalid params");
        }
    }

    @Override
    public void insert(List<String> params) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO emp VALUES (?,?,?,?,NULL,?,?,?)");
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }
            int rs = st.executeUpdate();
            st.close();
            System.out.println(rs + " rows affected");
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("invalid params");
        }
    }

    @Override
    public void delete(String arg) {
        try {
            PreparedStatement st = conn.prepareStatement("DELETE FROM emp WHERE empno = ?");
            st.setString(1, arg);
            int rs = st.executeUpdate();
            System.out.println(rs + " row deleted");
            st.close();
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("invalid params");
        }
    }

    @Override
    public void find(String arg) {
        try {
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
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
