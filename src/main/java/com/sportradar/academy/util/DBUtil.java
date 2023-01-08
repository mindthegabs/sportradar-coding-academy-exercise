package com.sportradar.academy.util;

import com.sportradar.academy.exception.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class provides functionality to open and close the connection to the database
 */
public class DBUtil {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static Connection con = null;


    /**
     * Opens and returns connection to the database
     *
     * @return opened connection
     * @throws PersistenceException if an error caused by the database occurs
     */
    public static Connection getConnection() throws PersistenceException {
        if (con == null){
            con = openConnection();
        }
        return con;
    }

    private static Connection openConnection() throws PersistenceException {
        LOG.info("Opening connection to the database");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/event_calendar","root", "2faa5778a29b945P");
        } catch (SQLException e) {
            LOG.error("Error while connecting to the database, error in credentials or database url");
            throw new PersistenceException();
        }

        return connection;
    }

    /**
     * closes connection to the database
     * @throws PersistenceException if an error caused by the database occurs
     */
    public static void closeConnection() throws PersistenceException{
        LOG.info("Closing connection to the database");
        try {
            con.close();
        } catch (SQLException e) {
            LOG.error("Error while closing connection to the database");
            throw new PersistenceException();

        }
    }

}

