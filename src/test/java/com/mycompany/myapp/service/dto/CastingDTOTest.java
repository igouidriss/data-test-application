package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CastingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CastingDTO.class);
        CastingDTO castingDTO1 = new CastingDTO();
        castingDTO1.setId(1L);
        CastingDTO castingDTO2 = new CastingDTO();
        assertThat(castingDTO1).isNotEqualTo(castingDTO2);
        castingDTO2.setId(castingDTO1.getId());
        assertThat(castingDTO1).isEqualTo(castingDTO2);
        castingDTO2.setId(2L);
        assertThat(castingDTO1).isNotEqualTo(castingDTO2);
        castingDTO1.setId(null);
        assertThat(castingDTO1).isNotEqualTo(castingDTO2);
    }
}
