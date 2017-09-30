package crud;

import client.MyESClient;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BaseCRUD {
    private static TransportClient client = MyESClient.getClient();
    //创建es索引(按照默认配置创建index)
    public static void createIndices(String typename, String indexname, List<Map<String,String>> fields,Integer shard,Integer replic)throws Exception{
        XContentBuilder mapping = getXcontent(typename,fields);
        client.admin().indices().prepareCreate(indexname)
                .setSettings(Settings.builder()
                        .put("index.number_of_shards", shard==null?5:shard)
                        .put("index.number_of_replicas", replic==null?0:replic)
                )
                .get();
        client.admin().indices().preparePutMapping(indexname)
                .setType(typename).setSource(mapping).get();
    }
    //处理mapping内容(index)
    public static XContentBuilder getXcontent(String typename, List<Map<String,String>> fields){
        if (fields == null){
            return null;
        }
        try{
            XContentBuilder mapping = XContentFactory.jsonBuilder()
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
    //处理document内容(doc)
    public static String getJson(String typename, Map<String,String> fields){
        String json = "{";
        int i = 0;
        for (Map.Entry entry:fields.entrySet()) {
            if (i<fields.entrySet().size()-1){
                json+="\"" + entry.getKey()  +"\":\"" + entry.getValue() +"\",";
            }else{
                json+="\"" + entry.getKey()  +"\":\"" + entry.getValue() +"\"";
            }
        }
        json+= "}";
        return json;

    }
    //创建新文档
    public static String indexDoc(String indexname,String typename,Map<String,Object> field){
        IndexResponse response = client.prepareIndex(indexname,typename)
                .setSource(field).get();
        if (response.getId()!=null){
            return response.getId();
        }else{
            return null;
        }
    }
}
