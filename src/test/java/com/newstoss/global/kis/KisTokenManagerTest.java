package com.newstoss.global.kis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KisTokenManagerTest {

    @Autowired
    KisTokenManager manager;

    @Test
    public void test() {
        manager.refresh();
    }

}