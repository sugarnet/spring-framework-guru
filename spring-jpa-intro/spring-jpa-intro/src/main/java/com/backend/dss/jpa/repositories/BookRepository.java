package com.backend.dss.jpa.repositories;

import com.backend.dss.jpa.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
