package crud;

import client.MyESClient;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.util.Map;

public class Mysearch {
    private static TransportClient client = MyESClient.getClient();
    public static String addindex(String index, String type, Map<String,Object> esdata){
        try {
            IndexResponse response = client.prepareIndex()
                    .setIndex(index).setType(type)
                    .setSource(esdata).execute().actionGet();
            return response.getId();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            client.close();
        }
    }
}
