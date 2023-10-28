package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CinemaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CinemaDTO.class);
        CinemaDTO cinemaDTO1 = new CinemaDTO();
        cinemaDTO1.setId(1L);
        CinemaDTO cinemaDTO2 = new CinemaDTO();
        assertThat(cinemaDTO1).isNotEqualTo(cinemaDTO2);
        cinemaDTO2.setId(cinemaDTO1.getId());
        assertThat(cinemaDTO1).isEqualTo(cinemaDTO2);
        cinemaDTO2.setId(2L);
        assertThat(cinemaDTO1).isNotEqualTo(cinemaDTO2);
        cinemaDTO1.setId(null);
        assertThat(cinemaDTO1).isNotEqualTo(cinemaDTO2);
    }
}
