package cn.guet.view;

import cn.guet.control.middle.ExecuteUpdate;
import cn.guet.control.middle.TableData;
import cn.guet.control.utils.QueryParameter;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class Graph_Add extends JFrame {
    private JButton jb_add, jb_cancel, jb_clear;
    public ExecuteUpdate ex = new ExecuteUpdate();
    String sql = "select * from ";
    String tableENName;
    private Map<String, JTextField> labelText;
    private int textSize;

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
        for (JTextField textField : labelText.values()) {
            textField.setText("");
        }
    }

    public void cancel() {
        this.dispose();
    }

    public void add() {
        StringBuilder insertNum = new StringBuilder(" values (");
        for (int i = 0; i < textSize; i++) {
            if (i == textSize - 1) {
                insertNum.append("?");
            } else {
                insertNum.append("?, ");
            }
        }
        insertNum.append(")");

        // 针对工资表特殊处理
        if(Objects.equals(tableENName, "cn_salary_management")) {
            sql = "insert into salary_management(sal_id, sal_name, sal_base, sal_reward, sal_subsidy) values(?,?,?,?,?)";
            System.out.println("sql改变了");
        }
        else{
            sql = "insert into " + tableNameMapping.get(tableENName) + insertNum;
        }


        // 直接从labelText中获取文本值并创建QueryParameter实例
        Object[] parameters = new Object[labelText.size()];
        int index = 0;
        for (JTextField textField : labelText.values()) {
            parameters[index++] = textField.getText();
        }


        QueryParameter qp = new QueryParameter(parameters);

        // 确保parameters的数量与SQL预设的问号数量匹配
        if (parameters.length <= textSize) { // 假设最大参数数量为6，根据实际情况调整
            int rows = ex.execute(sql, qp);
            System.out.println(sql);

            for (Object str : parameters) {
                System.out.println(str);
            }
            if (rows != 0) {
                JOptionPane.showMessageDialog(null, "添加成功");
                clear();
            } else {
                JOptionPane.showMessageDialog(null, "添加失败，请检查输入");
            }
        } else {
            JOptionPane.showMessageDialog(null, "输入参数过多，请检查");
        }
    }

}
