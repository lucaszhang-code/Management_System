package cn.guet.control.middle;

import cn.guet.control.dao.DAO;
import cn.guet.control.utils.ConnectionUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class TableData {
    private List<String> columnNames;
    private List<Object[]> data;

    public TableData(List<String> columnNames, List<Object[]> data) {
        this.columnNames = columnNames;
        this.data = data;
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
                columnNames.add(rsmd.getColumnLabel(i));
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
            ConnectionUtils.close(null, null, rs);
        }
        return new TableData(columnNames, data);
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<Object[]> getData() {
        return data;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
    }
}
