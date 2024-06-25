package cn.guet.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class Graph_Query {
    private String tableENName;
    private Render_FormData rfd = new Render_FormData();
    private Map<String, JTextField> labelText;
    private List<String> columnName;

    Graph_Query(String tableENName){
        this.tableENName = tableENName;

        JFrame frame = new JFrame("查询");
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);

        JPanel p_top = new JPanel();
        JLabel l_tip = new JLabel("请输入要查询的信息");

        Font font = new Font("宋体", Font.PLAIN, 20);
        l_tip.setFont(font);
        p_top.add(l_tip);

        JPanel p_Form = new JPanel();
        rfd.getFormData(p_Form, tableENName);

        // 临时面板，让界面的组件左右留出空间
        JPanel jp_Temp1 = new JPanel();
        jp_Temp1.setPreferredSize(new Dimension(25, 100));
        JPanel jp_Temp2 = new JPanel();
        jp_Temp2.setPreferredSize(new Dimension(25, 100));

        JPanel p_bottom = new JPanel();
        JButton b_query = new JButton("查询");
        b_query.addActionListener(e -> exact_query());

        JButton b_clear = new JButton("清空");
        p_bottom.add(b_query);
        p_bottom.add(b_clear);


        frame.add(p_top, BorderLayout.NORTH);
        frame.add(p_Form, BorderLayout.CENTER);
        frame.add(jp_Temp1, BorderLayout.WEST);
        frame.add(jp_Temp2, BorderLayout.EAST);
        frame.add(p_bottom, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void exact_query() {
        this.labelText = rfd.getLabelText();
        this.columnName = rfd.getColumnNames();
        StringBuilder sql = new StringBuilder("select * from " + tableENName + " where ");

        for (int i = 0; i < rfd.getTextSize(); i++) {
            JTextField textField = labelText.get(columnName.get(i));
            if (textField != null) {
                String text = textField.getText();
//                System.out.println("Column: " + columnName.get(i) + ", Text: " + text); // Debug 输出
                if (text != null && !text.isEmpty()) {
                    sql.append(columnName.get(i)).append(" = '").append(text).append("' and ");
                }
            } else {
//                System.out.println("TextField for column " + columnName.get(i) + " is null."); // Debug 输出
            }
        }

        // 去掉最后一个 "and "
        if (sql.length() > 0) {
            sql.setLength(sql.length() - 5); // " and " 的长度为5
        }

        System.out.println("Constructed SQL: " + sql.toString()); // Debug 输出
        Render_TableData.updateTableData(sql.toString(), Graph_Delete.p_center);
    }


}
