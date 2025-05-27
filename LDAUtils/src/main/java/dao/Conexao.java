/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author LUIS DAS ARTIMANHAS
 */
public class Conexao {

    private static Connection conexao;
    private static final SessionFactory sessionFactory;

    public static Connection getConexaoMysql(String host, String db, String user, String password) throws ClassNotFoundException, SQLException {
        String conectionStr = "jdbc:mysql://" + host + ":3306/" + db;

        Class.forName("com.mysql.cj.jdbc.Driver");

        conexao = DriverManager.getConnection(conectionStr, user, password);
        return conexao;
    }
    
    public static Connection getConexaoPsql(String host, String user,String password,String database) throws ClassNotFoundException, SQLException {
        String url = "jdbc:postgresql://"+host+":5432/"+database;
        
        Class.forName("org.postgresql.Driver");
        
        conexao = DriverManager.getConnection(url,user,password);
        return conexao;
    }
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
