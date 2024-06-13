package cn.guet.control.middle;

import cn.guet.control.dao.DAO;
import cn.guet.control.utils.ConnectionUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args)  {
        demo1();
//        demo2();
    }

    public static void demo1(){
        String sql = "select * from staff_Management";
        getColumeName(sql);
        getData(sql);
    }

    public static TableData getData(String sql) {
        DAO dao = new DAO();
        ResultSet rs = dao.query(sql);
        List<String> columnNames = new ArrayList<>();
        List<Object[]> data = new ArrayList<>();

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // 获取列名
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(rsmd.getColumnName(i));
            }

            // 获取数据
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                data.add(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 确保释放资源
            ConnectionUtils.close(dao.getConnection(), dao.getStatement(), rs);
        }

        return new TableData(columnNames, data);
    }


    public static void demo2(){
        String sql = "delete from staff_management where sta_name ='刘备'" ;
        DAO dao = new DAO();
        int row = dao.update(sql);
        System.out.println(row);
    }
}
