package uz.fido_biznes.rest.service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import uz.fido_biznes.rest.exceptions.ServiceException;
import uz.fido_biznes.rest.repository.RepositoryMine;
import uz.fido_biznes.rest.dto.RestfullInDo;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ListenerServiceImpl implements ListenerService {
    final RepositoryMine repositoryMine;
    @Autowired
    ObjectMapper objectMapper;

    public ListenerServiceImpl(RepositoryMine repositoryMine) {
        this.repositoryMine = repositoryMine;
    }

    @Override
    public String saveRecievedMessage(RestfullInDo param) throws SQLException, JsonProcessingException {
        //throw new SQLException();

        var message = repositoryMine.saveRecievedMessage(param);
        var result = new HashMap<String,Object>();
        if(message.get("OMSG")==null){
            result.put("code",0);
            result.put("value","OK");
        }else {
            result.put("code",400);
            result.put("value", result.get("OMSG"));
        }

        String jsonResult = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        return jsonResult;
    }

    @Override
    public String handleRequest(String requestType, Integer integerParam, String varcharParam) throws SQLException, ServiceException, JsonProcessingException, JSONException {
        Map<?,?> temp = repositoryMine.handleRequest(requestType, integerParam, varcharParam);
        ConcurrentHashMap<String, Object> res = new ConcurrentHashMap<String, Object>();
        System.out.println(res);
        String retstr = ((Clob)temp.get("return")).getSubString(1, (int) ((Clob)temp.get("return")).length());
        res.put("code", temp.get("O_STATUS"));
        res.put("retstr", retstr);
        System.out.println(retstr);
        System.out.println(res);
        String json = new Gson().toJson(res);
        System.out.println(json);
        //return json;
        return objectMapper.writeValueAsString(res);
    }

}
