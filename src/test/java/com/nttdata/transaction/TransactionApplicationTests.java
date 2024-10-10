package com.nttdata.transaction;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionManager;

@SpringBootTest
class TransactionApplicationTests {

    @Mock
    private TransactionManager transactionManager;

   // @Test
    void contextLoads() {
    }

}
