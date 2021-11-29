package com.revature.service;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionService {
    /**
     Creates a connection to the database.
     **/
    public static Connection getConnection(){
        try{
            Class.forName("org.postgresql.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://enterprise.c8boxomjtofz.us-west-1.rds.amazonaws.com:5432/postgres?currentSchema=\"RevORM\"",
                    "postgres",
                    "brandon123"
            );

            return connection;
        } catch (SQLException | ClassNotFoundException e  ){
            System.out.println(e.getMessage());
        }
        return null;
    }

}