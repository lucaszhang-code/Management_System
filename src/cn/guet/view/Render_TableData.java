package cn.guet.view;

import cn.guet.control.middle.TableData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;

public class Render_TableData {
    public static JTable table;
    public static DefaultTableModel tableModel;
    // 用于更新表格数据的方法
    public static void updateTableData(String sql, JPanel p_Graph) {
        // 获取查询数据
        TableData tableData = TableData.getData(sql);

        // 获取列名和数据
        List<String> columnNames = tableData.getColumnNames();
        List<Object[]> data = tableData.getData();

        // 创建 DefaultTableModel 并设置列名
        tableModel = new DefaultTableModel();
        for (String columnName : columnNames) {
            tableModel.addColumn(columnName);
        }

        // 添加数据到表格模型
        for (Object[] rowData : data) {
            tableModel.addRow(rowData);
        }

        // 创建 JTable 并设置模型
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        p_Graph.removeAll();
        p_Graph.setLayout(new BorderLayout());
        p_Graph.add(scrollPane, BorderLayout.CENTER);
        p_Graph.revalidate();
        p_Graph.repaint();
    }

    public JTable getTable(){
        return table;
    }

}
