package org.gbbv.musikkspring.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gbbv.musikkspring.dto.AlbumDto;
import org.gbbv.musikkspring.dto.AuthorDto;
import org.gbbv.musikkspring.model.Author;
import org.gbbv.musikkspring.service.AlbumService;
import org.gbbv.musikkspring.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS})
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    private final AlbumService albumService;

    @GetMapping("/list")
    public String listAuthors(Model model) {
        List<Author> authors = authorService.listAuthors();
        model.addAttribute("authors", authors);
        return "authors";
    }

    @GetMapping("/{authorId}")
    public String getAuthor(@PathVariable Integer authorId, Model model) {
        AuthorDto authorDto = authorService.getAuthor(authorId);
        model.addAttribute("author", authorDto);
        return "authorDetail";
    }

    @PostMapping("/add")
    public String addAuthor(@Valid @ModelAttribute AuthorDto authorDto) {
        authorService.addAuthor(authorDto);
        return "redirect:/author/list";
    }

    @GetMapping("/add")
    public String addAuthorForm(Model model) {
        AuthorDto authorDto = new AuthorDto();
        model.addAttribute("author", authorDto);
        return "addAuthor";
    }

    @GetMapping("/name/{authorId}")
    public String getAuthorName(@PathVariable Integer authorId, Model model) {
        AuthorDto author = authorService.getAuthor(authorId);
        model.addAttribute("authorName", author.getAuthorName());
        return "authorName";
    }

    @GetMapping("/albums/{authorId}")
    public String getAuthorAlbums(@PathVariable Integer authorId, Model model) {
        List<AlbumDto> albums = albumService.getAlbumsByAuthorId(authorId);
        model.addAttribute("albums", albums);
        return "authorAlbums";
    }

    @GetMapping("/update/{id}")
    public String editAuthorForm(@PathVariable("id") Integer authorId, Model model) {
        AuthorDto authorDto = authorService.getAuthor(authorId);
        model.addAttribute("author", authorDto);
        return "editAuthor";
    }

    @PutMapping("/update/{id}")
    public String updateAuthor(@PathVariable("id") Integer authorId, @Valid @ModelAttribute AuthorDto authorDto) {
        authorService.updateAuthor(authorId, authorDto);
        return "redirect:/author/" + authorId;
    }
}