package com.atguigu.myssm.basedao;

import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T> {

    protected Connection conn ;
    protected PreparedStatement psmt ;
    protected ResultSet rs ;

    //T��Class����
    private Class entityClass ;

    public BaseDAO(){
        //getClass() ��ȡClass���󣬵�ǰ����ִ�е���new FruitDAOImpl() , ��������FruitDAOImpl��ʵ��
        //��ô���๹�췽���ڲ����Ȼ���ø��ࣨBaseDAO�����޲ι��췽��
        //��˴˴���getClass()�ᱻִ�У�����getClass��ȡ����FruitDAOImpl��Class
        //����getGenericSuperclass()��ȡ������BaseDAO��Class
        Type genericType = getClass().getGenericSuperclass();
        //ParameterizedType ����������
        Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        //��ȡ����<T>�е�T����ʵ������
        Type actualType = actualTypeArguments[0];
        try {
            entityClass = Class.forName(actualType.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected Connection getConn(){
        return ConnUtil.getConn();
    }

    protected void close(ResultSet rs , PreparedStatement psmt , Connection conn){

    }

    //��Ԥ��������������ò���
    private void setParams(PreparedStatement psmt , Object... params) throws SQLException {
        if(params!=null && params.length>0){
            for (int i = 0; i < params.length; i++) {
                psmt.setObject(i+1,params[i]);
            }
        }
    }

    //ִ�и��£�����Ӱ������
    protected int executeUpdate(String sql , Object... params){
        boolean insertFlag = false ;
        insertFlag = sql.trim().toUpperCase().startsWith("INSERT");
        try {
            conn = getConn();
            if(insertFlag){
                psmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            }else {
                psmt = conn.prepareStatement(sql);
            }
            setParams(psmt,params);
            int count = psmt.executeUpdate() ;

            if(insertFlag){
                rs = psmt.getGeneratedKeys();
                if(rs.next()){
                    return ((Long)rs.getLong(1)).intValue();
                }
            }

            return count ;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rs,psmt,conn);
        }
        return 0;
    }

    //ͨ�����似����obj�����property���Ը�propertyValueֵ
    private void  setValue(Object obj ,  String property , Object propertyValue) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Class clazz = obj.getClass();
        try {
            //��ȡproperty����ַ�����Ӧ�������� �� ���� "fid"  ȥ�� obj�����е� fid ����
            Field field = clazz.getDeclaredField(property);
            if(field!=null){

                //��ȡ��ǰ�ֶε���������
                String typeName=field.getType().getName();
                //�ж�������Զ������ͣ�����Ҫ��������Զ�����Ĵ�һ�������Ĺ��췽��������������Զ����ʵ������Ȼ��ʵ������ֵ���������

                if(isMyType(typeName)){
                    //����typeName��"com.atguigu.qqzone.pojo.UserBasic"
                    Class typeNameClass=Class.forName(typeName);
                    Constructor constructor=typeNameClass.getDeclaredConstructor(Integer.class);
                    propertyValue=constructor.newInstance(propertyValue);
                }
                field.setAccessible(true);
                field.set(obj,propertyValue);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Boolean isMyType(String typeName){
        return !isNotMyType(typeName);
    }

    private Boolean isNotMyType(String typeName){
        return "java.lang.Integer".equals(typeName)||
                "java.lang.String".equals(typeName)||
                "java.util.LocalDateTime".equals(typeName)||
                "java.sql.LocalDateTime".equals(typeName)||
                "java.time.LocalDateTime".equals(typeName)||
                "java.lang.Double".equals(typeName);
    }

    //ִ�и��Ӳ�ѯ����������ͳ�ƽ��
    protected Object[] executeComplexQuery(String sql , Object... params){
        try {
            conn = getConn() ;
            psmt = conn.prepareStatement(sql);
            setParams(psmt,params);
            rs = psmt.executeQuery();

            //ͨ��rs���Ի�ȡ�������Ԫ����
            //Ԫ���ݣ�������������ݵ����� , �򵥽�������������������Щ�У�ʲô���͵ȵ�

            ResultSetMetaData rsmd = rs.getMetaData();
            //��ȡ�����������
            int columnCount = rsmd.getColumnCount();
            Object[] columnValueArr = new Object[columnCount];
            //6.����rs
            if(rs.next()){
                for(int i = 0 ; i<columnCount;i++){
                    Object columnValue = rs.getObject(i+1);     //33    ƻ��      5
                    columnValueArr[i]=columnValue;
                }
                return columnValueArr ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs,psmt,conn);
        }
        return null ;
    }

    //ִ�в�ѯ�����ص���ʵ�����
    protected T load(String sql , Object... params) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        try {
            conn = getConn() ;
            psmt = conn.prepareStatement(sql);
            setParams(psmt,params);
            rs = psmt.executeQuery();

            //ͨ��rs���Ի�ȡ�������Ԫ����
            //Ԫ���ݣ�������������ݵ����� , �򵥽�������������������Щ�У�ʲô���͵ȵ�

            ResultSetMetaData rsmd = rs.getMetaData();
            //��ȡ�����������
            int columnCount = rsmd.getColumnCount();
            //6.����rs
            if(rs.next()){
                T entity = (T)entityClass.newInstance();

                for(int i = 0 ; i<columnCount;i++){
                    String columnName = rsmd.getColumnName(i+1);            //fid   fname   price
                    Object columnValue = rs.getObject(i+1);     //33    ƻ��      5
                    setValue(entity,columnName,columnValue);
                }
                return entity ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            close(rs,psmt,conn);
        }
        return null ;
    }


    //ִ�в�ѯ������List
    protected List<T> executeQuery(String sql , Object... params) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        List<T> list = new ArrayList<>();
        try {
            conn = getConn() ;
            psmt = conn.prepareStatement(sql);
            setParams(psmt,params);
            rs = psmt.executeQuery();

            //ͨ��rs���Ի�ȡ�������Ԫ����
            //Ԫ���ݣ�������������ݵ����� , �򵥽�������������������Щ�У�ʲô���͵ȵ�

            ResultSetMetaData rsmd = rs.getMetaData();
            //��ȡ�����������
            int columnCount = rsmd.getColumnCount();
            //6.����rs
            while(rs.next()){
                T entity = (T)entityClass.newInstance();

                for(int i = 0 ; i<columnCount;i++){
                    //getColumnLabel�õ������������������ģ��� id as kid��getColumnLabel��ȡkid��getColumnName��ȡid
                    String columnName = rsmd.getColumnLabel(i+1);            //fid   fname   price
                    Object columnValue = rs.getObject(i+1);     //33    ƻ��      5
                    setValue(entity,columnName,columnValue);
                }
                list.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            close(rs,psmt,conn);
        }
        return list ;
    }
}