package uz.fido_biznes.rest.service;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zaxxer.hikari.HikariDataSource;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.sql.DataSource;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfiguration {
    @Bean
    @ConfigurationProperties("uz.rest.datasource.hikari")
    public DataSource dataSourceRest(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "jdbcTemplateRest")
    public JdbcTemplate jdbcTemplateRest(){
        return new JdbcTemplate(dataSourceRest());
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer(){
        return builder -> {
            builder.simpleDateFormat("dd.MM.yyyy");
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        };
    };

    @SneakyThrows
    @Bean
    WebClient webClient(){
        SslContext sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext)).
                option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000).
                responseTimeout(Duration.ofMillis(10000)).
                doOnConnected(conn->{
                    conn.addHandlerLast(new ReadTimeoutHandler(10, TimeUnit.SECONDS));
                    conn.addHandlerLast(new WriteTimeoutHandler(10, TimeUnit.SECONDS));
                });

        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).build();
    }
}

