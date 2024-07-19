package org.gbbv.musikkspring.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gbbv.musikkspring.model.Author;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    private Integer authorId;
    private String authorName;
    private String description;
    private String imageUrl;
    private LocalDate activeSince;

    public Author toAuthor(){
        Author author = new Author();
        author.setAuthorId(this.authorId);
        author.setAuthorName(this.authorName);
        author.setDescription(this.description);
        author.setImageUrl(this.imageUrl);
        author.setActiveSince(this.activeSince);
        return author;
    }
}
