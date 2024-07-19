package org.gbbv.musikkspring.controllers.RestControllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gbbv.musikkspring.common.ApiResponse;
import org.gbbv.musikkspring.dto.AlbumDto;
import org.gbbv.musikkspring.model.Album;
import org.gbbv.musikkspring.repository.AlbumRepository;
import org.gbbv.musikkspring.repository.AuthorRepository;
import org.gbbv.musikkspring.repository.GenreRepository;
import org.gbbv.musikkspring.service.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS})
@RestController
@RequiredArgsConstructor
@RequestMapping("/album")
public class AlbumController {

    private final AlbumRepository albumRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    private final AlbumService albumService;

    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);


    @GetMapping
    public ResponseEntity<List<AlbumDto>> getAlbums() {
        List<AlbumDto> albumDtos = albumService.listAlbums();
        return new ResponseEntity<>(albumDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addAlbum(@RequestBody AlbumDto albumDto) {
        return albumService.addAlbum(albumDto);
//        return ResponseEntity.ok(new ApiResponse(true, "Album added successfully"));
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumDto> getAlbum(@PathVariable Integer albumId) {
        Optional<Album> albumOptional = albumRepository.findById(albumId);
        if (albumOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        AlbumDto albumDto = albumService.getAlbum(albumId);
        return ResponseEntity.ok(albumDto);
    }

    @GetMapping("/genre/{genreId}")
    public ResponseEntity<List<AlbumDto>> getAlbumsByGenreId(@PathVariable Integer genreId) {
        List<AlbumDto> albumDtos = albumService.getAlbumsByGenreId(genreId);
        return ResponseEntity.ok(albumDtos);
    }

    @PutMapping("/{albumId}")
    public ResponseEntity<ApiResponse> updateAlbum(@PathVariable Integer albumId, @RequestBody @Valid AlbumDto albumDto) {
        Optional<Album> albumOptional = albumRepository.findById(albumId);
        logger.info("Received authorId: " + albumDto.getAuthorId());
        if (albumOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Album not found"));
        }
        albumService.updateAlbum(albumId, albumDto);
        return ResponseEntity.ok(new ApiResponse(true, "Album updated successfully"));
    }
}