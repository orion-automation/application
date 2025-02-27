package com.eorion.bo.enhancement.application.adapter.inbound;

import org.springframework.beans.factory.annotation.Value;

import java.io.InputStreamReader;
import java.util.Objects;

public class BaseControllerTest {
    @Value("${spring.profiles.active:default}") // "default" if no profile is set
    private String activeProfile;

    protected InputStreamReader getApplicationDeleteReader() {
        if (activeProfile.equals("testcontainers")) {
            return new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("sql/delete-all.oracle.sql")));
        }
        return new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("sql/delete-all.sql")));
    }
}
