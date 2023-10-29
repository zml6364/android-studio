package cn.edu.bzu.ie.mysqldb;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作类
 */
public class UserDao extends DbOpenHelper {

    /**
     * 验证数据库是否存在输入数据
     */
    public Userinfo Query(String material, String diameter, String method){
        Userinfo item = null;
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from paramsdatatable where WeldingMaterial=? and WireDiameter=? and WeldingMethod=? and ParamIndex=1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, material);
            pStmt.setString(2, diameter);
            pStmt.setString(3, method);
            rs = pStmt.executeQuery();
            if(rs.next()){
                item = new Userinfo();
                /*item.setMaterial(material);
                item.setDiameter(diameter);
                item.setMethod(method);*/
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return item;
    }

    /**
     * 按材料、直径、方法查询参数
     * @return Userinfo 实例
     */
    public List<Userinfo> getDataByMaterialAndDiameterAndMethod(String material, String diameter, String method){
        List<Userinfo> list = new ArrayList<>();
        Userinfo item = null;
        try{
            getConnection();   // 取得连接信息
            String sql = "select * from paramsdatatable where WeldingMaterial=? and WireDiameter=? and WeldingMethod=? and ParamIndex=1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, material);
            pStmt.setString(2, diameter);
            pStmt.setString(3, method);
            rs = pStmt.executeQuery();
            while (rs.next()){
                item = new Userinfo();
                /*item.setMaterial(material);
                item.setDiameter(diameter);
                item.setMethod(method);*/
                item.setParamName(rs.getString("ParamName"));
                item.setParamValue(rs.getString("ParamValue"));
                list.add(item);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            closeAll();
        }
        return list;
    }
}