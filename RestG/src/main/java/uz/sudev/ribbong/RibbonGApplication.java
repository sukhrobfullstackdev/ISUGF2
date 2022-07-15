package uz.sudev.ribbong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import uz.sudev.ribbong.configuration.RibbonConfiguration;

@SpringBootApplication
@RibbonClient(name = "ISUGFREST", configuration = RibbonConfiguration.class)
public class RibbonGApplication {

	public static void main(String[] args) {
		SpringApplication.run(RibbonGApplication.class, args);
	}
	@Bean
	@LoadBalanced
	public RestTemplate template() {
		return new RestTemplate();
	}
}
