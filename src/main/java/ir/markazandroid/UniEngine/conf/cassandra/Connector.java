package ir.markazandroid.UniEngine.conf.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ali on 6/16/2019.
 */
@Configuration
public class Connector {

    private final Session session;

    public Connector() {
        Cluster cluster = Cluster.builder()
                .addContactPoint("89.42.210.32")
                .withPort(9042)
                .withoutMetrics()
                .build();

        session = cluster.connect("UniEngine");

        /*CqlSession.builder()
                .addContactPoint(InetSocketAddress.createUnresolved("89.42.210.32",9042))
                .withLocalDatacenter("datacenter1")
                .withKeyspace("UniEngine")
                .build();*/

    }

    public Session getSession() {
        return session;
    }

    public static void main(String[] args) {
        Connector connector = new Connector();
        //connector.createKeySpace();
        connector.createTable();
    }

    private void createKeySpace() {
        String query = "CREATE KEYSPACE IF NOT EXISTS " +
                "UniEngine" +
                " WITH replication = {" +
                "'class':'" + "SimpleStrategy" +
                "','replication_factor':" + 1 +
                "};";
        session.execute(query);
    }

    private void createTable() {
        session.execute("CREATE TABLE device_data (\n" +
                "    device_id bigint,\n" +
                "    date text,\n" +
                "    time timestamp,\n" +
                "    values_string map<text,text>,\n" +
                "    values_float map<text,float>,\n" +
                "    values_int map<text,int>,\n" +
                "    primary key ((device_id,date), time)\n" +
                ") WITH CLUSTERING ORDER BY (time DESC);");
    }
}
