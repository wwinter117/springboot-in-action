package cn.wwinter.springboot3example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBoot3ExampleApplication {

	public static void main(String[] args) {
		System.out.println("hello");
		SpringApplication.run(SpringBoot3ExampleApplication.class, args);
	}

}
