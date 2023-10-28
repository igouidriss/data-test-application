package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CastingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Casting.class);
        Casting casting1 = new Casting();
        casting1.setId(1L);
        Casting casting2 = new Casting();
        casting2.setId(casting1.getId());
        assertThat(casting1).isEqualTo(casting2);
        casting2.setId(2L);
        assertThat(casting1).isNotEqualTo(casting2);
        casting1.setId(null);
        assertThat(casting1).isNotEqualTo(casting2);
    }
}
