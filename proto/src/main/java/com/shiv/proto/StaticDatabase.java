package com.shiv.proto;

import com.dev.shiv.Book;

import java.util.ArrayList;
import java.util.List;

public class StaticDatabase {

    public static List<Book> getBooks(){
        return new ArrayList<Book>(){
            {
                add(Book.newBuilder().setBookId(1).setName("C").setTitle("H1").setPages(200).setPrice(25).build());
                add(Book.newBuilder().setBookId(2).setName("C++").setTitle("H2").setPages(220).setPrice(253).build());
                add(Book.newBuilder().setBookId(3).setName("Java").setTitle("H3").setPages(400).setPrice(205).build());
                add(Book.newBuilder().setBookId(4).setName("Python").setTitle("H4").setPages(2500).setPrice(350).build());
                add(Book.newBuilder().setBookId(5).setName("Python").setTitle("H5").setPages(2500).setPrice(258).build());
                add(Book.newBuilder().setBookId(6).setName("Python").setTitle("H6").setPages(2500).setPrice(259).build());
                add(Book.newBuilder().setBookId(7).setName("Python").setTitle("H7").setPages(2500).setPrice(252).build());
            }
        };
    }
}
