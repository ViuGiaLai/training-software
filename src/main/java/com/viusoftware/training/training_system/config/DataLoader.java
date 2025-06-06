// package com.viusoftware.training.training_system.config;

// import com.viusoftware.training.training_system.repository.UsersAdminRepository;
// import com.viusoftware.training.training_system.service.UserService;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration
// public class DataLoader {

//     private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     @Bean
//     public CommandLineRunner initDatabase(
//             UsersAdminRepository usersAdminRepository,
//             UserService userService) {

//         return args -> {
//             logger.info("Loading initial data...");

//             if (usersAdminRepository.findByUsername("viu106018") == null) {
//                 logger.info("Creating admin user viu106018...");
//                 userService.createUser(
//                     "viu106018",
//                     passwordEncoder.encode("vui106018"),
//                     "ADMIN",
//                     "viu106018@donga.edu.vn",
//                     "0367604684",
//                     null // Added null for imageUrl
//                 );
//                 logger.info("Admin user viu106018 created.");
//             } else {
//                 logger.info("Admin user viu106018 already exists.");
//             }

//             if (usersAdminRepository.findByUsername("viu852005") == null) {
//                 logger.info("Creating admin user viu852005...");
//                 userService.createUser(
//                     "viu852005",
//                     passwordEncoder.encode("rmahviu@852005"),
//                     "ADMIN",
//                     "viuadmin@donga.edu.vn",
//                     "0367604685",
//                     null // Added null for imageUrl
//                 );
//                 logger.info("Admin user viu852005 created.");
//             } else {
//                 logger.info("Admin user viu852005 already exists.");
//             }

//             if (usersAdminRepository.findByUsername("temp_admin") == null) {
//                 logger.info("Creating temporary admin user temp_admin...");
//                 userService.createUser(
//                     "temp_admin",
//                     passwordEncoder.encode("password"),
//                     "ADMIN",
//                     "temp.admin@example.com",
//                     null, // phone là tùy chọn
//                     null // Added null for imageUrl
//                 );
//                 logger.info("Temporary admin user temp_admin created.");
//             } else {
//                 logger.info("Temporary admin user temp_admin already exists.");
//             }

//             logger.info("Initial data loading complete.");
//         };
//     }
// }
