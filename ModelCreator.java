package tw.toothpick;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class ModelCreator {

    public static void main (String args[]) throws Exception {
        ModelCreator mtc = new ModelCreator();
        List<String> table= new ArrayList<String>();
        JSONArray array=mtc.selectAllTable();
        for (int i=0;i<array.length();i++){
            JSONObject json= (JSONObject) array.get(i);
            String table_name=json.getString("table_name");
            if(!table.contains(table_name)){
                table.add(table_name);
                mtc.createIt(table_name,array);
            }
        }
        System.out.println(table);
    }
    public JSONArray selectAllTable() throws Exception {
        Jdbc jdbc=new Jdbc();
        return jdbc.getAllTable();
    }
    public void createIt(String table_name,JSONArray array) {
        String packageName = getClass().getPackage().getName();


        try {
            String className=table_name.substring(0, 1).toUpperCase() + table_name.substring(1)+"Model";//class name
            File file = new File("src\\main\\java\\tw\\toothpick\\model\\"+className+".java");// path ande file name
            file.setWritable(true);
            file.setReadable(true);
            FileWriter aWriter = new FileWriter(file, true);

            aWriter.write("//此段程式碼由程式自動產生 編譯後 修改可能會覆蓋 auth:CB zhang\n");
            aWriter.write("package "+ packageName+".model;\n");//package
            aWriter.write("import lombok.Data;\n" +
                    "import org.springframework.stereotype.Component;\n" +
                    "\n" +
                    "@Component@Data\n");
            aWriter.write("public class "+ className + "{\n");
            for(int i=0;i<array.length();i++){
                JSONObject json= (JSONObject) array.get(i);
                String type=json.getString("data_type");
                String column=json.getString("column_name");
                String table=json.getString("table_name");
                System.out.println(type+"---"+column);
                String vType="";
                if(Objects.equals(table, table_name)){
                    if(Objects.equals(type, "int")){
                        vType="int";
                    }
                    else {
                        vType="String";
                    }
                    aWriter.write("\tprivate "+vType+" "+column+";\n" );//get;set



                    aWriter.write("\tpublic "+vType+" get"+column.substring(0, 1).toUpperCase() + column.substring(1)+"() {return "+column+";}\n" +
                                      "\tpublic void set"+column.substring(0, 1).toUpperCase() + column.substring(1)+"("+vType+" "+column+") {this."+column+" = " +column+";}\n");
                }
            }
            aWriter.write(" }\n");
            aWriter.flush();
            aWriter.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        table_name=table_name.substring(0, 1).toUpperCase() + table_name.substring(1);

        try {
            String className=table_name+"Service";
            File file = new File("src\\main\\java\\tw\\toothpick\\service\\"+className+".java");
            file.setWritable(true);
            file.setReadable(true);
            FileWriter aWriter = new FileWriter(file, true);

            aWriter.write("//此段程式碼部分由程式自動產生 編譯後ModelCreator 修改會被覆蓋 auth:CB zhang\n");
            aWriter.write("package "+ packageName+".service;\n");
            aWriter.write("import org.springframework.beans.factory.annotation.Autowired;\n" +
                    "import org.springframework.stereotype.Service;\n" +
                    "import tw.toothpick.model."+table_name+"Model;\n" +
                    "import tw.toothpick.repository."+table_name+"Repository;\n" +
                    "@Service\n" +
                    "public class "+table_name+"Service {\n" +
                    "    @Autowired\n" +
                    "    "+table_name+"Repository "+table_name+"Repository; \n" +
                    "}");
            aWriter.flush();
            aWriter.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        try {
            String className=table_name+"Repository";
            File file = new File("src\\main\\java\\tw\\toothpick\\Repository\\"+className+".java");
            file.setWritable(true);
            file.setReadable(true);
            FileWriter aWriter = new FileWriter(file, true);
            aWriter.write("//此段程式碼部分由程式自動產生 編譯後ModelCreator 修改會被覆蓋 auth:CB zhang\n");
            aWriter.write("package "+ packageName+".repository;\n");
            aWriter.write("import org.springframework.beans.factory.annotation.Autowired;\n" +
                    "import org.springframework.jdbc.core.JdbcTemplate;\n" +
                    "import org.springframework.stereotype.Repository;\n" +
                    "import tw.toothpick.model."+table_name+"Model;\n" +
                    "\n" +
                    "@Repository\n" +
                    "public class "+table_name+"Repository {\n" +
                    "\n" +
                    "    @Autowired\n" +
                    "    private JdbcTemplate jdbcTemplate;\n" +
                    "};");
            aWriter.flush();
            aWriter.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        try {
            String className=table_name+"Controller";
            File file = new File("src\\main\\java\\tw\\toothpick\\Controller\\"+className+".java");
            file.setWritable(true);
            file.setReadable(true);
            FileWriter aWriter = new FileWriter(file, true);
            aWriter.write("//此段程式碼部分由程式自動產生 編譯後ModelCreator 修改會被覆蓋 auth:CB zhang\n");
            aWriter.write("package "+ packageName+".controller;\n");
            aWriter.write("import org.springframework.beans.factory.annotation.Autowired;\n" +
                    "import org.springframework.web.bind.annotation.RequestMapping;\n" +
                    "import org.springframework.web.bind.annotation.RestController;\n" +
                    "import tw.toothpick.model."+table_name+"Model;\n" +
                    "import tw.toothpick.service."+table_name+"Service;\n" +
                    "\n" +
                    "@RestController\n" +
                    "public class "+table_name+"Controller {\n" +
                    "\n" +
                    "    @Autowired\n" +
                    "    "+table_name+"Model "+table_name+"Model;\n" +
                    "\n" +
                    "    @Autowired\n" +
                    "    "+table_name+"Service "+table_name+"Service;\n" +
                    "}");

            aWriter.flush();
            aWriter.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
