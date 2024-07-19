package org.gbbv.musikkspring.service;

import lombok.RequiredArgsConstructor;
import org.gbbv.musikkspring.common.ApiResponse;
import org.gbbv.musikkspring.dto.AlbumDto;
import org.gbbv.musikkspring.exceptions.AlbumNotExistException;
import org.gbbv.musikkspring.model.Album;
import org.gbbv.musikkspring.model.Author;
import org.gbbv.musikkspring.model.Genre;
import org.gbbv.musikkspring.repository.AlbumRepository;
import org.gbbv.musikkspring.repository.AuthorRepository;
import org.gbbv.musikkspring.repository.GenreRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;
    private static final Logger logger = LoggerFactory.getLogger(AlbumService.class);

    public static Album getAlbumFromDto(AlbumDto albumDto) {
        Album album = new Album();
        album.setAlbumName(albumDto.getAlbumName());
        album.setReleaseDate(albumDto.getReleaseDate());
        album.setPrice(albumDto.getPrice());
        return album;
    }
//    public ResponseEntity<ApiResponse> addAlbum(AlbumDto albumDto) {
//        Optional<Author> authorOptional = authorRepository.findById(albumDto.getAuthorId());
//        if (authorOptional.isEmpty()) {
//            return ResponseEntity.badRequest().body(new ApiResponse(false, "Author not found"));
//        }
//        Author author = authorOptional.get();
//        Optional<Genre> genreOptional = genreRepository.findById(albumDto.getGenreId());
//        if (genreOptional.isEmpty()) {
//            return ResponseEntity.badRequest().body(new ApiResponse(false, "Genre not found"));
//        }
//        Genre genre = genreOptional.get();
//        Album album = albumDto.toAlbum(author, genre);
//        album.setImageUrl(albumDto.getImageUrl());
//        albumRepository.save(album);
//        return ResponseEntity.ok(new ApiResponse(true, "Album added successfully"));
//    }
    public List<AlbumDto> listAlbums() {
        List<Album> albums = albumRepository.findAll();
        List<AlbumDto> albumDtos = new ArrayList<>();

        for(Album album : albums) {
            albumDtos.add(new AlbumDto(album));
        }
        return albumDtos;
    }

    public void updateAlbum(Integer albumId, AlbumDto albumDto) {
        try {
            logger.info("Updating album: " + albumDto);
            Album album = albumRepository.findById(albumId)
                    .orElseThrow(ResourceNotFoundException::new);
            album.setAlbumName(albumDto.getAlbumName());
            album.setImageUrl(albumDto.getImageUrl());
            album.setReleaseDate(albumDto.getReleaseDate());
            album.setDescription(albumDto.getDescription());
            album.setPrice(albumDto.getPrice());

            if (albumDto.getAuthorId() != null) {
                Author author = authorRepository.findById(albumDto.getAuthorId())
                        .orElseThrow(ResourceNotFoundException::new);
                album.setAuthor(author);
            }

            if (albumDto.getGenreId() != null) {
                Genre genre = genreRepository.findById(albumDto.getGenreId())
                        .orElseThrow(ResourceNotFoundException::new);
                album.setGenre(genre);
            }

            albumRepository.save(album);
            logger.info("Updated album: " + album);
        } catch (Exception e) {
            logger.error("Error updating album: " + e.getMessage());
        }
    }

    public AlbumDto getAlbum(Integer albumId) {
        Optional<Album> albumOptional = albumRepository.findById(albumId);
        if (albumOptional.isEmpty()) {
            return null;
        }
        Album album = albumOptional.get();
        AlbumDto albumDto = new AlbumDto(album);
        albumDto.setGenreId(album.getGenre().getGenreId());
        return albumDto;
    }

    public List<AlbumDto> getAlbumsByGenreId(Integer genreId) {
        List<Album> albums = albumRepository.findByGenre_GenreId(genreId);
        List<AlbumDto> albumDtos = new ArrayList<>();
        for (Album album : albums) {
            albumDtos.add(new AlbumDto(album));
        }
        return albumDtos;
    }

    public List<AlbumDto> getAlbumsByAuthorId(Integer authorId) {
        List<Album> albums = albumRepository.findByAuthor_AuthorId(authorId);
        List<AlbumDto> albumDtos = new ArrayList<>();
        for (Album album : albums) {
            albumDtos.add(new AlbumDto(album));
        }
        return albumDtos;
    }

    public void deleteAlbum(Integer albumId) {
        albumRepository.deleteById(albumId);
    }

    public void saveAlbum(Album album) {
        try {
            logger.info("Saving album: " + album);
            albumRepository.save(album);
            logger.info("Album saved: " + album);
        } catch (Exception e) {
            logger.error("Error saving album: " + e.getMessage());
        }
    }


    public Album getAlbumById(Integer albumId) throws AlbumNotExistException {
        Optional<Album> optionalAlbum = albumRepository.findById(albumId);
        if (optionalAlbum.isEmpty())
            throw new AlbumNotExistException("Album id is invalid " + albumId);
        return optionalAlbum.get();
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }
}
