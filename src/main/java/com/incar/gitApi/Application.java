package com.incar.gitApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


/**
 * Created by ct on 2016/2/19 0019.
 */

@SpringBootApplication()
@EnableConfigurationProperties(GithubClientConfig.class)
public class Application  {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
