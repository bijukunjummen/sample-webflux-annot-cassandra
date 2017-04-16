package cass.web;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoEmptyTest {
    
    @Test
    public void monoEmptyTest() {
        Mono<Integer> empty = Mono.empty();
        Mono<Integer> n =  empty.flatMap(i -> Mono.just(1));
        StepVerifier stepVerifier = StepVerifier.create(n).expectComplete();
        stepVerifier.verify();
    }
}
