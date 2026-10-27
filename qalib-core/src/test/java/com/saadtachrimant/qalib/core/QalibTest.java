package com.saadtachrimant.qalib.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QalibTest {

    @Test
    void exposesDslVersion() {
        assertThat(Qalib.DSL_VERSION).isEqualTo("0.1");
    }
}
