package cn.guet.view;
import cn.guet.control.middle.TableData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;



public class Graph extends JFrame {
    private static JPanel p_Center = new JPanel();
    private static JPanel p_Graph = new JPanel();
    private static JPanel p_Operate = new JPanel();
    private static JButton btn_add;
    private static JButton btn_del;
    private static JButton btn_edit;
    private static JButton btn_query;

    public static void main(String[] args) {
        new Graph();
    }

    Graph() {
        JFrame frame = new JFrame("人力资源管理系统");

        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu selMenu = new JMenu("查询");
        menuBar.add(selMenu);

        JMenu updMenu = new JMenu("更新");
        menuBar.add(updMenu);
        JMenuItem addItem = new JMenuItem("添加");
        updMenu.add(addItem);
        JMenuItem delItem = new JMenuItem("删除");
        updMenu.add(delItem);
        JMenuItem chanItem = new JMenuItem("修改");
        updMenu.add(chanItem);

        JMenu BehMenu = new JMenu("状态");
        menuBar.add(BehMenu);
        JMenuItem refItem = new JMenuItem("刷新");
        BehMenu.add(refItem);

        JMenu helpMenu = new JMenu("帮助");
        menuBar.add(helpMenu);

        // 创建标题标签
        JLabel title = new JLabel("人力资源管理系统");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("MicroSoft YaHei", Font.BOLD, 36));

        p_Center.setBackground(new Color(255, 255, 255));
        p_Center.setPreferredSize(new Dimension(800, 100));
        p_Center.add(title, BorderLayout.CENTER);

        p_Operate.setLayout(new BoxLayout(p_Operate, BoxLayout.X_AXIS));
        p_Operate.setPreferredSize(new Dimension(800, 100));
        p_Operate.setBackground(new Color(255, 255, 255));

        btn_add = new JButton("添加");
        btn_add.setPreferredSize(new Dimension(100, 75));
        p_Operate.add(Box.createHorizontalGlue());
        p_Operate.add(btn_add);
        p_Operate.add(Box.createHorizontalGlue());

        btn_del = new JButton("删除");
        btn_del.setPreferredSize(new Dimension(100, 75));
        p_Operate.add(btn_del);
        p_Operate.add(Box.createHorizontalGlue());

        btn_edit = new JButton("修改");
        btn_edit.setPreferredSize(new Dimension(100, 75));
        p_Operate.add(btn_edit);
        p_Operate.add(Box.createHorizontalGlue());

        btn_query = new JButton("查询");
        btn_query.setPreferredSize(new Dimension(100, 75));
        p_Operate.add(btn_query);
        p_Operate.add(Box.createHorizontalGlue());

        btn_query.addActionListener(e -> updateTableData("select * from staff_management"));

        frame.add(p_Center, BorderLayout.NORTH);
        frame.add(p_Graph, BorderLayout.CENTER);
        frame.add(p_Operate, BorderLayout.SOUTH);

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // 用于更新表格数据的方法
    private void updateTableData(String sql) {
        // 获取查询数据
        TableData tableData = TableData.getData(sql);

        // 获取列名和数据
        List<String> columnNames = tableData.getColumnNames();
        List<Object[]> data = tableData.getData();

        // 创建 DefaultTableModel 并设置列名
        DefaultTableModel tableModel = new DefaultTableModel();
        for (String columnName : columnNames) {
            tableModel.addColumn(columnName);
        }

        // 添加数据到表格模型
        for (Object[] rowData : data) {
            tableModel.addRow(rowData);
        }

        // 创建 JTable 并设置模型
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        p_Graph.removeAll();
        p_Graph.setLayout(new BorderLayout());
        p_Graph.add(scrollPane, BorderLayout.CENTER);
        p_Graph.revalidate();
        p_Graph.repaint();
    }
}