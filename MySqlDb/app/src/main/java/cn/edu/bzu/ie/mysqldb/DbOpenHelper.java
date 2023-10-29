package cn.edu.bzu.ie.mysqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * MySQL数据库的连接辅助类
 */
public class DbOpenHelper {
    private static final String CLS = "com.mysql.jdbc.Driver";
    //private static final String URL = "jdbc:mysql://192.168.2.115:3306/mysql?useSSL=false";
    private static final String URL = "jdbc:mysql://192.168.237.172/mysql?useSSL=false";
    private static final String USER = "root";
    private static final String PWD = "123456";

    public static Connection conn;   // 连接对象
    public static Statement stmt;    // 命令集
    public static PreparedStatement pStmt;   // 预编译命令集
    public static ResultSet rs;     // 结果集

    // 取得连接的方法
    public static void getConnection(){
        try{
            Class.forName(CLS);
            conn = DriverManager.getConnection(URL, USER, PWD);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    // 关闭数据库操作对象
    public static void closeAll(){
        try{
            if(rs!=null){
                rs.close();
                rs=null;
            }
            if(stmt!=null){
                stmt.close();
                stmt=null;
            }
            if(pStmt!=null){
                pStmt.close();
                pStmt=null;
            }
            if(conn!=null){
                conn.close();
                conn=null;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
