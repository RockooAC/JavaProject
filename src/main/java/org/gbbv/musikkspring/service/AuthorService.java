package org.gbbv.musikkspring.service;

import lombok.RequiredArgsConstructor;
import org.gbbv.musikkspring.dto.AuthorDto;
import org.gbbv.musikkspring.model.Album;
import org.gbbv.musikkspring.model.Author;
import org.gbbv.musikkspring.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public void addAuthor(AuthorDto authorDto) {
        Author author = authorDto.toAuthor();
        authorRepository.save(author);
    }

    public List<Author> listAuthors() {
        return authorRepository.findAll();
    }

    public void updateAuthor(Integer authorId, AuthorDto authorDto) {
        authorDto.setAuthorId(authorId);
        Author author = authorDto.toAuthor();
        authorRepository.save(author);
    }

    public AuthorDto getAuthor(Integer authorId) {
        Author author = authorRepository.findById(authorId).orElse(null);
        return modelMapper.map(author, AuthorDto.class);
    }

    public Author getAuthorById(Integer authorId) {
        return authorRepository.findById(authorId).get();
    }

}
