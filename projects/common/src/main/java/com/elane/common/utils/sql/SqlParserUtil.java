package com.elane.common.utils.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.TablesNamesFinder;

/***
 * 单句Sql解析器制造工厂
 * 
 * @author
 *
 */
public class SqlParserUtil {
    /***
     * 解析SQL返回表名List
     * 
     * @param sql
     * @return List<String>
     * @throws JSQLParserException
     */
    public static List<String> getTableNameBySql(String sql) throws JSQLParserException {

        Statement statement = CCJSqlParserUtil.parse(sql);
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(statement);

        return tableList;
    }

    /***
     * 解析SQL返回类型<br>
     * (INSERT|DELETE|UPDATE|SELECT|TRUNCATE|ALTER|CREATETABLE|CREATEINDEX|CREATEVIEW)
     * @param sql
     * @return String
     * @throws JSQLParserException
     */
    public static String getSQLTypeBySql(String sql) throws JSQLParserException {

        Statement statement = CCJSqlParserUtil.parse(sql);
        if (statement instanceof Insert) {
            return "INSERT";
        }
        if (statement instanceof Delete) {
            return "DELETE";
        }
        if (statement instanceof Update) {
            return "UPDATE";
        }
        if (statement instanceof Select) {
            return "SELECT";
        }
        if (statement instanceof Truncate) {
            return "TRUNCATE";
        }
        if (statement instanceof Alter) {
            return "ALTER";
        }
        if (statement instanceof CreateTable) {
            return "CREATETABLE";
        }
        if (statement instanceof CreateIndex) {
            return "CREATEINDEX";
        }
        if (statement instanceof CreateView) {
            return "CREATEVIEW";
        }
        return "";
    }

    /***
     * 将ResultSet转换为Json
     * @param rs
     * @return json
     * @throws Exception
     */
    public static String resultSet2Json(ResultSet rs) throws Exception {
        JsonArray jsonArray = new JsonArray();
        if(rs==null){
            return "";
        }
        // 获取列数  
        ResultSetMetaData metaData = rs.getMetaData();  
        int columnCount = metaData.getColumnCount();  
         
        // 遍历ResultSet中的每条数据  
         while (rs.next()) {  
             JsonObject jsonObj = new JsonObject();  
              
             // 遍历每一列  
             for (int i = 1; i <= columnCount; i++) {  
                 String columnName =metaData.getColumnLabel(i);  
                 String value = rs.getString(columnName);  
                 jsonObj.addProperty(columnName, value);  
             }   
             jsonArray.add(jsonObj);   
         }
        return jsonArray.toString();
    }
}
