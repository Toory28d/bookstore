package com.bookstore.service;

import com.bookstore.dto.request.AuthorRequestDto;
import com.bookstore.dto.response.AuthorResponseDto;
import com.bookstore.entity.Author;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.mapper.AuthorMapper;
import com.bookstore.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public List<AuthorResponseDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public AuthorResponseDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return authorMapper.toResponseDto(author);
    }

    @Transactional
    public AuthorResponseDto createAuthor(AuthorRequestDto request) {
        Author author = authorMapper.toEntity(request);
        author = authorRepository.save(author);
        return authorMapper.toResponseDto(author);
    }

    @Transactional
    public AuthorResponseDto updateAuthor(Long id, AuthorRequestDto request) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        authorMapper.updateEntity(author, request);
        author = authorRepository.save(author);

        return authorMapper.toResponseDto(author);
    }

    @Transactional
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        authorRepository.delete(author);
    }
}
