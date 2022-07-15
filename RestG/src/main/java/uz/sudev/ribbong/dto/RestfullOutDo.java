package uz.sudev.ribbong.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;

/**
 * 
 * @author Kabulov Khusayn
 * Create at 2022-02-24 00:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestfullOutDo {

    /**
     * 
     */
    @JsonProperty("ID")
    @JsonAlias({"ID", "id"})
    private BigInteger id;

    /**
     * 
     */
    @JsonProperty("RESPONSE_ID")
    @JsonAlias({"RESPONSE_ID", "responseId", "responseid"})
    private Integer responseId;

    /**
     * 
     */

    @JsonProperty("METHOD_NAME")
    @JsonAlias({"methodname", "method", "methodName"})
    private String method;

    /**
     * 
     */
    @JsonProperty("DATA")
    @JsonAlias({"DATA", "data", "PAYLOAD", "payload"})
    private LinkedHashMap data;

    /**
     *
     */
    @JsonProperty("URLID")
    @JsonAlias({"urlId", "urlid"})
    private Integer urlid;

    /**
     *
     */
    @JsonProperty("URL")
    @JsonAlias({"URL", "url"})
    private String url;

    /**
     * 
     */
    @JsonProperty("STATE")
    @JsonAlias({"state", "STATE"})
    private Integer state;

    /**
     * 
     */
    @JsonProperty("REQUEST_ID")
    @JsonAlias({"requestId", "requestid"})
    private Integer requestId;

    /**
     * 
     */
    private OffsetDateTime dateexec;

    /**
     * 
     */
    private OffsetDateTime dateEnter;
}