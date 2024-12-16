package repick.chatbotapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@EnableAsync
@SpringBootApplication
public class ChatbotapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatbotapiApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {return new RestTemplate();}

}
