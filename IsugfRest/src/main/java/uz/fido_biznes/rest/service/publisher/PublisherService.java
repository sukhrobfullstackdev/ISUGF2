package uz.fido_biznes.rest.service.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import uz.fido_biznes.rest.dto.RestfullOutDo;
import uz.fido_biznes.rest.repository.RepositoryJdbcImpl;

import java.util.*;

@Service
@Slf4j
public class PublisherService {
    @Autowired
    ObjectMapper objectMapper;

    final RepositoryJdbcImpl repository;
    private final WebClient webClient;

    public PublisherService(RepositoryJdbcImpl repository, WebClient webClient) {
        this.repository = repository;
        this.webClient = webClient;
    }

    @Scheduled(fixedDelay = 10000)
    public void publishTask() throws JsonProcessingException {
        publishMessage();
    }

    public String publishOneMessage(RestfullOutDo outDo) throws JsonProcessingException {
        String jsonResult = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(outDo.getData());
        ResponseEntity<String> response = webClient.post().
                uri(outDo.getUrl()).//http://192.168.254.55/inps/api/RequestTrade/ResponseRequestTrade
                contentType(MediaType.APPLICATION_JSON).
                body(BodyInserters.fromValue(jsonResult)). // outDo.getData().toString()
                retrieve().
                toEntity(String.class).
                //onStatus(HttpStatus::is4xxClientError, error->Mono.error(new RuntimeException(error.toString()))).
                //bodyToMono(String.class).
                doOnError(error->{log.error("Error on publishOneMessage: {}", error.getMessage());}).
                onErrorResume(
                        error->{
                            log.error("ErrorResume on publish: {}", error.toString());
                            Map<String, Object> k = new Hashtable<String, Object>();
                            k.put("omsg", "error");
                            k.put("message", error.toString());
                            return Mono.just(new ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST));
                        }).
                block();


        String res = "";
        if(!Objects.equals(response.getStatusCode(), HttpStatus.BAD_REQUEST))
            res = repository.regOutMessageSended(outDo.getId()).toString();
        else res = response.toString();

        return res;
        /*String resGet = webClient.get().
                uri(outDo.getUrl()).//http://192.168.254.55/inps/api/RequestTrade/ResponseRequestTrade
                accept(MediaType.APPLICATION_JSON).
                //body(BodyInserters.fromValue(outDo.getData())).
                retrieve().subscribe(res1->{System.out.println("qwerty");}).
                //onStatus(HttpStatus::is4xxClientError, error->Mono.error(new RuntimeException(error.toString()))).
                bodyToMono(String.class).
                doOnError(error->{log.error("Error on publish: {}", error.getMessage());}).
                onErrorResume(error->{log.error("ErrorResume on publish: {}", error.toString()); return Mono.just("test ono");}).
                block()
                ;*/

        //System.out.println(resPost);
    }

    public void publishMessage() throws JsonProcessingException {
        List<RestfullOutDo> outDos = repository.findAllMessagesForSend();
        String jsonString = """
                            {
                                "methodName":"Transport", 
                                "url":"http://192.168.44.201:8383/transporter", 
                                "data":
                                {
                                    "methodName":"Rest", 
                                    "url":"http://192.168.44.201:8383/restapi", 
                                    "data":
                                    {
                                        "methodName":"RestSub", 
                                        "ID":1234567,
                                        "requestId":321,
                                        "state":1,
                                        "data":
                                        {
                                            "methodName":"RestSub", 
                                            "ID":1234567,
                                            "requestId":321,
                                            "state":1
                                        }
                                    }
                                }
                            }
                            """;
        Map<String, Object> map = objectMapper.readValue(jsonString, new TypeReference<Map<String,Object>>(){});
        RestfullOutDo outDo = objectMapper.readValue(jsonString, new TypeReference<RestfullOutDo>(){});
        //String rest = publishOneMessage(outDo);
        for (RestfullOutDo elem:outDos) {
            publishOneMessage(elem);
        }
    }
}
