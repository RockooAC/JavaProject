package org.gbbv.musikkspring.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gbbv.musikkspring.dto.AlbumDto;
import org.gbbv.musikkspring.model.Genre;
import org.gbbv.musikkspring.model.User;
import org.gbbv.musikkspring.service.AlbumService;
import org.gbbv.musikkspring.service.GenreService;
import org.gbbv.musikkspring.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS})
@Controller
@RequiredArgsConstructor
@RequestMapping("/genre")
public class GenreController {
    private final GenreService genreService;
    private final AlbumService albumService;
    private final UserServiceImpl userService;
    @GetMapping("/list")
    public String listGenres(Model model) {
        List<Genre> genres = genreService.listGenres();
        model.addAttribute("genres", genres);
        return "genres";
    }

    @GetMapping("/name/{genreId}")
    public String getGenreName(@PathVariable Integer genreId, Model model) {
        Genre genre = genreService.readGenreById(genreId);
        if (genre != null) {
            model.addAttribute("genreName", genre.getGenreName());
            return "genreName";
        }
        return "error";
    }

    @GetMapping("/{genreId}/albums")
    public String getAlbumsByGenreId(@PathVariable Integer genreId, Model model) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails)principal).getUsername();
                User currentUser = userService.getUserByUsername(username);
                model.addAttribute("currentUser", currentUser);
            }

            List<AlbumDto> albumDtos = albumService.getAlbumsByGenreId(genreId);
            model.addAttribute("albums", albumDtos);
            return "albumsByGenre";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{genreId}")
    public String getGenre(@PathVariable ("genreId") Integer genreId, Model model) {
        Genre genre = genreService.readGenreById(genreId);
        if (genre != null) {
            model.addAttribute("genre", genre);
            return "genreDetail";
        }
        return "error";
    }

    @PostMapping("/add")
    public String addGenre(@Valid @ModelAttribute Genre genre) {
        if (Objects.nonNull(genreService.readGenre(genre.getGenreName()))) {
            return "error";
        }
        genreService.addGenre(genre);
        return "redirect:/genre/list";
    }

    @GetMapping("/add")
    public String addGenreForm(Model model) {
        model.addAttribute("genre", new Genre());
        return "genreForm";
    }

    @GetMapping("/update/{genreId}")
    public String updateGenreForm(@PathVariable ("genreId") Integer genreId, Model model) {
        Genre genre = genreService.readGenreById(genreId);
        if (genre != null) {
            model.addAttribute("genre", genre);
            return "editGenreForm";
        }
        return "error";
    }

    @PutMapping("/update/{genreId}")
    public String updateGenre(@PathVariable ("genreId") Integer genreId, @Valid @ModelAttribute Genre genre) {
        if (Objects.nonNull(genreService.readGenre(genreId))) {
            genreService.updateGenre(genreId, genre);
            return "redirect:/genre/" + genreId;
        }
        return "error";
    }

    @DeleteMapping("/{genreId}")
    public String deleteGenre(@PathVariable Integer genreId) {
        genreService.deleteGenre(genreId);
        return "redirect:/genre/list";
    }

}