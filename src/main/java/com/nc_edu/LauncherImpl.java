package com.nc_edu;

import oracle.jdbc.OracleDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Created by anch0317 on 13.04.2017.
 */
public class LauncherImpl implements ILauncher {

    private Connection conn;
    private Logger log = LoggerFactory.getLogger(LauncherImpl.class);

//    @Autowired
//    TUI tui;

    public static void main(String[] args) {
        /*ILauncher launcher = new LauncherImpl();
        TUI tui = new TUIImpl();
        launcher.connect();
        tui.start();
        launcher.disconnect();*/

        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring.xml"});
        TUI tui = (TUI) context.getBean("TUIImpl");
        tui.start();
    }

    @Override
    public void connect() {
        Locale.setDefault(Locale.ENGLISH);
        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(
//                             "jdbc:oracle:thin:@sql.edu-netcracker.com:1251:XE",  //for connection outside NC network
                    "jdbc:oracle:thin:@sqledu.netcracker.com:1251:XE",  //for connection inside NC network
                    "unc17i_achirkov", "2pWzGbXq");
            if (conn.isValid(0)) {
                log.info("Connection established");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            conn.close();
            log.info("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
