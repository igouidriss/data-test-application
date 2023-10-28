package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CinemaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cinema.class);
        Cinema cinema1 = new Cinema();
        cinema1.setId(1L);
        Cinema cinema2 = new Cinema();
        cinema2.setId(cinema1.getId());
        assertThat(cinema1).isEqualTo(cinema2);
        cinema2.setId(2L);
        assertThat(cinema1).isNotEqualTo(cinema2);
        cinema1.setId(null);
        assertThat(cinema1).isNotEqualTo(cinema2);
    }
}
