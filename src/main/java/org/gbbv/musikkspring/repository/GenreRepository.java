package org.gbbv.musikkspring.repository;

import org.gbbv.musikkspring.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    List<Genre> findByGenreNameContaining(String query);
    Genre findByGenreName(String genreName);
    boolean existsById(Integer genreId);

    List<Genre> findAll();


}

