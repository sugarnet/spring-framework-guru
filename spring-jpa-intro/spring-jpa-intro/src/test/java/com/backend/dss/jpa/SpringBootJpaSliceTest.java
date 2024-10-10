package com.backend.dss.jpa;

import com.backend.dss.jpa.entities.Book;
import com.backend.dss.jpa.repositories.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@ComponentScan("com.backend.dss.jpa.bootstrap")
class SpringBootJpaSliceTest {

    @Autowired
    BookRepository bookRepository;

    @Commit // equals to @Rollback(false)
    @Order(1)
    @Test
    void jpaSliceTest() {

        long countBefore =bookRepository.count();
        Assertions.assertThat(countBefore).isEqualTo(5);

        bookRepository.save(new Book("My Book", "123123123", "Self"));

        long countAfter =bookRepository.count();

        Assertions.assertThat(countBefore).isLessThan(countAfter);
    }

    @Order(2)
    @Test
    void jpaSliceTransactionTest() {

        long countBefore =bookRepository.count();

        Assertions.assertThat(countBefore).isEqualTo(6);
    }
}
