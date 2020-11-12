package functional.security.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


// Webclient example for stand alone application written for password check
public class DefaultPasswordVerificationService implements PasswordVerificationService{
   final WebClient webClient;


    public DefaultPasswordVerificationService(WebClient.Builder webClientBuilder) {
        this.webClient = WebClient
                            .builder()
                            .baseUrl("http://localhost:8081")
                            .build();
    }

    @Override
    public Mono<Void> check(String raw, String encoded) {
        return webClient
                .post()
                .uri("/check")
                .body(BodyInserters.fromPublisher(
                        Mono.just(new PasswordDTO(raw, encoded)),
                        PasswordDTO.class
                ))
                .exchange()
                .flatMap(clientResponse -> {
                    if(clientResponse.statusCode().is2xxSuccessful()){
                        return Mono.empty();
                    } else if(clientResponse.statusCode() == HttpStatus.EXPECTATION_FAILED){
                        return Mono.error(new RuntimeException("Bad Credentials"));
                    }
                    return Mono.error(new IllegalStateException());
                });
    }
}
