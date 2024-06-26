package cn.guet.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Graph_Generate_report {
    private Font myFont = new Font("宋体", Font.BOLD, 25);
    private String tableENName;
    private List<String> columnNames;
    private Render_TableData render_tableData = new Render_TableData();
    private JPanel p_center;
    private List<JCheckBox> columnCheckBoxes;
    private Map<String, JRadioButton> ascButtons;
    private Map<String, JRadioButton> descButtons;
    private ButtonGroup globalOrderGroup;

    Graph_Generate_report(List<String> columnNames, String tableENName) {
        this.tableENName = tableENName;
        this.columnNames = columnNames;
        this.ascButtons = new HashMap<>();
        this.descButtons = new HashMap<>();
        this.globalOrderGroup = new ButtonGroup();

        JFrame frame = new JFrame("生成报表");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel p_top = new JPanel();
        JLabel l_title = new JLabel("表单选项");
        l_title.setFont(myFont);
        p_top.add(l_title);

        p_center = new JPanel();
        p_center.setLayout(new GridLayout(1, columnNames.size()));

        columnCheckBoxes = columnNames.stream()
                .map(columnName -> {
                    JCheckBox cb = new JCheckBox(columnName);
                    cb.setFont(new Font("宋体", Font.BOLD, 16));
                    return cb;
                })
                .collect(Collectors.toList());

        columnNames.forEach(columnName -> {
            JPanel columnPanel = new JPanel(new GridLayout(3, 1));

            JCheckBox cb = new JCheckBox(columnName);
            cb.setFont(new Font("宋体", Font.BOLD, 16));
            columnPanel.add(cb);
            columnCheckBoxes.add(cb);

            JRadioButton ascButton = new JRadioButton("升序");
            ascButton.setFont(new Font("宋体", Font.BOLD, 16));
            ascButtons.put(columnName, ascButton);
            columnPanel.add(ascButton);

            JRadioButton descButton = new JRadioButton("降序");
            descButton.setFont(new Font("宋体", Font.BOLD, 16));
            descButtons.put(columnName, descButton);
            columnPanel.add(descButton);

            globalOrderGroup.add(ascButton);
            globalOrderGroup.add(descButton);

            p_center.add(columnPanel);
        });

        JPanel p_bottom = new JPanel();
        p_bottom.setPreferredSize(new Dimension(800, 100));
        JButton btn_generate = new JButton("生成报表");
        btn_generate.setPreferredSize(new Dimension(150, 50));
        p_bottom.add(btn_generate);
        btn_generate.addActionListener(e -> generate());

        JButton btn_clearAll = new JButton("清空所有选项");
        btn_clearAll.setPreferredSize(new Dimension(150, 50));
        btn_clearAll.addActionListener(e -> clearAll());
        p_bottom.add(btn_clearAll);

        frame.add(p_top, BorderLayout.NORTH);
        frame.add(p_center);
        frame.add(p_bottom, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void generate() {
        List<String> selectedColumns = columnCheckBoxes.stream()
                .filter(AbstractButton::isSelected)
                .map(AbstractButton::getText)
                .collect(Collectors.toList());

        if (selectedColumns.isEmpty()) {
            JOptionPane.showMessageDialog(null, "请选择至少一个列进行生成报表");
            return;
        }

        String sql = "select " + String.join(", ", selectedColumns) + " from " + tableENName;

        String orderClause = columnNames.stream()
                .filter(column -> ascButtons.get(column).isSelected() || descButtons.get(column).isSelected())
                .map(column -> {
                    if (ascButtons.get(column).isSelected()) {
                        return column + " asc";
                    } else if (descButtons.get(column).isSelected()) {
                        return column + " desc";
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));

        if (!orderClause.isEmpty()) {
            sql += " order by " + orderClause;
        }

        System.out.println("Generated SQL: " + sql);

        render_tableData.updateTableData(sql, Graph_Main.p_Graph);
        Graph_Main.p_Graph.repaint();
    }

    public void clearAll() {
        columnCheckBoxes.forEach(cb -> cb.setSelected(false));
        globalOrderGroup.clearSelection();
    }
}
