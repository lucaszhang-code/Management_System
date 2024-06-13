package cn.guet.control.middle;

import cn.guet.control.dao.DAO;
import cn.guet.control.utils.QueryParameter;

public class ExecuteUpdate {
    public int execute(String sql, QueryParameter... queryParameters){
        DAO dao = new DAO();
        Object[] params = queryParameters.length > 0 ? queryParameters[0].getParameters() : new Object[]{};
        int rows = dao.update(sql, params);
        return rows;
    }
}
