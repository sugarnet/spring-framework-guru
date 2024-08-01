package com.backend.dss.jpa.bootstrap;

import com.backend.dss.jpa.entities.Book;
import com.backend.dss.jpa.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Book book1 = new Book("Effective Java (3rd Edition)", "978-0134685991", "Addison-Wesley Professional");
        Book book2 = new Book("Java: The Complete Reference (11th Edition)", "978-1260440232", "McGraw-Hill Education");
        Book book3 = new Book("Head First Java (2nd Edition)", "978-0596009205", "O'Reilly Media");
        Book book4 = new Book("Java Concurrency in Practice", "978-0321349606", "Addison-Wesley Professional");
        Book book5 = new Book("Core Java Volume I – Fundamentals (11th Edition)", "978-0135166307", "Prentice Hall");

        Book saveBook1 = bookRepository.save(book1);
        Book saveBook2 = bookRepository.save(book2);
        Book saveBook3 = bookRepository.save(book3);
        Book saveBook4 = bookRepository.save(book4);
        Book saveBook5 = bookRepository.save(book5);

        bookRepository.findAll().forEach(book -> {
            System.out.println("Book Id: " + book.getId());
            System.out.println("Book Title: " + book.getTitle());
        });


    }
}
