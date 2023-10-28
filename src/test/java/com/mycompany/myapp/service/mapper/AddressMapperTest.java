package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class AddressMapperTest {

    private AddressMapper addressMapper;

    @BeforeEach
    public void setUp() {
        addressMapper = new AddressMapperImpl();
    }
}
