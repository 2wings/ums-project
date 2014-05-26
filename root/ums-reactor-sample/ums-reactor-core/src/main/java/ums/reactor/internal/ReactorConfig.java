package ums.reactor.internal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.spring.context.config.EnableReactor;

@Configuration
@EnableReactor("default")
public class ReactorConfig {

    @Bean(name = "rootReactor")
    public Reactor rootReactor(Environment env) {
        return Reactors.reactor().env(env).dispatcher(Environment.RING_BUFFER).get();
    }


}
