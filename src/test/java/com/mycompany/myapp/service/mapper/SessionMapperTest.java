package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class SessionMapperTest {

    private SessionMapper sessionMapper;

    @BeforeEach
    public void setUp() {
        sessionMapper = new SessionMapperImpl();
    }
}
