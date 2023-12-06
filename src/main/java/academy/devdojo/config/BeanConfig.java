package academy.devdojo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.outside.controller.Connection;

@Configuration
public class BeanConfig {

         @Bean(name = "mysql")
         //@Primary // if you want to use this bean as default
         public Connection connectionMySQL() {
             return new Connection("localhostMYSQL", "8080", "root");
         }

        @Bean(name = "mongoDB")
        public Connection connectionMongoDB() {
            return new Connection("localhostMONGO", "8080", "root");
        }
}
