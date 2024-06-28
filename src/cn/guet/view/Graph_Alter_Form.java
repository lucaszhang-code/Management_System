package cn.guet.view;

import cn.guet.control.middle.ExecuteUpdate;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static cn.guet.view.Render_TableData.tableModel;

public class Graph_Alter_Form {
    private String tableENName;
    private Font myFont = new Font("宋体", Font.BOLD, 16);
    private List<String> columnNames;
    private List<String> rowData;
    private String unedit_id;
    private Map<String, JTextField> labelText;
    private Render_TableData render_tableData = new Render_TableData();

    Graph_Alter_Form(List<String> columnNames, List<String> rowData, String tableENName) {
        this.tableENName = tableENName;
        this.columnNames = columnNames;
        this.rowData = rowData;
        this.unedit_id = rowData.get(0);

        JFrame frame = new JFrame("修改数据");
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);

        JPanel p_Form = new JPanel(new GridLayout(columnNames.size(), 2));
        labelText = new LinkedHashMap<>();
        for (int i = 0; i < columnNames.size(); i++) {
            JLabel label = new JLabel(columnNames.get(i));
            label.setFont(myFont);
            JTextField text = new JTextField(rowData.get(i));
            text.setFont(myFont);
            p_Form.add(label);
            p_Form.add(text);

            if (columnNames.get(i).equals("工资总额")) {
                text.setEditable(false);
            }
            labelText.put(columnNames.get(i), text);
        }

        // 临时面板，让界面的组件左右留出空间
        JPanel jp_Temp1 = new JPanel();
        jp_Temp1.setPreferredSize(new Dimension(25, 100));
        JPanel jp_Temp2 = new JPanel();
        jp_Temp2.setPreferredSize(new Dimension(25, 100));

        JPanel p_bottom = new JPanel();
        JButton btn_alter = new JButton("修改");
        btn_alter.addActionListener(e -> alterData());


        JButton btn_cancel = new JButton("取消");
        p_bottom.add(btn_alter);
        p_bottom.add(btn_cancel);

        frame.add(p_Form, BorderLayout.CENTER);
        frame.add(jp_Temp1, BorderLayout.WEST);
        frame.add(jp_Temp2, BorderLayout.EAST);
        frame.add(p_bottom, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void alterData() {
        StringBuilder sql_1 = new StringBuilder("update " + tableENName + " set ");
        String sql_2 = " where ";
        for (int i = 0; i < rowData.size(); i++) {
            if(labelText.get(columnNames.get(i)).getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入完整信息！");
                return;
            }
            if (i == 0) {
                // 假设第一个字段是主键，用于 WHERE 子句
                sql_2 += columnNames.get(i) + " = '" + unedit_id + "'";
                sql_1.append(columnNames.get(i)).append(" = '").append(labelText.get(columnNames.get(i)).getText()).append("', ");
            } else {
                sql_1.append(columnNames.get(i)).append(" = '").append(labelText.get(columnNames.get(i)).getText()).append("', ");
            }
        }

        // 移除 sql_1 末尾多余的逗号和空格
        if (sql_1.toString().endsWith(", ")) {
            sql_1 = new StringBuilder(sql_1.substring(0, sql_1.length() - 2));
        }
        ExecuteUpdate ex = new ExecuteUpdate();

        // 完整的 SQL 语句
        String finalSql = sql_1 + sql_2;
        System.out.println("Constructed SQL: " + finalSql); // 调试输出
        int confirm = JOptionPane.showConfirmDialog(null, "确定修改信息吗？", "确认修改", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ex.execute(finalSql);
            System.out.println(finalSql);
            JOptionPane.showMessageDialog(null, "记录已修改");

            for (JTextField textField : labelText.values()) {
                textField.setText("");
            }

            render_tableData.updateTableData("select * from " + tableENName, Graph_Alter.p_center);
            render_tableData.updateTableData("select * from " + tableENName, Graph_Main.p_Graph);
        }

    }
}
