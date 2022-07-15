package uz.fido_biznes.rest.dto;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.LinkedHashMap;

/**
 * 
 * @author Kabulov Khusayn
 * Create at 2022-02-22 11:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestfullInDo {
    /**
     * 
     */
    @JsonProperty("ID")
    @JsonAlias({"id"})
    private Integer id;

    /**
     * 
     */
    @JsonProperty("REQUEST_ID")
    @JsonAlias({"requestId", "requestid"})
    private Integer requestId;

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
    @JsonAlias({"DATA", "data"})
    private LinkedHashMap data;

    /**
     * 
     */
    @JsonProperty("STATE")
    @JsonAlias({"state", "STATE"})
    private Integer state;

    /**
     * 
     */
    private String errmsg;

    /**
     * 
     */
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime dateexec;

    /**
     * 
     */
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateEnter;
}