package org.gbbv.musikkspring.controllers.RestControllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gbbv.musikkspring.common.ApiResponse;
import org.gbbv.musikkspring.dto.AlbumDto;
import org.gbbv.musikkspring.dto.AuthorDto;
import org.gbbv.musikkspring.model.Author;
import org.gbbv.musikkspring.service.AlbumService;
import org.gbbv.musikkspring.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS})
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private AlbumService albumService;


    @GetMapping("/list")
    public ResponseEntity<List<Author>> listAuthors() {
        return new ResponseEntity<>(authorService.listAuthors(), HttpStatus.OK);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable Integer authorId) {
        AuthorDto authorDto = authorService.getAuthor(authorId);
        if (authorDto == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(authorDto);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addAuthor(@Valid @RequestBody AuthorDto authorDto) {
        authorService.addAuthor(authorDto);
        return new ResponseEntity<>(new ApiResponse(true, "Added the author"), HttpStatus.CREATED);
    }

    @GetMapping("/name/{authorId}")
    public ResponseEntity<String> getAuthorName(@PathVariable Integer authorId) {
        AuthorDto author = authorService.getAuthor(authorId);
        if (author != null) {
            return new ResponseEntity<>(author.getAuthorName(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/albums/{authorId}")
    public ResponseEntity<List<AlbumDto>> getAuthorAlbums(@PathVariable Integer authorId) {
        List<AlbumDto> albums = albumService.getAlbumsByAuthorId(authorId);
        if (albums != null) {
            return new ResponseEntity<>(albums, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateAuthor(@PathVariable("id") Integer authorId, @Valid @RequestBody AuthorDto authorDto) {
        authorService.updateAuthor(authorId, authorDto);
        return new ResponseEntity<>(new ApiResponse(true, "Updated the author"), HttpStatus.OK);
    }

}