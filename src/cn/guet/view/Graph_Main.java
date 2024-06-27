package cn.guet.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class Graph_Main extends JFrame {
    private static JPanel p_Center = new JPanel(new BorderLayout());
    public static  JPanel p_Graph = new JPanel();
    private static JPanel p_Operate = new JPanel();
    private static JButton btn_add;
    private static JButton btn_del;
    private static JButton btn_edit;
    private static JButton btn_query;
    private static JButton btn_generate_report;
    private static JComboBox cb_sel;
    private static final Map<String, String> tableNameMapping = new HashMap<>();
    private static String query_sql = "select * from ";
    private String selectedItem = "职员表";
    private static Render_TableData render_tableData = new Render_TableData();

    static {
        tableNameMapping.put("职员表", "cn_staff_management");
        tableNameMapping.put("薪资表", "cn_salary_management");
        tableNameMapping.put("考核管理表", "cn_assessment_management");
        tableNameMapping.put("应聘管理表", "cn_recruitment_management");
        tableNameMapping.put("培训管理表", "cn_training_management");
    }

    Graph_Main() {
        JFrame frame = new JFrame("人力资源管理系统");

        render_tableData.updateTableData("select * from cn_staff_management",p_Graph);

        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu selMenu = new JMenu("查询");
        menuBar.add(selMenu);
        JMenuItem selItem = new JMenuItem("查询");
        selMenu.add(selItem);
        selItem.addActionListener(e -> sel_query(selectedItem));

        JMenu updMenu = new JMenu("更新");
        menuBar.add(updMenu);
        JMenuItem addItem = new JMenuItem("添加");
        updMenu.add(addItem);
        // 添加按钮事件
        addItem.addActionListener(e -> {
            Graph_Add graph_add = new Graph_Add(getEnglishTableName(selectedItem));
            graph_add.setVisible(true);
        });

        JMenuItem delItem = new JMenuItem("删除");
        updMenu.add(delItem);
        delItem.addActionListener(e -> new Graph_Delete(getEnglishTableName(selectedItem)));

        JMenuItem chanItem = new JMenuItem("修改");
        updMenu.add(chanItem);
        chanItem.addActionListener(e -> new Graph_Alter(getEnglishTableName(selectedItem)));

        JMenu BehMenu = new JMenu("状态");
        menuBar.add(BehMenu);
        JMenuItem refItem = new JMenuItem("生成报表");
        refItem.addActionListener(e -> new Graph_Generate_report(getColumnNames(), tableNameMapping.get(selectedItem)));
        BehMenu.add(refItem);

        JMenu helpMenu = new JMenu("帮助");
        menuBar.add(helpMenu);

        // 创建标题面板及标签
        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("人力资源管理系统", SwingConstants.CENTER);
        title.setFont(new Font("Microsoft YaHei", Font.BOLD, 36)); // 设置字体样式
        titlePanel.add(title); // 添加标题到标题面板

        // 创建下拉菜单
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, 100));
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(100, 100));
        String[] items = {"职员表", "薪资表", "考核管理表", "应聘管理表", "培训管理表"};
        JComboBox<String> cb_sel = new JComboBox<>(items);
        rightPanel.add(cb_sel);

        // 选择列表的逻辑函数
        cb_sel.addActionListener(e -> {
            selectedItem = (String) cb_sel.getSelectedItem();
            sel_query(selectedItem);
        });

        // 布局组件
        p_Center.add(titlePanel, BorderLayout.CENTER); // 居中添加标题面板
        p_Center.add(leftPanel, BorderLayout.WEST); // 右侧添加下拉菜单
        p_Center.add(rightPanel, BorderLayout.EAST);

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

        btn_generate_report = new JButton("生成报表");
        btn_generate_report.setPreferredSize(new Dimension(100, 75));
        p_Operate.add(btn_generate_report);
        p_Operate.add(Box.createHorizontalGlue());

        /**
         * 添加信息按钮事件
         * 需要传递不同的值，告诉新窗口要添加哪张表的数据
         */
        btn_add.addActionListener(e -> new Graph_Add(tableNameMapping.get(selectedItem)));
        btn_del.addActionListener(e -> new Graph_Delete(tableNameMapping.get(selectedItem)));
        btn_edit.addActionListener(e -> new Graph_Alter(tableNameMapping.get(selectedItem)));
        btn_query.addActionListener(e -> sel_query(selectedItem));
        btn_generate_report.addActionListener(e -> new Graph_Generate_report(getColumnNames(), tableNameMapping.get(selectedItem)));


        frame.add(p_Center, BorderLayout.NORTH);
        frame.add(p_Graph, BorderLayout.CENTER);
        frame.add(p_Operate, BorderLayout.SOUTH);

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * 根据中文表名获取对应的英文表名
     * @param chineseName 中文表名
     * @return 英文表名，如果未找到则返回null
     */
    public static String getEnglishTableName(String chineseName) {
        return tableNameMapping.get(chineseName);
    }


    /**
     * 构建SQL查询语句
     * @param tableName 表名
     * @return 完整的SQL查询语句
     */
    public static void sel_query(String tableName) {
        String tableENName = getEnglishTableName(tableName);
        String result_sql = query_sql + tableENName;
        render_tableData.updateTableData(result_sql,p_Graph);
    }

    public List<String> getColumnNames() {
        return render_tableData.getColumnNames();
    }

    public static void main(String[] args) {
        new Graph_Main();
    }
}