package cn.guet.view;

import cn.guet.control.middle.ExecuteUpdate;
import cn.guet.control.utils.QueryParameter;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class Graph_Add extends JFrame {
    private JButton jb_add, jb_cancel, jb_clear;
    public ExecuteUpdate ex = new ExecuteUpdate();
    String sql = "select * from ";
    String tableENName;
    private Map<String, JComponent> labelText;
    private int textSize;
    private Render_TableData render_tableData = new Render_TableData();

    static Map<String, String> tableNameMapping = new HashMap<>();

    static {
        tableNameMapping.put("cn_staff_management", "staff_management");
        tableNameMapping.put("cn_salary_management", "salary_management");
        tableNameMapping.put("cn_assessment_management", "assessment_management");
        tableNameMapping.put("cn_recruitment_management", "recruitment_management");
        tableNameMapping.put("cn_training_management", "training_management");
    }

    public Graph_Add(String tableENName) {
        this.tableENName = tableENName;

        // 渲染表单
        JPanel jp_North = new JPanel();
        Render_FormData rf = new Render_FormData();
        rf.getFormData(jp_North, tableENName);

        // 获取labelText和textSize
        this.labelText = rf.getLabelText();
        this.textSize = rf.getTextSize();

        jb_add = new JButton("提交");
        jb_clear = new JButton("清空");
        jb_cancel = new JButton("取消");

        JPanel jp_South = new JPanel();
        jp_South.add(jb_add);
        jp_South.add(jb_cancel);
        jp_South.add(jb_clear);

        // 临时面板，让界面的组件左右留出空间
        JPanel jp_Temp1 = new JPanel();
        jp_Temp1.setPreferredSize(new Dimension(25, 100));
        JPanel jp_Temp2 = new JPanel();
        jp_Temp2.setPreferredSize(new Dimension(25, 100));

        this.add(jp_North);
        this.add(jp_South, BorderLayout.SOUTH);
        this.add(jp_Temp1, BorderLayout.EAST);
        this.add(jp_Temp2, BorderLayout.WEST);

        jb_add.addActionListener(e -> add());
        jb_clear.addActionListener(e -> clear());
        jb_cancel.addActionListener(e -> cancel());

        this.setTitle("添加");
        this.setSize(300, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void clear() {
        labelText.values().forEach(comp -> {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setText("");
            } else if (comp instanceof JPanel) { // Assuming this JPanel contains JRadioButtons
                Arrays.stream(((JPanel) comp).getComponents())
                        .filter(c -> c instanceof JRadioButton)
                        .forEach(c -> ((JRadioButton) c).setSelected(false));
            }
        });
    }

    public void cancel() {
        this.dispose();
    }


    public void add() {
        ArrayList<Object> parameters = new ArrayList<>();
        AtomicBoolean valid = new AtomicBoolean(true); // 添加一个标志以跟踪输入是否有效
        StringBuilder errorMessage = new StringBuilder();

        Pattern phonePattern = Pattern.compile("^1[3-9]\\d{9}$"); // 手机号码正则表达式

        labelText.forEach((key, comp) -> {
            if (comp instanceof JTextField) {
                String text = ((JTextField) comp).getText();
                if (text.isEmpty()) {
                    // 处理空输入框
                    errorMessage.append(key).append(" 输入框不能为空\n");
                    valid.set(false); // 设置标志为无效
                } else if ("员工联系方式".equals(key)) {
                    // 检查手机号码的合法性
                    if (!phonePattern.matcher(text).matches()) {
                        errorMessage.append("手机号码不合法\n");
                        valid.set(false); // 设置标志为无效
                    } else {
                        parameters.add(text);
                    }
                } else {
                    parameters.add(text);
                }
            } else if (comp instanceof JPanel) { // 假设这个JPanel包含JRadioButton
                boolean selected = false;
                for (Component c : ((JPanel) comp).getComponents()) {
                    if (c instanceof JRadioButton && ((JRadioButton) c).isSelected()) {
                        parameters.add(((JRadioButton) c).getText());
                        selected = true;
                        break; // 找到已选择的按钮后退出循环
                    }
                }
                if (!selected) {
                    // 处理未选择任何选项的情况
                    errorMessage.append(key).append(" 必须选择一个选项\n");
                    valid.set(false); // 设置标志为无效
                }
            }
        });

        if (!valid.get()) {
            JOptionPane.showMessageDialog(null, errorMessage.toString(), "警告", JOptionPane.WARNING_MESSAGE);
            return; // 如果有无效输入，退出函数
        }

        Object[] parametersArray = parameters.toArray();
        QueryParameter qp = new QueryParameter(parametersArray);

        sql = "insert into " + tableNameMapping.getOrDefault(tableENName, tableENName) + " values(" + String.join(",", Collections.nCopies(parameters.size(), "?")) + ")";

        // 针对工资表特殊处理
        if (Objects.equals(tableENName, "cn_salary_management")) {
            sql = "insert into salary_management(sal_id, sal_name, sal_base, sal_reward, sal_subsidy) values(?,?,?,?,?)";
            System.out.println("sql改变了");
        }

        int rows = ex.execute(sql, qp);
        System.out.println(sql);

        if (rows > 0) {
            JOptionPane.showMessageDialog(this, "添加成功");
            clear();
            render_tableData.updateTableData("select * from " + tableENName, Graph_Main.p_Graph);
            Graph_Main.p_Graph.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "添加失败，请检查输入");
        }
    }

}


