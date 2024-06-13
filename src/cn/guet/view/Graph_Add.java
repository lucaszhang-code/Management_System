package cn.guet.view;
import cn.guet.control.middle.ExecuteUpdate;
import cn.guet.control.utils.QueryParameter;
import javax.swing.*;
import java.awt.*;
import cn.guet.control.dao.DAO;


public class Graph_Add extends JFrame {
    private JLabel jl_name, jl_gender, jl_birth, jl_contact, jl_department, jl_job;
    private JTextField  jtf_name, jtf_gender, jtf_birth, jtf_contact, jtf_department, jtf_job;
    private JButton jb_add, jb_cancel, jb_clear;
    private Font myFont = new Font("宋体", Font.BOLD, 20);
    public ExecuteUpdate ex = new ExecuteUpdate();

    public Graph_Add() {
        jl_name = new JLabel("姓名");
        jl_name.setFont(myFont);
        jl_gender = new JLabel("性别");
        jl_gender.setFont(myFont);
        jl_birth  = new JLabel("生日");
        jl_birth.setFont(myFont);
        jl_contact = new JLabel("电话");
        jl_contact.setFont(myFont);
        jl_department = new JLabel("部门");
        jl_department.setFont(myFont);
        jl_job = new JLabel("职位");
        jl_job.setFont(myFont);

        jtf_name = new JTextField(11);
        jtf_gender = new JTextField(11);
        jtf_birth = new JTextField(11);
        jtf_contact = new JTextField(11);
        jtf_department = new JTextField(11);
        jtf_job = new JTextField(11);

        jb_add = new JButton("提交");
        jb_clear = new JButton("清空");
        jb_cancel = new JButton("取消");

        JPanel jp_North = new JPanel();
        JPanel jp_South = new JPanel();

        jp_North.setLayout(new GridLayout(6,2));

        jp_North.add(jl_name);
        jp_North.add(jtf_name);
        jp_North.add(jl_gender);
        jp_North.add(jtf_gender);
        jp_North.add(jl_birth);
        jp_North.add(jtf_birth);
        jp_North.add(jl_contact);
        jp_North.add(jtf_contact);
        jp_North.add(jl_department);
        jp_North.add(jtf_department);
        jp_North.add(jl_job);
        jp_North.add(jtf_job);

        jp_South.add(jb_add);
        jp_South.add(jb_cancel);
        jp_South.add(jb_clear);

        // 临时面板，让界面的组件左右留出空间
        JPanel jp_Temp1 = new JPanel();
        jp_Temp1.setPreferredSize(new Dimension(50, 100));
//        jp_Temp1.setBackground(new Color(255,255,255));
        JPanel jp_Temp2 = new JPanel();
        jp_Temp2.setPreferredSize(new Dimension(50, 100));


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

    public static void main(String[] args) {
        new Graph_Add();
    }

    public void clear(){
        jtf_name.setText("");
        jtf_birth.setText("");
        jtf_gender.setText("");
        jtf_birth.setText("");
        jtf_contact.setText("");
        jtf_department.setText("");
        jtf_job.setText("");
    }

    public void cancel(){
        this.dispose();
    }

    public void add(){
        String name = jtf_name.getText();
        String gender = jtf_gender.getText();
        String birth = jtf_birth.getText();
        String contact = jtf_contact.getText();
        String department = jtf_department.getText();
        String job = jtf_job.getText();

        String sql = "insert into staff_management ( sta_name, sta_gender, sta_birth, sta_contact_info, sta_department, sta_job)\n" +
                "values (?,?,?,?,?,?)";

        int rows = ex.execute(sql, new QueryParameter(name, gender, birth, contact, department, job));
        if(rows != 0){
            JOptionPane.showMessageDialog(null, "添加成功");
            clear();
        }
    }
}
