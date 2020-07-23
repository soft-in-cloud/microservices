package com.adriangraczyk.errorservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ErrorMapperTest {

    private ErrorMapper errorMapper;

    @BeforeEach
    public void setUp() {
        errorMapper = new ErrorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(errorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(errorMapper.fromId(null)).isNull();
    }
}
