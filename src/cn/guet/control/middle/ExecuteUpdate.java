package cn.guet.control.middle;

import cn.guet.control.dao.DAO;
import cn.guet.control.utils.QueryParameter;

import java.util.Date;

public class TestUpdate {
    public static void main(String[] args) {
        String sql = "insert into staff_management ( sta_name, sta_gender, sta_birth, sta_contact_info, sta_department, sta_job)\n" +
                "values (?,?,?,?,?,?)";
        DAO dao  =new DAO();
//        Object[] params = {"朱远威", "男", "2023-01-01", "12345678909", "宣传部", "职员"};
        int rows = dao.update(sql, new QueryParameter("朱远威", "男", "2023-01-01", "12345678909", "宣传部", "职员"));
        System.out.println(rows);
    }
}
