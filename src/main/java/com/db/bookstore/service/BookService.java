package com.db.bookstore.service;

import com.db.bookstore.model.Book;
import com.db.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public void insertBook(Book book) {
        bookRepository.save(book);
    }
}
