package uz.fido_biznes.rest.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import uz.fido_biznes.rest.dto.RestfullInDo;
import uz.fido_biznes.rest.dto.RestfullOutDo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RepositoryJdbcImpl implements RepositoryMine {
    final JdbcTemplate jdbcTemplate;
    @Autowired
    ObjectMapper objectMapper;

    public RepositoryJdbcImpl(@Qualifier(value = "jdbcTemplateRest") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Map<String, Object> saveRecievedMessage(RestfullInDo message) throws JsonProcessingException {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);
        call.withCatalogName("REST_MESSAGE").withProcedureName("REGRECIEVEMESSAGE");
        String dataAsJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(message.getData());
        SqlParameterSource source = new MapSqlParameterSource().
                addValue("irequestId", message.getId()).
                addValue("imethod",message.getMethod()).
                addValue("idata", dataAsJsonString);

        return call.execute(source);
    }

    @Override
    public List<RestfullOutDo> findAllMessagesForSend(){
        return jdbcTemplate.queryForList("""
                select r.id, r.response_id resid, r.method, r.data, ru.url from restfull_out r, restfull_urls ru where r.urlid = ru.id and r.state = 0
                """).
                stream().map(res->{
                    var message = new RestfullOutDo();
                    try {
                        var data = objectMapper.readValue((String)res.get("data"), new TypeReference<LinkedHashMap<?,?>>(){});
                        message.setId(((BigDecimal) res.get("id")).toBigInteger());
                        message.setMethod((String) res.get("method"));
                        message.setData(data); // (String) res.get("data")
                        message.setResponseId(((BigDecimal) res.get("resid")).intValue());
                        message.setUrl((String)res.get("url"));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    return message;
                }).toList();
    }

    @Override
    public Map<String, Object>  handleRequest(String requestType, Integer integerParam, String varcharParam) {
        Integer status = 0;
        String result;
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);
        call.withCatalogName("REST_GET_HANDLER").withFunctionName("handle_request");
        SqlParameterSource source = new MapSqlParameterSource().
                addValue("i_requestType", requestType).
                addValue("i_par", integerParam).
                addValue("i_parV", varcharParam);

        Map<String, Object> m = call.execute(source);
        return m;
    }

    @Override
    public Map<String, Object> regOutMessageSended(BigInteger imessId) {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);
        call.withCatalogName("REST_MESSAGE").withProcedureName("regOutMessageSended");
        SqlParameterSource source = new MapSqlParameterSource().
                addValue("imessId", imessId);

        return call.execute(source);
    }
}
