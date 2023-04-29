package tw.toothpick;



import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class Jdbc {
    public JSONArray getAllTable() throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("select  table_name,data_type,column_name from information_schema.columns where table_schema = 'aaa' order by table_name");
        ResultSet myRs = statement.executeQuery();
        JSONArray array=convert(myRs);
        connection.close();
        return array;
    }
    public static JSONArray convert(ResultSet resultSet) throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int columns = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < columns; i++)
                obj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));
            jsonArray.put(obj);
        }
        return jsonArray;
    }
    private static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到驅動程式類別");
            e.printStackTrace();
        }
        String serverName = "localhost";
        String database = "aaa";
        String url = "jdbc:mysql://" + serverName + "/" + database;
        // 帳號和密碼
        String user = "root";
        String password = "NewPassword";
        return DriverManager.getConnection(url, user, password);
    }
}