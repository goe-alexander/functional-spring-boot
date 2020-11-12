package functional.security.client;

import reactor.core.publisher.Mono;

public interface PasswordVerificationService {
    public Mono<Void> check(String raw, String encoed);
}
