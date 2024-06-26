package cn.guet.view;


import cn.guet.control.middle.ExecuteUpdate;

import javax.swing.*;
import java.awt.*;

import static cn.guet.view.Render_TableData.tableModel;

public class Graph_Delete {
    private String tableENName; // 视图表的名字
    public static JPanel p_center;
    private static JPanel p_bottom;
    private Render_TableData render_tableData = new Render_TableData();
    String sql;
    private ExecuteUpdate ex = new ExecuteUpdate();


    Graph_Delete(String tableENName){
        this.tableENName = tableENName;
        JFrame frame = new JFrame("删除列表");
        frame.setSize(600,500);
        frame.setLocationRelativeTo(null);


        p_center = new JPanel();
        frame.add(p_center);

        p_bottom = new JPanel();
        JButton btn_delete = new JButton("删除");
        btn_delete.addActionListener(e -> delete_data());

        JButton btn_query_delete = new JButton("查询删除");
        btn_query_delete.addActionListener(e -> new Graph_Query(tableENName, p_center));

        p_bottom.add(btn_delete);
        p_bottom.add(btn_query_delete);
        frame.add(p_center, BorderLayout.CENTER);
        frame.add(p_bottom, BorderLayout.SOUTH);

        render_tableData.updateTableData("select * from " + tableENName, p_center);
        frame.setVisible(true);
    }

    public void delete_data() {
        JTable table = render_tableData.getTable();
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "请选择要删除的记录");
            return;
        }

        String id = table.getValueAt(selectedRow, 0).toString();
        String name = table.getValueAt(selectedRow, 1).toString();

        sql = "DELETE FROM " + tableENName.substring(3) + " WHERE ";

        switch (tableENName) {
            case "cn_staff_management":
                sql += "sta_id = " + id + " AND sta_name = '" + name + "'";
                break;
            case "cn_salary_management":
                sql += "sal_id = " + id + " AND sal_name = '" + name + "'";
                break;
            case "cn_assessment_management":
                sql += "ass_id = " + id + " AND ass_name = '" + name + "'";
                break;
            case "cn_recruitment_management":
                sql += "recru_id = " + id + " AND recru_empolyer = '" + name + "'";
                break;
            case "cn_training_management":
                sql += "tra_id = " + id + " AND tra_instructor = '" + name + "'";
                break;
            default:
                JOptionPane.showMessageDialog(null, "未知的表名");
                return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "确定删除姓名为" + "“" + name + "”" + "的人的所有信息吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ex.execute(sql);
            System.out.println(sql);
            tableModel.removeRow(selectedRow);
            render_tableData.updateTableData("select * from " + tableENName, Graph_Main.p_Graph);
            Graph_Main.p_Graph.repaint();
            JOptionPane.showMessageDialog(null, "记录已删除");
        }
    }
}
