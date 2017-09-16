package com.github.vitrocket.mydemo.uploader.server.controller;

import junit.framework.TestCase;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vit Rocket on 16.09.2017.
 * @version 1.0
 * @since on 16.09.2017
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void exampleTest() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("/hello/", String.class);
        assertThat(response.getBody()).contains("Hello REST!");
    }

}