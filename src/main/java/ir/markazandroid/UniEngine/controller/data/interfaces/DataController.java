package ir.markazandroid.UniEngine.controller.data.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ali on 4/15/2019.
 */
@RequestMapping("/data")
public interface DataController {

    @GetMapping("/read/**")
    void readFile(@RequestParam(name = "eFileId",required = false) String encodedFileId, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
