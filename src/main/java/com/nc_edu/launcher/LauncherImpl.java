package com.nc_edu.launcher;

import com.nc_edu.controller.TUI;
import oracle.jdbc.OracleDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Created by anch0317 on 13.04.2017.
 */
public class LauncherImpl implements ILauncher {

    private final Logger log = LoggerFactory.getLogger(LauncherImpl.class);
    private static TUI tui;

    public final void launch() {
        Locale.setDefault(Locale.ENGLISH);
        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring.xml"});
        tui = (TUI) context.getBean("TUIImpl");
        tui.start();
    }

    public static void main(String[] args) {
        (new LauncherImpl()).launch();
    }
}

