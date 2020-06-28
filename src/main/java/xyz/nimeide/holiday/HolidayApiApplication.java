package xyz.nimeide.holiday;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;


@SpringBootApplication
@RestController
public class HolidayApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HolidayApiApplication.class, args);
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping
    public Mono<String> get(){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set("a","b",100, TimeUnit.SECONDS);
        System.out.println("a");
        System.out.println(operations.get("a"));
        return Mono.create(monoSink ->monoSink.success("success"));
    }
}
