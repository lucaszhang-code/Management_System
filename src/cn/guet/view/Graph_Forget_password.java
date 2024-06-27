package cn.guet.view;

import cn.guet.control.dao.DAO;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Graph_Forget_password {
    private Font myFont = new Font("宋体", Font.BOLD, 16);
    JFrame frame;
    private JTextField text_user;
    private JPasswordField text_password;
    private JPasswordField text_checked_password;
    private String user;
    private String password;
    private String checked_password;

    Graph_Forget_password() {
        frame = new JFrame("修改密码");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        JPanel p_center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JPanel p_top = new JPanel(new FlowLayout());
        JLabel label_title = new JLabel("修改密码");
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

        JLabel label_check_password = new JLabel("确认密码");
        label_check_password.setFont(myFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        p_center.add(label_check_password, gbc);

        text_checked_password = new JPasswordField(15);
        text_checked_password.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        p_center.add(text_checked_password, gbc);

        JPanel p_bottom = new JPanel();
        p_bottom.setPreferredSize(new Dimension(100, 50));
        JButton btn_edit = new JButton("修改");
        btn_edit.setPreferredSize(new Dimension(100, 30));
        p_bottom.add(btn_edit);

        btn_edit.addActionListener(e -> edit_password());

        frame.add(p_top, BorderLayout.NORTH);
        frame.add(p_center, BorderLayout.CENTER);
        frame.add(p_bottom, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void edit_password() {
        user = text_user.getText();
        password = text_password.getText();
        checked_password = text_checked_password.getText();
        if(password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "密码不能为空");
            return;
        }

        if (password.equals(checked_password)) {
            DAO dao = new DAO();
            int rows = dao.update("update administrator set password = " + "'" + checked_password + "'"
                    + "where admin_user = " + "'" + user + "'");
            if(rows > 0) {
                JOptionPane.showMessageDialog(null, "修改成功");
                text_user.setText("");
                text_password.setText("");
                text_checked_password.setText("");
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "两次密码不一致");
        }
    }



}
