package ir.markazandroid.UniEngine.persistance.cassandra;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import ir.markazandroid.UniEngine.conf.cassandra.Connector;
import ir.markazandroid.UniEngine.persistance.cassandra.entity.DeviceData;
import ir.markazandroid.UniEngine.persistance.cassandra.interfaces.DeviceDataDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by Ali on 6/17/2019.
 */
@Repository
public class DeviceDataDAOImp implements DeviceDataDAO {

    private final Connector connector;
    private final Mapper<DeviceData> mapper;

    public DeviceDataDAOImp(Connector connector) {
        this.connector = connector;
        MappingManager manager = new MappingManager(connector.getSession());
        mapper = manager.mapper(DeviceData.class);
    }

    @Override
    public void saveDeviceData(DeviceData deviceData) {
        mapper.save(deviceData);
    }
}
