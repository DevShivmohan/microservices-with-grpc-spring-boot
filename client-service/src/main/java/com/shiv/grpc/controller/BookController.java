package com.shiv.grpc.controller;

import com.shiv.grpc.service.BookClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book")
@AllArgsConstructor
public class BookController {
    private final BookClientService bookClientService;

    @GetMapping("id/{bookId}")
    public ResponseEntity<?> getBook(@PathVariable("bookId") int bookId){
        return bookClientService.getBook(bookId);
    }


    @GetMapping("name/{bookName}")
    public ResponseEntity<?> getBooksByName(@PathVariable("bookName") String name) throws InterruptedException {
        return bookClientService.getBoooksByName(name);
    }

    @GetMapping
    public ResponseEntity<?> getExpensiveBook() throws InterruptedException {
        return bookClientService.getExpensiveBook();
    }

    @GetMapping("all")
    public ResponseEntity<?> getBooks() throws InterruptedException {
        return bookClientService.getBooks();
    }
}
