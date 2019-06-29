package ir.markazandroid.UniEngine;

import ir.markazandroid.UniEngine.media.layout.Layout;
import ir.markazandroid.UniEngine.media.layout.Orientation;
import ir.markazandroid.UniEngine.media.layout.Side;
import ir.markazandroid.UniEngine.persistance.entity.DeviceEntity;
import ir.markazandroid.UniEngine.persistance.entity.LayoutEntity;
import ir.markazandroid.UniEngine.persistance.interfaces.DeviceDAO;
import ir.markazandroid.UniEngine.service.interfaces.CampaignService;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UniEngineApplicationTests {

    @Autowired
    private CampaignService campaignService;

    @Test
    public void saveLayout() {
        Layout layout = new Layout();
        Side midVert = layout.newSide(50, Orientation.vertical);
        Side midHori = layout.newSide(50, Orientation.horizontal);

        layout.newRegion(layout.getLeftParentSide()
                , layout.getTopParentSide()
                , midVert
                , midHori);

        layout.newRegion(midVert
                , layout.getTopParentSide()
                , layout.getRightParentSide()
                , midHori);

        layout.newRegion(layout.getLeftParentSide()
                , midHori
                , midVert
                , layout.getBottomParentSide());

        layout.newRegion(midVert
                , midHori
                , layout.getRightParentSide()
                , layout.getBottomParentSide());

        LayoutEntity layoutEntity = new LayoutEntity();
        layoutEntity.setLayoutData(layout.toLayoutData());

        campaignService.saveLayout(layoutEntity);

        loadLayout();
    }

    /* vertical_0_horizontal_50_vertical_50_horizontal_100
             vertical_50_horizontal_0_vertical_100_horizontal_50
     vertical_0_horizontal_0_vertical_50_horizontal_50
             vertical_50_horizontal_50_vertical_100_horizontal_100*/
    @Test
    public void loadLayout() {
        LayoutEntity layoutEntity = campaignService.getLayoutById(4);
        Layout layout = new Layout(layoutEntity.getLayoutData());

        layout.getRegions().forEach(region -> System.out.println(region.getId()));

    }

    @Autowired
    private DeviceDAO deviceDAO;

    @Transactional
    @Test
    public void testDevice() {
        DeviceEntity deviceEntity = new DeviceEntity();
        //deviceEntity.setUuid(UUID.randomUUID().toString());

        //deviceDAO.saveDevice(deviceEntity);

        deviceEntity = deviceDAO.getDeviceById(298, "uuid", "name");

        deviceEntity = null;

    }


    public static void main(String[] args) {
        JSONObject object = new JSONObject();
        object.put("ali", "ali\nfew\r\nfesfd");
        object.put("fdsf", 1);
        System.out.println(object);
        System.out.println("ali\nfew\r\nfesfd");
    }

}
