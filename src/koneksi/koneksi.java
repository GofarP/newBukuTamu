/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Gofur
 */

public class koneksi
{
    private static Connection mysqlconfig;
    public static  Connection configDB() throws SQLException
    {
        try 
        {
            String url="jdbc:mysql://localhost:3306/bukutamu";
            String user="root";
            String pass="";
        } 
        
        catch (Exception e) 
        {
            
        }
    }
}
//public class koneksi {
//    private static Connection mysqlconfig;
//    public static Connection configDB()throws SQLException{
//        try {
//            String url="jdbc:mysql://localhost:3306/bukutamu"; //url database
//            String user="root"; //user database
//            String pass=""; //password database
//            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//            mysqlconfig=DriverManager.getConnection(url, user, pass);            
//        } catch (Exception e) {
//            System.err.println("koneksi gagal "+e.getMessage()); //perintah menampilkan error pada koneksi
//        }
//        return mysqlconfig;
//    }    
//}
