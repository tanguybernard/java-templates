package org.example;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MainTest {

    @Test
    void test1() {
        int a = 1;
        int b = 2;
        assertThat(a+b).isEqualTo(3);
    }
}
