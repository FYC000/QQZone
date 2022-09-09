package com.atguigu.myssm.basedao;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnUtil {

   /* public static String DRIVER ;
    public static String URL ;
    public static String USER ;
    public static String PWD ;*/
    private static ThreadLocal<Connection>threadLocal=new ThreadLocal<>();
    static Properties properties=new Properties();

    static {

        InputStream is=ConnUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");

        try {
            properties.load(is);

           /* //1.直接使用properties文件读取


            DRIVER= properties.getProperty("jdbc.driver");
            URL= properties.getProperty("jdbc.url");
            USER= properties.getProperty("jdbc.user");
            PWD= properties.getProperty("jdbc.pwd");*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn(){
        Connection conn=threadLocal.get();
        if(conn==null){
            conn=createConn();
            threadLocal.set(conn);
        }
        return threadLocal.get();
    }

    public static Connection createConn(){
        try {

            //两种方法连接数据库
            /*//1.直接使用properties文件读取
            DruidDataSource druidDataSource=new DruidDataSource();
            druidDataSource.setDriverClassName(DRIVER);
            druidDataSource.setUrl(URL);
            druidDataSource.setUsername(USER);
            druidDataSource.setPassword(PWD);

            druidDataSource.setMaxWait(5000);
            druidDataSource.setMinIdle(3);
            druidDataSource.setMaxActive(10);*/


            //2.通过druid数据源连接池
            DataSource druidDataSource= null;
            try {
                druidDataSource = DruidDataSourceFactory.createDataSource(properties);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return druidDataSource.getConnection();//通过驱动管理器获取连接对象
           /* //1.加载驱动
            Class.forName(DRIVER);
            //2.通过驱动管理器获取连接对象
            return DriverManager.getConnection(URL, USER, PWD);*/
        } catch (/*ClassNotFoundException |*/ SQLException e) {
            e.printStackTrace();
        }
            return null;
    }

    public static void closeConn() throws SQLException {
        Connection conn= threadLocal.get();
        if(conn==null){
            return;
        }
        if(!conn.isClosed()){
            conn.close();
            //threadLocal.set(null);
            threadLocal.remove();
        }
    }

}
