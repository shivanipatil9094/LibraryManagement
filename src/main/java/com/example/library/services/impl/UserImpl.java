package com.example.library.services.impl;

import com.example.library.model.*;
import com.example.library.model.dto.AuthorDto;
import com.example.library.model.dto.BookDto;
import com.example.library.model.dto.CategoryDto;
import com.example.library.model.dto.UserDto;
import com.example.library.model.helper.Email;
import com.example.library.model.mapper.UserMapper;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import com.example.library.services.EmailService;
import com.example.library.services.FileService;
import com.example.library.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;
@Autowired
private BookRepository bookRepository;
@Autowired
    FileService fileService;
@Autowired
EmailService emailService;
    private ModelMapper modelMapper;
    private PaginationResponseImpl paginationResponse;

    private UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) throws Exception {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUserName(userDto.getUserName());
        user.setUserEmail(userDto.getUserEmail());
        user.setUserContact(userDto.getUserContact());


        List<Book> finalPurchasedBooks = new ArrayList<>();

        for (BookDto b : userDto.getBooks()) {
            Book book = bookRepository.findById(b.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));


            if(b.getQuantity()>=b.getQuantity()){
                book.setQuantity(book.getQuantity()-b.getQuantity());
            }
            else{
                throw new Exception("Quantity does not exist");
            }


            finalPurchasedBooks.add(book);
      }
//        finalPurchasedBooks.forEach(book1 -> {
//            System.out.println(book.getBookId());
//
//        });
//        //now deduct stock safely
//        for (Book book : finalPurchasedBooks) {
//
//            book.setQuantity(book.getQuantity() - userDto.);
//            BookRepository.save(book);
//
//            // low stock alert
//            if (book.getQuantity() <= 3) {
//                Email lowStockEmail = new Email();
//                lowStockEmail.setTo(new String[]{"gaikwadmanaliv007@gmail.com"});
//                lowStockEmail.setHeader("LOW STOCK ALERT: " + book.getBookTitle());
//                lowStockEmail.setMessage(
//                        "The book '" + book.getBookTitle() + "' has low stock.\n" +
//                                "Remaining Quantity: " + book.getQuantity()
//                );
//                EmailService.sendEmail(lowStockEmail);
//            }
//        }
        user.setBooks(finalPurchasedBooks);


        User save = userRepository.save(user);

        if(save!=null){
            List<Book> books =  save.getBooks();
            List<File> fileList = new ArrayList<>();
            books.forEach(book -> {
                File book1 = fileService.getBook(book.getBookTitle());
                fileList.add(book1);
            });
            Email email = new Email();
            email.setTo(new String[] {"shivanipatil9094@gmail.com"});
            email.setHeader("Purchased Books");
            email.setMessage("the list of books purchased");
            email.setFiles(fileList);
            emailService.sendEmailAttachments(email);

            bookRepository.saveAll(finalPurchasedBooks);

        }



//
//        List<BookDto> bookDto = userDto.getBooks();
//        List<Book> books = new ArrayList<>();
//        bookDto.forEach(b->
//        {
//            Book book = new Book();
//            book.setBookId(b.getBookId());
//            books.add(book);
//        });
//
//
//        user.setBooks(books);
     return userDto;
    }
    public PaginatedResponse<UserDto> getAllEmployees(int size, int pageNo, Sort sort) {


        Pageable page = PageRequest.of(pageNo, size , sort);
        Page<User> all = userRepository.findAll(page);
        List<UserDto> collect = all.getContent().stream().map(this::userResponse).toList();
        return paginationResponse.buildPaginatedResponse(collect, all);
    }


    @Override
    public UserDto getUser(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent())
        {


            List<Book> books = byId.get().getBooks();
            List<BookDto> bookDtos = new ArrayList<>();
            books.forEach(book ->
            {
                BookDto dto = new BookDto();
                dto.setBookId(book.getBookId());
                dto.setBookLanguage(book.getBookLanguage());
                dto.setBookPublicationYear(book.getBookPublicationYear());
                dto.setBookTitle(book.getBookTitle());
                bookDtos.add(dto);

                dto.setAuthor(AuthorResponse(book.getAuthor()));

//                Author author = book.getAuthor();
//                AuthorDto authorDto = new AuthorDto();
//                authorDto.setAuthorId(author.getAuthorId());
//                authorDto.setAuthorName(author.getAuthorName());
//                authorDto.setAuthorGenre(author.getAuthorGenre());

//                Category category = book.getCategory();
//                CategoryDto categoryDto = new CategoryDto();
//                categoryDto.setCategoryId(category.getCategoryId());
//                categoryDto.setCategoryName(category.getCategoryName());

                dto.setCategory(categoryResponse(book.getCategory()));




            });
            User user = byId.get();
            UserDto dto = new UserDto();
            dto.setUserId(user.getUserId());
            dto.setUserName(user.getUserName());
            dto.setUserEmail(user.getUserEmail());
            dto.setUserContact(user.getUserContact());
            dto.setBooks(bookDtos);
            return dto;
        }
        return null;
    }

    @Override
    public Boolean deleteUser(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


//    public UserDto userResponse(User user) {
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(user.getUserId());
//        userDto.setUserEmail(user.getUserEmail());
//        userDto.setUserContact(user.getUserContact());
//        userDto.setUserName(user.getUserName());
//
//
//        return userDto;
//    }
//
//
//    // Convert DTO → Entity
//    public User userRequest(UserDto userDto) {
//
//        User user = new User();
//        user.setUserId(userDto.getUserId());
//        user.setUserName(userDto.getUserName());
//        user.setUserEmail(userDto.getUserEmail());
//        user.setUserContact(userDto.getUserContact());
//
//        return user;
//    }



//    public UserDto userResponse(User user){
//        return modelMapper.map(user , UserDto.class);
//    }
//
//    public User userRequest(UserDto userdto){
//        return modelMapper.map(userdto , User.class);
//    }

    public CategoryDto categoryResponse(Category category){
        category.setBooks(null);
        return modelMapper.map(category,CategoryDto.class);
    }
//
//    public Category categoryRequest(CategoryDto categoryDto){
//        return modelMapper.map(categoryDto,Category.class);
//    }
//
//

    public BookDto BookResponse(Book book){
        book.setAuthor(null);
        return modelMapper.map(book,BookDto.class);
    }

    public Book BookRequest(BookDto bookDto){
        return modelMapper.map(bookDto,Book.class);
    }

    public AuthorDto AuthorResponse(Author author){
         author.setBooks(null);
        return modelMapper.map(author,AuthorDto.class);
    }

    public Author AuthorRequest(AuthorDto authorDto){
        return modelMapper.map(authorDto,Author.class);
    }


    public UserDto userResponse(User user){
        return userMapper.userResponse(user);
    }

    public User userRequest(UserDto userDto){
        return userMapper.userRequest(userDto);
    }


}
