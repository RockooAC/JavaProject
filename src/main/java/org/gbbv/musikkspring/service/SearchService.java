package org.gbbv.musikkspring.service;

import lombok.Getter;
import lombok.Setter;
import org.gbbv.musikkspring.model.Author;
import org.gbbv.musikkspring.model.Genre;
import org.gbbv.musikkspring.model.Album;
import org.gbbv.musikkspring.repository.AuthorRepository;
import org.gbbv.musikkspring.repository.GenreRepository;
import org.gbbv.musikkspring.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Service
public class SearchService {


    private final AuthorRepository authorRepository;


    private final GenreRepository genreRepository;


    private final AlbumRepository albumRepository;

    public SearchService(AuthorRepository authorRepository, GenreRepository genreRepository, AlbumRepository albumRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.albumRepository = albumRepository;
    }


    public List<Object> search(String query) {
        List<Object> results = new ArrayList<>();

        List<Author> authors = authorRepository.findByAuthorNameContaining(query);
        for (Author author : authors) {
            results.add(new SearchResult("author", author.getAuthorName(), author.getAuthorId()));
        }

        List<Genre> genres = genreRepository.findByGenreNameContaining(query);
        for (Genre genre : genres) {
            results.add(new SearchResult("genre", genre.getGenreName(), genre.getGenreId()));
        }

        List<Album> albums = albumRepository.findByAlbumNameContaining(query);
        for (Album album : albums) {
            results.add(new SearchResult("album", album.getAlbumName(), album.getAlbumId()));
        }

        return results;
    }
    @Getter
    @Setter
    private static class SearchResult {
        private String type;
        private String name;
        private Integer id;

        public SearchResult(String type, String name, Integer id) {
            this.type = type;
            this.name = name;
            this.id = id;
        }
        @Override
        public String toString() {
            return "SearchResult{" +
                    "type='" + type + '\'' +
                    ", name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        }
    }
}