package uz.sudev.ribbong.configuration;

import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.RequestSpecificRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.RetryRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class RibbonConfiguration {
    @Autowired
    IClientConfig ribbonClient;

    @Value("${ISUGFREST.ribbon.OkToRetryOnAllOperations}")
    private boolean okToRetryOnAllOperations;

    @Value("${ISUGFREST.ribbon.MaxAutoRetries}")
    private Integer maxAutoRetries;

    @Value("${ISUGFREST.ribbon.MaxAutoRetriesNextServer}")
    private Integer maxAutoRetriesNextServer;

    public RibbonConfiguration() {
    }

    @Bean
    public RetryHandler retryHandler() {
        IClientConfig clientConfig = DefaultClientConfigImpl
                .Builder
                .newBuilder()
                .withMaxAutoRetries(this.maxAutoRetries)
                .withMaxAutoRetriesNextServer(this.maxAutoRetriesNextServer)
                .withRetryOnAllOperations(this.okToRetryOnAllOperations)
                .build();

        RetryHandler retryHandler = new DefaultLoadBalancerRetryHandler(this.maxAutoRetries, this.maxAutoRetriesNextServer, this.okToRetryOnAllOperations);
        return new RequestSpecificRetryHandler(true, true, retryHandler, clientConfig );
    }


    @Bean
    public IPing ribbonPing(IClientConfig config) {
        return new PingUrl();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public IRule ribbonRule(IClientConfig config) {
        return new RetryRule();
    }
}
