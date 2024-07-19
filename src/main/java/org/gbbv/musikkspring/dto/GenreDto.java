package org.gbbv.musikkspring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

    private Integer genreId;
    private String genreName;
    private String description;
    private String imageUrl;

}
