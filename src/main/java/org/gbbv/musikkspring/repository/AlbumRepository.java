package org.gbbv.musikkspring.repository;

import org.gbbv.musikkspring.dto.AlbumDto;
import org.gbbv.musikkspring.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
    List<Album> findByAlbumName(String albumName);

    List<Album> findByAuthor_AuthorId(Integer authorId);

    List<Album> findByGenre_GenreId(Integer genreId);

    List<Album> findByAlbumNameContaining(String query);

}
