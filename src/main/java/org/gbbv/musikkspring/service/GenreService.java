package org.gbbv.musikkspring.service;

import org.gbbv.musikkspring.exceptions.GenreNotFoundException;
import org.gbbv.musikkspring.model.Genre;
import org.gbbv.musikkspring.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public Genre readGenre(String genreName) {
        return genreRepository.findByGenreName(genreName);
    }

    public void deleteGenre(Integer genreId) {
        genreRepository.deleteById(genreId);
    }

    public Genre readGenreById(Integer genreId) {
        return genreRepository.findById(genreId).get();
    }

    public Optional<Genre> readGenre(Integer genreId) {
        return genreRepository.findById(genreId);
    }

    public void updateGenre(Integer genreId, Genre newGenre) {
        Genre genre = genreRepository.findById(genreId).get();
        genre.setGenreName(newGenre.getGenreName());
        genre.setDescription(newGenre.getDescription());
        genre.setImageUrl(newGenre.getImageUrl());
        genreRepository.save(genre);
    }
    public void addGenre(Genre genre) {
        genreRepository.save(genre);
    }

    public List<Genre> listGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(Integer genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException("Genre with id " + genreId + " not found"));
    }
}