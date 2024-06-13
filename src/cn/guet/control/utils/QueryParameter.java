package cn.guet;

public class QueryParameter {
    private Object[] parameters;

    public QueryParameter(Object... parameters) {
        this.parameters = parameters;
    }

    public Object[] getParameters() {
        return parameters;
    }
}
