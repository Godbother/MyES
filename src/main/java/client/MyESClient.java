package client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.network.InetAddresses;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node.*;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.util.List;
import java.util.Map;

public class MyESClient {
    private static TransportClient client;
    static {
        Settings settings = Settings.builder().put("cluster.name","es307")
                .put("client.transport.sniff",true).build();
        client = new PreBuiltTransportClient(settings)
                .addTransportAddress( new InetSocketTransportAddress(InetAddresses.forString("192.168.2.11"),10800));
    }
    public static TransportClient getClient(){
        return client;
    }

    public static TransportClient getClient(List<Map<String,Object>> ipandport){
        for (Map<String,Object> ip:ipandport) {
            client.addTransportAddress(new InetSocketTransportAddress(InetAddresses.forString((String) ip.get("ip")),(Integer) ip.get("port")));
        }
        return client;
    }
}
