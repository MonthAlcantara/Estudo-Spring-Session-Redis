package io.github.monthalcantara.estudosession.config;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import redis.clients.jedis.Jedis;


@EnableRedisHttpSession
public class SessionConfig {

    @Bean
    public Jedis jedisConnectionFactory() {
        Jedis factory = new Jedis("localhost",6379);
        return factory;
    }
}
