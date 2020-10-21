package com.example.rmqapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.PollableBean;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Log4j2
@SpringBootApplication
public class RmqAppApplication {

	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication.run(RmqAppApplication.class, args);
	}

	@SneakyThrows
	private String encode(ObjectMapper om, Object o) {
		return om.writeValueAsString(o);
	}

	//
	//--spring.cloud.stream.bindings.log-in-0.destination=my-topic
	@Bean
	Supplier<String> person(ObjectMapper objectMapper) {
		return () -> {
			log.info("new person, comin' right up!");
			return encode(objectMapper, Map.of("id", 1, "name", "Jane"));
		};
	}

	@Bean
	Consumer<String> log() {
		return person -> log.info("new Person update received: " + person + " @ " + Instant.now());
	}
}
/*

@Data
@AllArgsConstructor
@NoArgsConstructor
class Person {
	private Integer id;
	private String name;
}
*/

