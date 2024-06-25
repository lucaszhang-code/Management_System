package cn.guet.view;

import cn.guet.control.middle.TableData;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Render_FormData {
    public Map<String, JTextField> labelText;
    public Font myFont = new Font("宋体", Font.BOLD, 16);
    public List<String> columnNames;
    private int textSize;

    /**
     * 从指定的表中获取数据表单的布局信息。
     * 此方法用于根据数据库表的结构动态生成表单，用于数据输入或显示。
     * @param panel 用于承载表单组件的面板。
     * @param tableENName 表的英文名称，用于查询数据库表结构。
     */
    public void getFormData(JPanel panel, String tableENName){
        labelText = new LinkedHashMap<>();
        // 得到当前表的表头数据，用于动态渲染label和textField个数
        TableData tableData = TableData.getData("select * from " + tableENName);
        columnNames = tableData.getColumnNames();
        textSize = columnNames.size();

        // 如果要跳过不显示 sal_sum 字段，需要调整 GridLayout 的行数
        int visibleFieldsCount = (int) columnNames.stream().filter(name -> !name.equals("工资总额")).count();


        panel.setLayout(new GridLayout(visibleFieldsCount, 2));

        for (String columnName : columnNames) {
            if (columnName.equals("工资总额")) {
                continue; // 跳过 sal_sum 字段
            }

            JLabel label = new JLabel(columnName);
            JTextField textField = new JTextField(5);
            textField.setFont(myFont);
            label.setFont(myFont);
            panel.add(label);
            panel.add(textField);
            labelText.put(columnName, textField);
        }
    }

    public Map<String, JTextField> getLabelText() {
        return labelText;
    }

    public int getTextSize() {
        return textSize;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }
}
