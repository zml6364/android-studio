package cn.edu.bzu.ie.mysqldb;

import java.io.Serializable;

/**
 * 数据实体类
 */
public class Userinfo implements Serializable {
    /*private int index;
    private String material;
    private String diameter;
    private String method;*/
    private String ParamName;
    private String ParamValue;

    public Userinfo(int index, String material, String diameter, String method, String pname, String pvalue) {
            /*this.index = index;
            this.material = material;
            this.diameter = diameter;
            this.method = method;*/
            this.ParamName = pname;
            this.ParamValue = pvalue;
        }

    public Userinfo() {
    }

    /*public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }*/

    public String getParamName() {
        return ParamName;
    }

    public void setParamName(String paramName) {
        ParamName = paramName;
    }

    public String getParamValue() {
        return ParamValue;
    }

    public void setParamValue(String paramValue) {
        ParamValue = paramValue;
    }
}