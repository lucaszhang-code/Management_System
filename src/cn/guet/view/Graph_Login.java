package cn.guet.view;

import cn.guet.control.dao.DAO;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Graph_Login {
    private Font myFont = new Font("宋体", Font.BOLD, 16);
    JFrame frame;
    private JTextField text_user;
    private JPasswordField text_password;
    private String user;
    private String password;
    private String sql = "select * from administrator where admin_user = ";

    Graph_Login() {
        frame = new JFrame("登录界面");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p_center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JPanel p_top = new JPanel(new FlowLayout());
        JLabel label_title = new JLabel("欢迎登录");
        label_title.setPreferredSize(new Dimension(100, 50));
        label_title.setFont(new Font("宋体", Font.BOLD, 20));
        p_top.add(label_title);

        JLabel label_user = new JLabel("用户名");
        label_user.setFont(myFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        p_center.add(label_user, gbc);

        text_user = new JTextField(15);
        text_user.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        p_center.add(text_user, gbc);

        JLabel label_password = new JLabel("密码");
        label_password.setFont(myFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        p_center.add(label_password, gbc);

        text_password = new JPasswordField(15);
        text_password.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        p_center.add(text_password, gbc);

        JPanel p_bottom = new JPanel();
        p_bottom.setPreferredSize(new Dimension(100, 50));
        JButton btn_login = new JButton("登录");
        btn_login.setPreferredSize(new Dimension(100, 30));
        JButton btn_forget = new JButton("忘记密码");
        btn_forget.setPreferredSize(new Dimension(100, 30));
        p_bottom.add(btn_login);
        p_bottom.add(btn_forget);

        btn_login.addActionListener(e -> login());
        btn_forget.addActionListener(e -> new Graph_Forget_password());

        frame.add(p_top, BorderLayout.NORTH);
        frame.add(p_center, BorderLayout.CENTER);
        frame.add(p_bottom, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void login()  {
        user = text_user.getText();
        password = text_password.getText();
        DAO dao = new DAO();
        ResultSet rs = dao.query(sql + "'" + user + "'");
        System.out.println(sql + "'" + user + "'");
        try{
            if(rs.next()){
                if(rs.getString("password").equals(password)){
                    JOptionPane.showMessageDialog(null, "登录成功");
                    Graph_Main graph_main = new Graph_Main();
                    frame.setVisible(false);
                }
                else{
                    JOptionPane.showMessageDialog(null, "密码错误");
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "用户名不存在");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Graph_Login graph_login = new Graph_Login();
    }
}
