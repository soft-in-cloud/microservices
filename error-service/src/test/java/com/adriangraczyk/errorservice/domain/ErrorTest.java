package com.adriangraczyk.errorservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.adriangraczyk.errorservice.web.rest.TestUtil;

public class ErrorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Error.class);
        Error error1 = new Error();
        error1.setId("id1");
        Error error2 = new Error();
        error2.setId(error1.getId());
        assertThat(error1).isEqualTo(error2);
        error2.setId("id2");
        assertThat(error1).isNotEqualTo(error2);
        error1.setId(null);
        assertThat(error1).isNotEqualTo(error2);
    }
}
