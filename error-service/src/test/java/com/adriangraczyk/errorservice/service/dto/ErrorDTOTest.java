package com.adriangraczyk.errorservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.adriangraczyk.errorservice.web.rest.TestUtil;

public class ErrorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ErrorDTO.class);
        ErrorDTO errorDTO1 = new ErrorDTO();
        errorDTO1.setId("id1");
        ErrorDTO errorDTO2 = new ErrorDTO();
        assertThat(errorDTO1).isNotEqualTo(errorDTO2);
        errorDTO2.setId(errorDTO1.getId());
        assertThat(errorDTO1).isEqualTo(errorDTO2);
        errorDTO2.setId("id2");
        assertThat(errorDTO1).isNotEqualTo(errorDTO2);
        errorDTO1.setId(null);
        assertThat(errorDTO1).isNotEqualTo(errorDTO2);
    }
}
