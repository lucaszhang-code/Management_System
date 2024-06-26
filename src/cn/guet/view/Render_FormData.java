package cn.guet.view;

import cn.guet.control.middle.TableData;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Render_FormData {
    public Map<String, JComponent> labelText;
    public Font myFont = new Font("宋体", Font.BOLD, 16);
    public List<String> columnNames;
    private int textSize;

    /**
     * 从指定的表中获取数据表单的布局信息。
     * @param panel 用于承载表单组件的面板。
     * @param tableENName 表的英文名称，用于查询数据库表结构。
     */
    public void getFormData(JPanel panel, String tableENName){
        labelText = new LinkedHashMap<>();
        TableData tableData = TableData.getData("select * from " + tableENName);
        columnNames = tableData.getColumnNames();
        textSize = columnNames.size();

        int visibleFieldsCount = (int) columnNames.stream().filter(name -> !name.equals("工资总额")).count();

        panel.setLayout(new GridLayout(visibleFieldsCount, 2));

        for (String columnName : columnNames) {
            if (columnName.equals("工资总额")) {
                continue; // 跳过 sal_sum 字段
            }

            JLabel label = new JLabel(columnName);
            label.setFont(myFont);
            panel.add(label);

            if(columnName.equals("员工性别")){
                JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JRadioButton maleButton = new JRadioButton("男");
                JRadioButton femaleButton = new JRadioButton("女");
                maleButton.setFont(myFont);
                femaleButton.setFont(myFont);

                ButtonGroup group = new ButtonGroup();
                group.add(maleButton);
                group.add(femaleButton);

                radioPanel.add(maleButton);
                radioPanel.add(femaleButton);

                panel.add(radioPanel);
                labelText.put(columnName, radioPanel);
            } else {
                JTextField textField = new JTextField(5);
                textField.setFont(myFont);
                panel.add(textField);
                labelText.put(columnName, textField);
            }
        }
    }

    public Map<String, JComponent> getLabelText() {
        return labelText;
    }

    public int getTextSize() {
        return textSize;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }
}
