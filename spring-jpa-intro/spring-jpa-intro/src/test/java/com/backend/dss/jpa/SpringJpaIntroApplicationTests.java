package com.backend.dss.jpa;

import com.backend.dss.jpa.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class SpringJpaIntroApplicationTests {

    @Autowired
    BookRepository bookRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void bookRepositoryTest() {
        long count = bookRepository.count();
        assertThat(count).isPositive();
    }

}
