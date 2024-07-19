package org.gbbv.musikkspring.controllers;

import lombok.RequiredArgsConstructor;
import org.gbbv.musikkspring.dto.AddToCartDto;
import org.gbbv.musikkspring.dto.AlbumDto;
import org.gbbv.musikkspring.dto.AuthorDto;
import org.gbbv.musikkspring.model.*;
import org.gbbv.musikkspring.repository.AlbumRepository;
import org.gbbv.musikkspring.service.AuthorService;
import org.gbbv.musikkspring.service.GenreService;
import org.gbbv.musikkspring.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.gbbv.musikkspring.service.AlbumService;
import org.gbbv.musikkspring.repository.UserRepository;
import org.gbbv.musikkspring.repository.RoleRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/album")
public class AlbumController {

    private final AlbumService albumService;

    private final GenreService genreService;

    private final AuthorService authorService;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AlbumRepository albumRepository;

    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;


    Logger logger = LoggerFactory.getLogger(AlbumController.class);

    @GetMapping("/list")
    public String getAlbums(Model model) {
        List<AlbumDto> albumDtos = albumService.listAlbums();
        model.addAttribute("albums", albumDtos);
        return "albums";
    }
    @GetMapping("/shop")
    public String showAlbumsShop(Model model) {
        List<Album> albums = albumService.getAllAlbums();
        model.addAttribute("albums", albums);
        model.addAttribute("addToCartDto", new AddToCartDto());
        Cart cart = new Cart();
        model.addAttribute("cart", cart);
        model.addAttribute("authorDto", new AuthorDto());
        UserDetails userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
        User currentUser = userService.findUserByEmail(username);;
        model.addAttribute("currentUser", currentUser);
        return "albumsShop";
    }

    @PostMapping("/add")
    public String addAlbum(@ModelAttribute AlbumDto albumDto) {
        Genre genre = genreService.getGenreById(albumDto.getGenreId());
        Author author = authorService.getAuthorById(albumDto.getAuthorId());
        try {
            Album album = albumDto.toAlbum(author, genre);
            logger.info("Attempting to save album: " + album);
            albumService.saveAlbum(album);
            logger.info("Album saved successfully");
        } catch (Exception e) {
            logger.error("Error adding album: " + e.getMessage());
        }

        return "redirect:/album/list";
    }


    @GetMapping("/add")
    public String addAlbumForm(Model model) {
        List<Genre> genres = genreService.listGenres();
        List<Author> authors = authorService.listAuthors();
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        model.addAttribute("albumDto", new AlbumDto());
        return "albumForm";
    }
    @GetMapping("/{albumId}")
    public String getAlbum(@PathVariable Integer albumId, Model model) {
        AlbumDto albumDto = albumService.getAlbum(albumId);
        model.addAttribute("genre", genreService.getGenreById(albumDto.getGenreId()));
        model.addAttribute("album", albumDto);
        model.addAttribute("author", authorService.getAuthorById(albumDto.getAuthorId()));
        return "albumDetail";
    }

    @GetMapping("/genre/{genreId}")
    public String getAlbumsByGenreId(@PathVariable Integer genreId, Model model) {
        List<AlbumDto> albumDtos = albumService.getAlbumsByGenreId(genreId);
        model.addAttribute("albums", albumDtos);
        return "albumsByGenre";
    }
    @GetMapping("/update/{albumId}")
    public String editAlbumForm(@PathVariable Integer albumId, Model model) {
        AlbumDto albumDto = albumService.getAlbum(albumId);
        model.addAttribute("album", albumDto);
        return "editAlbum";
    }

    @PostMapping("/update/{albumId}")
    public String updateAlbum(@PathVariable Integer albumId, @ModelAttribute AlbumDto albumDto) {
        try {
            logger.info("Attempting to update album: " + albumDto);
            albumService.updateAlbum(albumId, albumDto);
            logger.info("Album updated successfully");
        } catch (Exception e) {
            logger.error("Error updating album: " + e.getMessage());
        }
        return "redirect:/album/" + albumId;
    }

    @DeleteMapping("/{albumId}")
    public String deleteAlbum(@PathVariable Integer albumId) {
        albumService.deleteAlbum(albumId);
        return "redirect:/album/list";
    }
}