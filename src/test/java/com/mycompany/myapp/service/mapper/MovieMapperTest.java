package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class MovieMapperTest {

    private MovieMapper movieMapper;

    @BeforeEach
    public void setUp() {
        movieMapper = new MovieMapperImpl();
    }
}
