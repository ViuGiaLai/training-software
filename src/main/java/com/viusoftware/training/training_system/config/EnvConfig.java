// package com.viusoftware.training.training_system.config;

// import io.github.cdimascio.dotenv.Dotenv;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class EnvConfig {
//     @Bean
//     public Dotenv dotenv() {
//         return Dotenv.configure()
//                 .directory("d:\\Hoc_spring_boot\\student_management\\training-system") // Đường dẫn đến thư mục chứa file .env
//                 .load();
//     }
// }



package com.viusoftware.training.training_system.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure()
                .ignoreIfMissing() 
                .load();
    }
}
