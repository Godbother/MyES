package crud;

import client.MyESClient;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BaseCRUD {
    private static TransportClient client = MyESClient.getClient();
    //创建es索引
    public static String createIndices(String typename, String indexname, List<Map<String,String>> fields)throws Exception{
//        List<Map<String,String>> standerdFields = helpToCharge(fields);
        XContentBuilder mapping = getXcontent(typename,fields);
        CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(indexname)
                .setSource(mapping);
        CreateIndexResponse response = cirb.execute().actionGet();
        if (response.isAcknowledged()){
            return indexname;
        }else {
            return null;
        }
    }
    //处理mapping内容
    public static XContentBuilder getXcontent(String typename, List<Map<String,String>> fields){
        if (fields == null){
            return null;
        }
        try{
            //初始化开头的setting
            XContentBuilder mapping = XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject("settings")
                            .field("number_of_shards",5)
                            .field("number_of_replicas",0)
                        .endObject()
                    .endObject()
                    .startObject()
                        .startObject(typename)
                            .startObject("properties")
                                .startObject("create_at").field("type","long").field("store","yes")
                                .endObject()
                                .startObject("create_at_time").field("type","date").field("format","dateOptionalTime")
                                .field("store","yes")
                                .endObject()
                                .startObject("message").field("type","string").field("index","not_analyzed")
                                .field("store","yes")
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject();
            return mapping;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    //容错处理
    public static List<Map<String,String>> helpToCharge(List<Map<String,String>> fields){
        List<Map<String,String>> newfields = new LinkedList<Map<String, String>>();
        for (Map<String,String> map:fields) {
            try{
                map.get("analysis");
            }catch (Exception e){
                map.put("analysis","default");
            }
            try{
                map.get("store");
            }catch (Exception e){
                map.put("store","default");
            }
            newfields.add(map);
        }
        return newfields;
    }


}
