package com.example.library.services.impl;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Category;
import com.example.library.model.PaginatedResponse;
import com.example.library.model.dto.AuthorDto;
import com.example.library.model.dto.CategoryDto;
import com.example.library.model.mapper.AuthorMapper;
import com.example.library.repository.AuthorRepository;
import com.example.library.services.AuthorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorImpl implements AuthorServices {
@Autowired
    private AuthorRepository authorRepository;
private PaginationResponseImpl paginationResponse;

private AuthorMapper authorMapper;


@Override
public AuthorDto createAuthor(AuthorDto authorDto){
    Author author= new Author();
    author.setAuthorId(authorDto.getAuthorId());
    author.setAuthorName(authorDto.getAuthorName());
    author.setAuthorGenre(authorDto.getAuthorGenre());



    Book book = new Book();


    authorRepository.save(author);
    return authorDto;
}

    public PaginatedResponse<AuthorDto> getAllAuthors(int size, int pageNo, Sort sort) {


        Pageable page = PageRequest.of(pageNo, size , sort);
        Page<Author> all = authorRepository.findAll(page);
        List<AuthorDto> collect = all.getContent().stream().map(this::AuthorResponse).toList();
        return paginationResponse.buildPaginatedResponse(collect, all);
    }

    @Override
    public AuthorDto getAuthor(Long id) {
        Optional<Author> byId =authorRepository.findById(id);
        if(byId.isPresent()) {
            AuthorDto dto = new AuthorDto();

            dto.setAuthorId(byId.get().getAuthorId());
            dto.setAuthorName(byId.get().getAuthorName());
            dto.setAuthorGenre(byId.get().getAuthorGenre());
            return dto;
        }
        return null;
    }

    @Override
    public Boolean deleteAuthor(Long id) {
          Optional<Author> byId=authorRepository.findById(id);
          if(byId.isPresent()){
               authorRepository.deleteById(id);
               return  true;
          }
          return false;
    }

//    public AuthorDto authorResponse(Author author){
//           AuthorDto authorDto = new AuthorDto();
//           author.setAuthorId(author.getAuthorId());
//           author.setAuthorGenre(author.getAuthorGenre());
//           author.setAuthorName(author.getAuthorName());
//           return authorDto;
//    }
//
//     public Author authorRequest(AuthorDto authorDto){
//           Author author=new Author();
//           author.setAuthorName(authorDto.getAuthorName());
//           author.setAuthorGenre(authorDto.getAuthorGenre());
//           author.setAuthorId(authorDto.getAuthorId());
//           return  author;
//     }

    public AuthorDto AuthorResponse(Author author){
        return authorMapper.AuthorResponse(author);
    }

    public Author authorRequest(AuthorDto authorDto){
        return authorMapper.AuthorRequest(authorDto);
    }




}




