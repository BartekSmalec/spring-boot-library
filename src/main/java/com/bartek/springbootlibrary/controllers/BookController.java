package com.bartek.springbootlibrary.controllers;

import com.bartek.springbootlibrary.dao.CheckoutRepository;
import com.bartek.springbootlibrary.entity.Book;
import com.bartek.springbootlibrary.entity.Checkout;
import com.bartek.springbootlibrary.responseModels.ShelfCurrentLoansResponse;
import com.bartek.springbootlibrary.services.BookService;
import com.bartek.springbootlibrary.utils.ExtractJWT;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/books")
@AllArgsConstructor
@Slf4j
public class BookController {
    private final CheckoutRepository checkoutRepository;
    public static final String SUB = "\"sub\"";
    private BookService bookService;

    @GetMapping("/secure/currentloans")
    public List<ShelfCurrentLoansResponse> currentLoans(@RequestHeader(value = "Authorization") String token) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, SUB);
        return bookService.currentLoans(userEmail);
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestHeader(value = "Authorization") String auth, @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(auth, SUB);
        log.info("User email: {}", userEmail);
        return bookService.checkoutBook(userEmail, bookId);
    }

    @GetMapping("/secure/ischeckout/byuser")
    public boolean checkoutBookByUser(@RequestHeader(value = "Authorization") String auth, @RequestParam Long bookId) {
        String userEmail = ExtractJWT.payloadJWTExtraction(auth, SUB);
        return bookService.checkoutBookByUser(userEmail, bookId);
    }

    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String auth) {
        String userEmail = ExtractJWT.payloadJWTExtraction(auth, SUB);
        return bookService.currentLoansCount(userEmail);
    }

    @PutMapping("/secure/return")
    public void returnBook(@RequestHeader(value = "Authorization") String auth, @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(auth, SUB);
        bookService.returnBook(userEmail, bookId);
    }

    @PutMapping("/secure/renew/loan")
    public void renewLoan(@RequestHeader(value = "Authorization") String auth, @RequestParam Long bookId) throws Exception{
        String userEmail = ExtractJWT.payloadJWTExtraction(auth, SUB);
        bookService.renewLoan(userEmail, bookId);
        log.info(checkoutRepository.findByUserEmailAndBookId(userEmail, bookId).toString());
    }
}
