package quartz;

import crud.BaseCRUD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InsertQuartz {
    private static final Logger logger = LoggerFactory.getLogger(InsertQuartz.class);

    public void insertTestToUsers(){
        String indexname = "users";
        String typename = "user";
        String chars = "abcdefghijklmnopqrstuvwxyz";
        String[] places = {"China","USA","Japen","UK","French","Genman"};
        Map data = new HashMap();
        StringBuffer username = new StringBuffer();
        username.append(Character.toUpperCase(chars.charAt((int)(Math.random()*chars.length()))));
        for (int i = 0;i<4;i++){
            username.append(chars.charAt((int)(Math.random()*chars.length())));
        }
        String address = places[(int)(Math.random()*places.length)];
        Integer age = (int)(Math.random()*100);
        Long time = new Date().getTime();
        data.put("username",username);
        data.put("address",address);
        data.put("age",age);
        data.put("create_at",time);
        System.out.println(BaseCRUD.indexDoc(indexname,typename,data));
//        logger.info(BaseCRUD.indexDoc(indexname,typename,data),data);
    }
}
