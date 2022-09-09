package com.atguigu.myssm.trans;

import com.atguigu.myssm.basedao.ConnUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    //��������
    public static void beginTrans() throws SQLException {
        ConnUtil.getConn().setAutoCommit(false);
        System.out.println("���ڿ���������....");
    }

    //�ύ����
    public static void commit() throws SQLException {
        Connection conn=ConnUtil.getConn();
        conn.commit();
        ConnUtil.closeConn();
        System.out.println("�����ύ������....");

    }

    //�ع�����
    public static void rollback() throws SQLException {
        Connection conn=ConnUtil.getConn();
        conn.rollback();
        ConnUtil.closeConn();
        System.out.println("���ڻع�������....");
    }

}
