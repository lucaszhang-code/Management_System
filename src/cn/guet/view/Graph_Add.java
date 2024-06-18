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
    private Font myFont = new Font("宋体", Font.BOLD, 16);
    public ExecuteUpdate ex = new ExecuteUpdate();
    String sql = "select * from ";
    String tableENName;
    Map<String, JTextField> labelText;
    static Map<String, String> tableNameMapping = new HashMap<>();
    private int textSize;

    static {
        tableNameMapping.put("cn_staff_management", "staff_management");
        tableNameMapping.put("cn_salary_management","salary_management");
        tableNameMapping.put("cn_assessment_management", "assessment_management");
        tableNameMapping.put("cn_recruitment_management", "recruitment_management");
        tableNameMapping.put("cn_training_management", "training_management");
    }

    public Graph_Add(String tableENName) {
        this.tableENName = tableENName;
        labelText = new LinkedHashMap<>();


        // 得到当前表的表头数据，用于动态渲染label和textField个数
        TableData tableData = TableData.getData(sql + tableENName);
        List<String> columnNames = tableData.getColumnNames();
        textSize = columnNames.size();

        JPanel jp_North = new JPanel();
        JPanel jp_South = new JPanel();
        jp_North.setLayout(new GridLayout(columnNames.size(),2));

        for (String columnName : columnNames) {
            JLabel label  = new JLabel(columnName);
            JTextField textField = new JTextField(5);
            textField.setFont(myFont);
            label.setFont(myFont);
            jp_North.add(label);
            jp_North.add(textField);
            labelText.put(columnName, textField); // 现在这里不会抛出NullPointerException了
        }

        jb_add = new JButton("提交");
        jb_clear = new JButton("清空");
        jb_cancel = new JButton("取消");

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

//    public static void main(String[] args) {
//        new Graph_Add();
//    }

    public void clear() {
        for(JTextField textField : labelText.values()){
            textField.setText("");
        }
    }

    public void cancel(){
        this.dispose();
    }

    public void add() {
    StringBuilder insertNum = new StringBuilder(" values (");
    for(int i = 0; i < textSize; i++){
        if(i == textSize - 1){
            insertNum.append("?");
        } else {
            insertNum.append("?, ");
        }
    }
    insertNum.append(")");
    String sql = "insert into " + tableNameMapping.get(tableENName) + insertNum;

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

        for(Object str : parameters){
            System.out.println(str);
        }

        if(rows != 0){
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
