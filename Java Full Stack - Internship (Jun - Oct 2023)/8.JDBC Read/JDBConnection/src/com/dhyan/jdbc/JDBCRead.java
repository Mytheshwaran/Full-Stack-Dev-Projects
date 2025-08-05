package com.dhyan.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCRead {
    private final String url = "jdbc:postgresql://localhost/test";
    private final String user = "jdbcuser";
    private final String password = "mytheshwaran";

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public Connection connect() {
        Connection conn = null;
        try {
        	Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
                Statement statement=null;
                String query="select * from userdetails;";
                String insertQuery="insert into userdetails(name) "
                		+ "values ('name2'),('name3');";
                try
                {
                	statement=conn.createStatement();
                	statement.executeUpdate(insertQuery);
                	ResultSet result=statement.executeQuery(query);
                	while(result.next())
                	{
                		int id=result.getInt("id");
                		String name=result.getString("name");
                		System.out.println("Id: "+id+"\nName: "+name);
                	}
                	result.close();
                }
                catch(Exception e)
                {
                	System.out.println(e.getMessage());
                }
                conn.close();
                statement.close();
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        	e.getMessage();
        }

        return conn;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	JDBCRead app = new JDBCRead();
        app.connect();
    }
}