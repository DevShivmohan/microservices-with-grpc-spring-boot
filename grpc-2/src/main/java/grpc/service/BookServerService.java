package grpc.service;

import com.dev.shiv.Book;
import com.dev.shiv.BookServiceGrpc;
import com.shiv.proto.StaticDatabase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class BookServerService extends BookServiceGrpc.BookServiceImplBase {
    /**
     * client will send one request and server will respond with one response.
     *
     * @param book
     * @param responseObserver
     */
    @Override
    public void getBook(Book book, StreamObserver<Book> responseObserver) {
        System.out.println("Service grpc-2 invoked");
        StaticDatabase.getBooks()
                .stream()
                .filter(book1 -> book1.getBookId() == book.getBookId())
                .findFirst()
                .ifPresent(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    /**
     * client will send one request and server will send stream of response to the client.
     *
     * @param book
     * @param responseObserver
     */
    @Override
    public void getBooksByName(Book book, StreamObserver<Book> responseObserver) {
        System.out.println("Service grpc-2 invoked");
        StaticDatabase.getBooks()
                .stream()
                .filter(book1 -> book1.getName().equalsIgnoreCase(book.getName()))
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    /**
     * client will stream of request and server will respond with one response.
     *
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<Book> getExpensiveBook(StreamObserver<Book> responseObserver) {
        System.out.println("Service grpc-2 invoked");
        return new StreamObserver<Book>() {
            Book comparisonBook = null;
            float price = 0;

            @Override
            public void onNext(Book book) {
                if (book.getPrice() > price) {
                    price = book.getPrice();
                    comparisonBook = book;
                }
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(comparisonBook);
                responseObserver.onCompleted();
            }
        };
    }

    /**
     * Bidirectional communication, stream of request and stream of response
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<Book> getBooks(StreamObserver<Book> responseObserver) {
        System.out.println("Service grpc-2 invoked");
        return new StreamObserver<Book>() {
            final List<Book> books=new ArrayList<>();
            @Override
            public void onNext(Book value) {
                books.add(value);
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                books.
                        forEach(responseObserver::onNext);
                responseObserver.onCompleted();
            }
        };
    }
}
