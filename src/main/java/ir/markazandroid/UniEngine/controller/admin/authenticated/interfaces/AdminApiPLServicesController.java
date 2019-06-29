package ir.markazandroid.UniEngine.controller.admin.authenticated.interfaces;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Ali on 5/1/2019.
 */
@RequestMapping(value = "/admin/PLServices",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface AdminApiPLServicesController {
    JSONObject registerDevice(@RequestParam String name);
}
