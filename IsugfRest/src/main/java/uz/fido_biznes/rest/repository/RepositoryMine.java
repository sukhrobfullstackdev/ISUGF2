package uz.fido_biznes.rest.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import uz.fido_biznes.rest.dto.RestfullInDo;
import uz.fido_biznes.rest.dto.RestfullOutDo;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;


public interface RepositoryMine {
    Map<String, Object> saveRecievedMessage(RestfullInDo message) throws JsonProcessingException;
    List<RestfullOutDo> findAllMessagesForSend();
    Map<String, Object> handleRequest(String requestType, Integer integerParam, String varcharParam);
    Map<String, Object> regOutMessageSended(BigInteger imessId);
}
