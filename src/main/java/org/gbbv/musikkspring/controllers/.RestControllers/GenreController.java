package org.gbbv.musikkspring.controllers.RestControllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gbbv.musikkspring.common.ApiResponse;
import org.gbbv.musikkspring.model.Genre;
import org.gbbv.musikkspring.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS})
@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")

public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping("/list")
    public ResponseEntity<List<Genre>> listGenres() {
        List<Genre> genres = genreService.listGenres();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }
    @GetMapping("/name/{genreId}")
    public ResponseEntity<String> getGenreName(@PathVariable Integer genreId) {
        Genre genre = genreService.readGenreById(genreId);
        if (genre != null) {
            return new ResponseEntity<>(genre.getGenreName(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<Genre> getGenre(@PathVariable ("genreId") Integer genreId) {
        Genre genre = genreService.readGenreById(genreId);
        if (genre != null) {
            return new ResponseEntity<>(genre, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addGenre(@Valid @RequestBody Genre genre) {
        if (Objects.nonNull(genreService.readGenre(genre.getGenreName()))) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "genre already exists"), HttpStatus.CONFLICT);
        }
        genreService.addGenre(genre);
        return new ResponseEntity<>(new ApiResponse(true, "added the genre"), HttpStatus.CREATED);
    }

    @PutMapping("/update/{genreId}")
    public ResponseEntity<ApiResponse> updateGenre(@PathVariable ("genreId") Integer genreId, @Valid @RequestBody Genre genre) {
        if (Objects.nonNull(genreService.readGenre(genreId))) {
            genreService.updateGenre(genreId, genre);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "updated the genre"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false, "genre does not exist"), HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{genreId}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Integer genreId) {
        genreService.deleteGenre(genreId);
        return ResponseEntity.noContent().build();
    }

}