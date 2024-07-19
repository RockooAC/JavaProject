package org.gbbv.musikkspring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gbbv.musikkspring.model.Album;
import org.gbbv.musikkspring.model.Author;
import org.gbbv.musikkspring.model.Genre;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDto {
    private Integer albumId;
    private String albumName;
    private String imageUrl;
    private String releaseDate;
    private String description;
    private Float price;
    private Integer genreId;
    private Integer authorId;

    public AlbumDto(Album album) {
        this.albumId = album.getAlbumId();
        this.albumName = album.getAlbumName();
        this.imageUrl = album.getImageUrl();
        this.releaseDate = album.getReleaseDate();
        this.description = album.getDescription();
        this.price = album.getPrice();
        this.genreId = album.getGenre().getGenreId();
        this.authorId = album.getAuthor().getAuthorId();
    }

    public Album toAlbum(Author author, Genre genre) {
        Album album = new Album();
        album.setAlbumId(this.albumId);
        album.setAlbumName(this.albumName);
        album.setReleaseDate(this.releaseDate);
        album.setPrice(this.price);
        album.setDescription(this.description);
        album.setAuthor(author);
        album.setGenre(genre);
        album.setImageUrl(this.imageUrl);
        return album;
    }


    public Integer getId() {
        return albumId;
    }
}