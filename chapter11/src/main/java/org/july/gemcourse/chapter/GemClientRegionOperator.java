package org.july.gemcourse.chapter;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;

import java.util.Map;

/**
 * Created by July on 2018/6/7.
 */
public class GemClientRegionOperator {
    private final static String locatorAddress = "localhost";
    private final static int  locatorPort = 10334;
    //main
    public static void main(String[] args) throws Exception {
        ClientCache cache = new ClientCacheFactory()
                .addPoolLocator(locatorAddress, locatorPort)
                .create();
        //create proxy region and sub region.
        ClientRegionFactory cf = cache.createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY);
        Region parentRegion = cf.create("gfs");
        Region<String, String> region  = cf.<String, String> createSubregion(parentRegion, "partdata");
        //create and put into data
        for(int j = 0; j< 10000; j++){
            region.put("key"+j, "Value"+j);
        }
        //list and print data.
        for (Map.Entry<String, String> entry : region.entrySet()) {
            System.out.format("key = %s, value = %s\n", entry.getKey(), entry.getValue());
        }
        cache.close();
    }
}
