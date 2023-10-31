package com.bulovackiy.switter

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class SwitterApplication {

    static void main(String[] args) {
        SpringApplication.run(SwitterApplication, args)
    }

}
