package com.shiv.grpc.service;

import com.dev.shiv.Book;
import com.dev.shiv.BookServiceGrpc;
import com.google.protobuf.Descriptors;
import com.shiv.proto.StaticDatabase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class BookClientService {
    /**
     * It is used for synchronousClient
     */
    @GrpcClient("dev-shiv-service")
    private BookServiceGrpc.BookServiceBlockingStub bookServiceBlockingStub;
    /**
     * It is used for asynchronousClient
     */
    @GrpcClient("dev-shiv-service")
    private BookServiceGrpc.BookServiceStub bookServiceStub;

    /**
     * Sending single request to the grpc service
     * @param bookId
     * @return
     */
    public ResponseEntity<?> getBook(int bookId){
        System.out.println("bookId");
        Book book= bookServiceBlockingStub.getBook(Book.newBuilder().setBookId(bookId).build());
        System.out.println("end bookId");
        return ResponseEntity.status(HttpStatus.OK)
                .body(book.getAllFields());
    }

    /**
     * Sending single request but receive a stream
     * @param bookName
     * @return
     * @throws InterruptedException
     */
    public ResponseEntity<?> getBoooksByName(String bookName) throws InterruptedException {
        Book book= Book.newBuilder().setName(bookName).build();
        CountDownLatch countDownLatch=new CountDownLatch(1);
        List<Map<Descriptors.FieldDescriptor, Object>> response=new ArrayList<>();
        bookServiceStub.getBooksByName(book, new StreamObserver<Book>() {
            @Override
            public void onNext(Book value) {
                response.add(value.getAllFields());
            }

            @Override
            public void onError(Throwable t) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        return ResponseEntity.status(HttpStatus.OK)
                .body(countDownLatch.await(1, TimeUnit.MINUTES) ? response : Collections.emptyList());
    }

    /**
     * Streaming the request to the grpc service and receive single response
     * @return
     * @throws InterruptedException
     */
    public ResponseEntity<?> getExpensiveBook() throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(1);
        Map<String, Map<Descriptors.FieldDescriptor,Object>> response=new HashMap<>();
        StreamObserver<Book> responseObserver= bookServiceStub.getExpensiveBook(new StreamObserver<Book>() {
            @Override
            public void onNext(Book value) {
                response.put("Expensive book",value.getAllFields());
            }

            @Override
            public void onError(Throwable t) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        StaticDatabase.getBooks()
                        .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
        return ResponseEntity.status(HttpStatus.OK)
                .body(countDownLatch.await(1,TimeUnit.MINUTES) ? response :Collections.emptyMap());
    }

    /**
     * Bidirectional communication, stream of request and stream of response
     * @return
     * @throws InterruptedException
     */
    public ResponseEntity<?> getBooks() throws InterruptedException {
        List<Map<Descriptors.FieldDescriptor,Object>> response=new ArrayList<>();
        CountDownLatch countDownLatch=new CountDownLatch(1);
        StreamObserver<Book> streamObserver= bookServiceStub.getBooks(new StreamObserver<Book>() {
            @Override
            public void onNext(Book value) {
                response.add(value.getAllFields());
            }

            @Override
            public void onError(Throwable t) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        StaticDatabase
                .getBooks()
                .forEach(streamObserver::onNext);
        streamObserver.onCompleted();
        return ResponseEntity.status(HttpStatus.OK)
                .body(countDownLatch.await(1,TimeUnit.MINUTES) ? response : Collections.emptyList());
    }
}
