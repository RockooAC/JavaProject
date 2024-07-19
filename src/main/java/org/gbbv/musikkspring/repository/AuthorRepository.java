package org.gbbv.musikkspring.repository;

import org.gbbv.musikkspring.dto.AuthorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.gbbv.musikkspring.model.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author findByAuthorName(String authorName);
    List<Author> findByAuthorNameContaining(String query);
}
