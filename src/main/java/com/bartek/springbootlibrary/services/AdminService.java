package com.bartek.springbootlibrary.services;

import com.bartek.springbootlibrary.dao.BookRepository;
import com.bartek.springbootlibrary.entity.Book;
import com.bartek.springbootlibrary.requestsModels.AddBookRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AdminService {
    private BookRepository bookRepository;

    public void postBook(AddBookRequest addBookRequest) {
        Book book = new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setDescription(addBookRequest.getDescription());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setImage(addBookRequest.getImg());
        bookRepository.save(book);
    }
}
