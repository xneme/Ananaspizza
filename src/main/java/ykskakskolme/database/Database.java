/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ykskakskolme.database;

import java.util.*;
import java.sql.*;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        
        if (dbUrl != null && dbUrl.length() > 0) {
            this.databaseAddress = dbUrl;
        } else {
            this.databaseAddress = databaseAddress;
        }
    }

    public Connection getConnection() throws SQLException {
//        System.out.println("Connecting to " + databaseAddress);
//return DriverManager.getConnection(databaseAddress);
        return DriverManager.getConnection("jdbc:postgresql://db:5432/postgres", "postgres", "postgres");
    }
}
