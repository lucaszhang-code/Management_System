package cn.guet.control.dao;

import cn.guet.control.utils.ConnectionUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {
    private  Connection conn ;
    private  PreparedStatement ps ;
    private  ResultSet rs ;
    private  boolean isAutoCommit = true;
    /**
     * 设置是否自动提交
     * @param isAutoCommit
     */
    public void setAutoCommit(boolean isAutoCommit) {
        isAutoCommit = isAutoCommit;
    }

    /**
     * 单独封装
     */
    public void commit(){
        if(isAutoCommit && conn != null){
            try {
                conn.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ResultSet query(String sql, Object... params){
        conn = ConnectionUtils.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            if(params != null){
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  int update(String sql, Object... params) {
        // 连接
        conn = ConnectionUtils.getConnection();
        // 声明 PreparedStatement
        try{
            // 设置是否自动提交
            conn.setAutoCommit(isAutoCommit);

            ps = conn.prepareStatement(sql);
            // 给占位符赋值
            if(params != null) {
                for(int i = 0;i<params.length;i++){
                    ps.setObject(i+1, params[i]);
                }
            }

            // 执行sql
            int i = ps.executeUpdate();
            return i;
        }
        catch(Exception e){
            if(!isAutoCommit){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        }
        finally {
            ConnectionUtils.close(conn, ps, rs);
        }
        return 1;
    }

}
