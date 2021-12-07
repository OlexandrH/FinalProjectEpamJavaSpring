package ua.oblikchasu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo {
    public static void main(String[] args) {
        SpringApplication.run(Demo.class,args);
    }
//    CommandLineRunner commandLineRunner(UserRepository userRepository) {
//        return args ->{
//            userRepository.save(new User("test2", "test2", "Test2", UserRole.USER));
//
//        };
//    }
}
