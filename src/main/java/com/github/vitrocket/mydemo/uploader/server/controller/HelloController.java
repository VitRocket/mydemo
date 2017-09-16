package com.github.vitrocket.mydemo.uploader.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vit Rocket on 16.09.2017.
 * @version 1.0
 * @since on 16.09.2017
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping(value = "/")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello REST!", HttpStatus.OK);
    }

}
