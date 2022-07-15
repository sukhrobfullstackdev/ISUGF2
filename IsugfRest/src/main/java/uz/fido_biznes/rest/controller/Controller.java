package uz.fido_biznes.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.fido_biznes.rest.service.listener.ListenerService;
import uz.fido_biznes.rest.dto.RestfullInDo;
import uz.fido_biznes.rest.dto.RestfullOutDo;
import uz.fido_biznes.rest.exceptions.ServiceException;
import uz.fido_biznes.rest.service.publisher.PublisherService;

import java.sql.SQLException;

import static org.springframework.http.ResponseEntity.ok;

@RestController("/restapi")
@Slf4j
public class Controller {

    final ListenerService listenerService;
    final PublisherService publisherService;

    @Autowired
    public Controller(ListenerService messageListener, PublisherService publisherService) {
        this.listenerService = messageListener;
        this.publisherService = publisherService;
    }

    @PostMapping(path = "/restapiPost", consumes = "application/json")
    public ResponseEntity<String> recieveMessage(@RequestBody RestfullInDo param) throws SQLException, JsonProcessingException {
        var res = ok(listenerService.saveRecievedMessage(param));
        return res;
    }

    @PostMapping(path = "/transporter", consumes = "application/json")
    public ResponseEntity<String> transportMessage(@RequestBody RestfullOutDo param) throws JsonProcessingException {
        var res = ok(publisherService.publishOneMessage(param));
        return res;
    }

    @GetMapping(path = "/restapiGet/{requesttype}", produces = "application/json")
    @SneakyThrows
    public ResponseEntity<String> recieveGetMessage(@PathVariable String requesttype,
                                               @RequestParam(name="par", required = false, defaultValue = "0") Integer p,
                                               @RequestParam(name = "parV", required = false, defaultValue = "") String pV) throws SQLException, ServiceException {
        return ok(listenerService.handleRequest(requesttype, p, pV));
        //return ok("{\"code\":\"test\"}");
    }
}
