package org.gbbv.musikkspring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorId;

    @Column(name = "author_name")
    private @NotNull String authorName;
    private String description;
    private String imageUrl;
    @Past
    private LocalDate activeSince;


    @Override
    public String toString() {
        return "Author {author id=" + authorId + ", author name='" + authorName + "', description='" + description + "', imageUrl='" + imageUrl + "', activeSince='" + activeSince + "'}";
    }
}