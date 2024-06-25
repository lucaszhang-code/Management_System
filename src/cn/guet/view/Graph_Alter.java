package cn.guet.view;

import javax.swing.*;
import java.awt.*;
import static cn.guet.view.Render_TableData.updateTableData;

import java.util.ArrayList;
import java.util.List;

public class Graph_Alter {
    private String tableENName;
    private Render_TableData render_tableData;
    private List <String> columnNames, rowData;
    public static JPanel p_center;

    public Render_FormData render_formData;

    Graph_Alter(String tableENName) {
        this.tableENName = tableENName;

        JFrame frame = new JFrame("修改界面");
        frame.setSize(600,500);
        frame.setLocationRelativeTo(null);

        p_center = new JPanel();
        updateTableData("select * from " + tableENName, p_center);

        JPanel p_bottom = new JPanel();
        JButton btn_alter = new JButton("修改");
        btn_alter.addActionListener(e -> alter_data());


        JButton btn_query_alter = new JButton("精确查询");
        p_bottom.add(btn_alter);
        p_bottom.add(btn_query_alter);

        frame.add(p_center, BorderLayout.CENTER);
        frame.add(p_bottom, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void alter_data()
    {
        render_tableData = new Render_TableData();
        JTable table = render_tableData.getTable();
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "请选择要修改的记录");
            return;
        }

         columnNames = new ArrayList<>();
         rowData = new ArrayList<>();

        // 获取表头信息和行数据
        for (int column = 0; column < table.getColumnCount(); column++) {
            String columnName = table.getColumnName(column);
            Object value = table.getValueAt(selectedRow, column);
            columnNames.add(columnName);
            rowData.add(value.toString());
        }

        new Graph_Alter_Form( columnNames, rowData, tableENName);


    }
}
