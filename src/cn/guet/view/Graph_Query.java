package cn.guet.view;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Graph_Query {
    private String tableENName;
    private Render_FormData rfd = new Render_FormData();
    private Map<String, JComponent> labelText;
    private List<String> columnName;
    private JPanel panel;
    private Render_TableData rtd = new Render_TableData();

    /**
     * 构造方法
     * @param tableENName 表名
     * @param panel 父面板
     */
    Graph_Query(String tableENName, JPanel panel){
        this.tableENName = tableENName;
        this.panel = panel;

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
        this.labelText = rfd.getLabelText();
        this.columnName = rfd.getColumnNames();

        // 临时面板，让界面的组件左右留出空间
        JPanel jp_Temp1 = new JPanel();
        jp_Temp1.setPreferredSize(new Dimension(25, 100));
        JPanel jp_Temp2 = new JPanel();
        jp_Temp2.setPreferredSize(new Dimension(25, 100));

        JPanel p_bottom = new JPanel();
        JButton b_query = new JButton("查询");
        b_query.addActionListener(e -> exact_query());

        JButton b_clear = new JButton("清空");
        b_clear.addActionListener(e -> clearFields());

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
        StringBuilder sql = new StringBuilder("select * from " + tableENName + " where ");

        columnName.forEach(column -> {
            JComponent comp = labelText.get(column);
            String value = null;

            if (comp instanceof JTextField) {
                value = ((JTextField) comp).getText();
            } else if (comp instanceof JPanel) { // Assuming this JPanel contains JRadioButtons
                for (Component c : ((JPanel) comp).getComponents()) {
                    if (c instanceof JRadioButton && ((JRadioButton) c).isSelected()) {
                        value = ((JRadioButton) c).getText();
                        break;
                    }
                }
            }

            if (value != null && !value.isEmpty()) {
                sql.append(column).append(" = '").append(value).append("' and ");
            }
        });

        // 去掉最后一个 "and "
        if (sql.length() > 20) {
            sql.setLength(sql.length() - 5); // " and " 的长度为5
        }

        System.out.println("Constructed SQL: " + sql.toString()); // Debug 输出
        rtd.updateTableData(sql.toString(), panel);
    }

    public void clearFields() {
        labelText.values().forEach(comp -> {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setText("");
            }
            else if (comp instanceof JPanel) {
                Arrays.stream(((JPanel) comp).getComponents())
                        .filter(c -> c instanceof JRadioButton)
                        .forEach(c -> ((JRadioButton) c).setSelected(false));

            }
        });
    }
}
