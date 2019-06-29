package ir.markazandroid.UniEngine.controller.deviceApi;

import ir.markazandroid.UniEngine.constants.DeviceDataParams;
import ir.markazandroid.UniEngine.controller.deviceApi.interfaces.DeviceApiDeviceController;
import ir.markazandroid.UniEngine.exception.DeviceDataWrongTimestampException;
import ir.markazandroid.UniEngine.object.ResponseObject;
import ir.markazandroid.UniEngine.persistance.cassandra.entity.DeviceData;
import ir.markazandroid.UniEngine.persistance.entity.DeviceEntity;
import ir.markazandroid.UniEngine.service.interfaces.DeviceService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by Ali on 6/10/2019.
 */
@RestController
public class DeviceApiDeviceControllerImp implements DeviceApiDeviceController {

    private final DeviceService deviceService;
    private final DeviceDataParams dataParams;

    public DeviceApiDeviceControllerImp(DeviceService deviceService, DeviceDataParams dataParams) {
        this.deviceService = deviceService;
        this.dataParams = dataParams;
    }

    @Override
    public DeviceEntity getMe(DeviceEntity deviceEntity) {
        return deviceEntity;
    }

    @Override
    public ResponseEntity<ResponseObject> onData(DeviceEntity deviceEntity, JSONObject jsonObject) {
        JSONArray dataArray = jsonObject.getJSONArray("datas");
        dataArray.forEach(o -> {
            JSONObject jo = (JSONObject) o;
            final long time = jo.getLong("t");
            jo.remove("t");
            if (time > System.currentTimeMillis() || time < System.currentTimeMillis() - (3600 * 24 * 30L))
                throw new DeviceDataWrongTimestampException();

            DeviceData deviceData = new DeviceData();
            deviceData.setDeviceId(deviceEntity.getDeviceId());
            deviceData.setTime(new Timestamp(time));
            deviceData.setDate(DeviceData.getDate(deviceData.getTime()));
            Map<String, Object> values = jo.toMap();
            values.forEach((s, o1) -> dataParams.setParamIntoObject(deviceData, s, o1));
            deviceService.saveDeviceData(deviceData);
        });

        return new ResponseObject.Builder().status(200).timestamp(System.currentTimeMillis()).build();
    }
}
