/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author LUIS DAS ARTIMANHAS
 */
public class ConexaoPSQL {

    private static Connection conexao;

    public static Connection getConexao() throws ClassNotFoundException, SQLException {
        String user = "root";
        String password = "";
        String database = "mydb";
        String url = "postgresql://localhost/"+database+"?user="+user+"&password="+password;
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        conexao = DriverManager.getConnection(url);
        return conexao;
    }
}
