package com.example.library.services.impl;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Category;
import com.example.library.model.PaginatedResponse;
import com.example.library.model.dto.AuthorDto;
import com.example.library.model.dto.BookDto;
import com.example.library.model.dto.CategoryDto;
import com.example.library.model.helper.Email;
import com.example.library.model.mapper.BookMapper;
import com.example.library.repository.BookRepository;
import com.example.library.services.BookServices;
import com.example.library.services.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookImpl implements BookServices {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private EmailServiceImpl emailServiceimpl;
    @Autowired
    private PaginationResponseImpl paginationResponse;

    private BookMapper bookMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public BookDto createBook(BookDto bookDto) throws IOException, MessagingException {
        Book book = new Book();
        book.setBookId(bookDto.getBookId());
        //pdf name and file name is same
        book.setBookTitle(bookDto.getFileAttach().getOriginalFilename());
        book.setBookLanguage(bookDto.getBookLanguage());
        book.setBookPublicationYear(bookDto.getBookPublicationYear());
        book.setQuantity(bookDto.getQuantity());

        Category category = new Category();
        category.setCategoryId(bookDto.getCategory().getCategoryId());
        book.setCategory(category);

        Author author = new Author();
        author.setAuthorId(bookDto.getAuthor().getAuthorId());
        book.setAuthor(author);

        bookRepository.save(book);
        Book save = bookRepository.save(book);
        if (save != null) {
            Email email = new Email();
            email.setTo(new String[]{"shivanipatil9094@gmail.com"});
            email.setHeader("new book added to stock-" + LocalDate.now().toString());
            email.setMessage("grab your new book");
            email.setFile(bookDto.getFileAttach());

            //file upload logic here


            emailServiceimpl.sendEmailAttachment(email);


            MultipartFile file = bookDto.getFileUpload();
            if (file != null && !file.isEmpty()) {

                String fileName = file.getOriginalFilename();
                //build the path
                Path filePath = Paths.get(uploadDir).resolve(fileName);
                //save file on disk
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return bookDto;
        }
        return  null;
    }





        public PaginatedResponse<BookDto> getAllBooks ( int size, int pageNo, Sort sort){


            Pageable page = PageRequest.of(pageNo, size, sort);
            Page<Book> all = bookRepository.findAll(page);
            List<BookDto> collect = all.getContent().stream().map(this::bookResponse).toList();
            return paginationResponse.buildPaginatedResponse(collect, all);
        }
        @Override
        public BookDto getBook (Long id){
            Optional<Book> byId = bookRepository.findById(id);
            if (byId.isPresent()) {
                Book book = byId.get();
                BookDto dto = new BookDto();
                dto.setBookId(book.getBookId());
                dto.setBookTitle(book.getBookTitle());
                dto.setBookLanguage(book.getBookLanguage());
                dto.setBookPublicationYear(book.getBookPublicationYear());
                return dto;
            }
            return null;
        }

        @Override
        public Boolean deleteBook (Long id){
            Optional<Book> byId = bookRepository.findById(id);
            if (byId.isPresent()) {
                bookRepository.deleteById(id);
                return true;
            }
            return false;
        }


//    public BookDto bookResponse(Book book) {
//
//        BookDto bookDto = new BookDto();
//        bookDto.setBookId(book.getBookId());
//        bookDto.setBookTitle(book.getBookTitle());
//        bookDto.setBookPublicationYear(book.getBookPublicationYear());
//        bookDto.setBookLanguage(book.getBookLanguage());
//        return bookDto;
//    }
//
//
//    // Convert DTO → Entity
//    public Book bookRequest(BookDto bookDto) {
//
//        Book book = new Book();
//        book.setBookId(bookDto.getBookId());
//        book.setBookTitle(bookDto.getBookTitle());
//        book.setBookPublicationYear(bookDto.getBookPublicationYear());
//        book.setBookLanguage(bookDto.getBookLanguage());
//
//
//        return book;
//    }

        public BookDto bookResponse (Book book){
            return bookMapper.BookResponse(book);
        }

        public Book bookRequest (BookDto bookDto){
            return bookMapper.BookRequest(bookDto);
        }


    }

