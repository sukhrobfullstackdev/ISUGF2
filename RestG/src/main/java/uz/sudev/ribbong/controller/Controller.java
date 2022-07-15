package uz.sudev.ribbong.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import uz.sudev.ribbong.dto.RestfullInDo;
import uz.sudev.ribbong.dto.RestfullOutDo;

@RestController
public class Controller {
    @Autowired
    private RestTemplate template;

    @PostMapping(path = "/restapi/restapi", consumes = "application/json")
    public Object recieveMessage(@RequestBody RestfullInDo param) {
        return template.postForEntity("http://IsugfRest/restapi/restapi", param, Object.class);
    }
    @PostMapping(path = "/restapi/transporter", consumes = "application/json")
    public Object transportMessage(@RequestBody RestfullOutDo param) throws JsonProcessingException {
        return template.postForEntity("http://IsugfRest/restapi/transporter", param, Object.class);
    }
    @GetMapping(path = "/restapi/restapi/{requesttype}", produces = "application/json")
    public String recieveGetMessage(@PathVariable String requesttype,
                                               @RequestParam(name="par", required = false, defaultValue = "0") Integer p,
                                               @RequestParam(name = "parV", required = false, defaultValue = "") String pV) {
        String v =  template.getForObject("http://IsugfRest/restapi/" + requesttype + "?par=" + p + "&parV=" + pV, String.class);
        System.out.println(v);
        return v;
    }
    @GetMapping
    public String hello() {
        return "It's SukhrobDev!";
    }
}
