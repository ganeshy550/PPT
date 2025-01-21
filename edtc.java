import io.etcd.jetcd.Client;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.options.PutOption;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class EtcdExample {
    public static void main(String[] args) throws Exception {
        // Connect to the ETCD server
        String etcdEndpoint = "http://localhost:2379";
        Client client = Client.builder().endpoints(etcdEndpoint).build();

        // Key and value to store
        String key = "exampleKey";
        String value = "exampleValue";

        // Put key-value pair
        CompletableFuture<Void> putFuture = client.getKVClient().put(
                key.getBytes(StandardCharsets.UTF_8),
                value.getBytes(StandardCharsets.UTF_8)
        );
        putFuture.get(); // Wait for the operation to complete
        System.out.println("Key-value pair stored.");

        // Get value for the key
        CompletableFuture<GetResponse> getFuture = client.getKVClient().get(
                key.getBytes(StandardCharsets.UTF_8)
        );
        GetResponse response = getFuture.get();

        for (KeyValue kv : response.getKvs()) {
            System.out.println("Retrieved value: " + kv.getValue().toString(StandardCharsets.UTF_8));
        }

        // Close the client
        client.close();
    }
}
