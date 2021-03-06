package fr.guddy.test;

import fr.guddy.test.model.Customer;
import fr.guddy.test.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Application {

    @Autowired
    private CustomerRepository repository;

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(final ApplicationContext context) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            final String[] beanNames = context.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (final String beanName : beanNames) {
                System.out.println(beanName);
            }

            System.out.println("Let's test MongoDB by Spring Boot:");

            repository.deleteAll();

            // save a couple of customers
            repository.save(new Customer("Alice", "Smith"));
            repository.save(new Customer("Bob", "Test"));

            // fetch all customers
            System.out.println("Customers found with findAll():");
            System.out.println("-------------------------------");
            for (final Customer customer : repository.findAll()) {
                System.out.println(customer);
            }
            System.out.println();

            // fetch an individual customer
            System.out.println("Customer found with findByFirstName('Alice'):");
            System.out.println("--------------------------------");
            System.out.println(repository.findByFirstName("Alice"));

            System.out.println("Customers found with findByLastName('Smith'):");
            System.out.println("--------------------------------");
            for (final Customer customer : repository.findByLastName("Smith")) {
                System.out.println(customer);
            }

        };
    }

}
