package com.github.vitrocket.mydemo.uploader.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Vit Rocket on 16.09.2017.
 * @version 1.0
 * @since on 16.09.2017
 */
@SpringBootApplication(scanBasePackages = {"com.github.vitrocket.mydemo.uploader"})
public class AppRestApi {

    public static void main(String[] args) {
        SpringApplication.run(AppRestApi.class, args);
    }

}
