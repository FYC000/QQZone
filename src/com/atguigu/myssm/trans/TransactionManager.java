package com.atguigu.myssm.trans;

import com.atguigu.myssm.basedao.ConnUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    //开启事务
    public static void beginTrans() throws SQLException {
        ConnUtil.getConn().setAutoCommit(false);
        System.out.println("正在开启事物中....");
    }

    //提交事务
    public static void commit() throws SQLException {
        Connection conn=ConnUtil.getConn();
        conn.commit();
        ConnUtil.closeConn();
        System.out.println("正在提交事物中....");

    }

    //回滚事务
    public static void rollback() throws SQLException {
        Connection conn=ConnUtil.getConn();
        conn.rollback();
        ConnUtil.closeConn();
        System.out.println("正在回滚事务中....");
    }

}
