import base.BaseConst;
import crud.BaseCRUD;
import crud.Mysearch;
import org.junit.Test;
import quartz.InsertQuartz;

import java.util.*;

public class TestMysearch {
    @Test
    public void test1(){
        Map<String,Object> esdata = new HashMap<String,Object>();
        esdata.put(BaseConst.ES_FIELD_NAME,"zhangsan");
        esdata.put(BaseConst.ES_FIELD_AGE,22);
        esdata.put(BaseConst.ES_FIELD_ADDRESS,"China shandong");
        Long createTime = new Date().getTime();
        esdata.put(BaseConst.ES_FIELD_CREATE_AT,createTime);

        String responce = Mysearch.addindex(BaseConst.ES_INDEX_USERS, BaseConst.ES_TYPE_USER,esdata);
        System.out.println(responce);
    }

    @Test
    public void testToCreateIndex(){
        String indexname = "tests";
        String typename = "test";
        Map<String,String> fields = new HashMap<String, String>();
        fields.put("colname","test1");
        fields.put("type","String");
        List<Map<String,String>> result = new LinkedList<Map<String, String>>();
        result.add(fields);
        try{
            BaseCRUD.createIndices(typename,indexname,result,null,null);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println();
        }

    }

    @Test
    public void testToCreateDoc() {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("create_at",new Date().getTime());
        map.put("create_at_time",new Date());
        map.put("message","just hava a try");
        map.put("test11","testautoincreate");
        String indexname = "tests";
        String typename = "test";
        System.out.println(BaseCRUD.indexDoc(indexname,typename,map));
    }

    @Test
    public void testToRand(){
        InsertQuartz insertQuartz = new InsertQuartz();
        int n = 10;
        for (int i = 0;i<n;i++){
            insertQuartz.insertTestToUsers();
        }

    }
}
