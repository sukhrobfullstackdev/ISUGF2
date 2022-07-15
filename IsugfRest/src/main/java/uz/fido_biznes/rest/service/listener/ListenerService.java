package uz.fido_biznes.rest.service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import uz.fido_biznes.rest.dto.RestfullInDo;
import uz.fido_biznes.rest.exceptions.ServiceException;

import java.sql.SQLException;

public interface ListenerService {
    String saveRecievedMessage(RestfullInDo param) throws SQLException, JsonProcessingException;

    String handleRequest(String requestType, Integer integerParam, String varcharParam) throws SQLException, ServiceException, JsonProcessingException, JSONException;
}
