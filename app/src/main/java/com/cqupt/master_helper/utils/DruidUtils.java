package com.cqupt.master_helper.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

public class DruidUtils {

    private static DataSource dataSource = null;
    private static ThreadLocal<Connection> threadLocal = null;


    static {
        try {
//            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("db.properties");
            Properties properties = new Properties();
//            properties.load(in);
            properties.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
            properties.setProperty("url", "jdbc:mysql://47.108.152.107:3306?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=true");
            properties.setProperty("username", "root");
            properties.setProperty("password", "MYsql_123456");


            dataSource = DruidDataSourceFactory.createDataSource(properties);

            threadLocal = new ThreadLocal<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 建立连接
     *
     * @return 连接
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection == null) {
            connection = dataSource.getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }

    /**
     * 释放资源
     */
    public static void closeSource() {
        Connection connection = threadLocal.get();
//        if (resultSet != null) {
//            try {
//                resultSet.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        if (statement != null) {
//            try {
//                statement.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
        if (connection != null) {
            try {
                connection.close();
                threadLocal.remove();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
