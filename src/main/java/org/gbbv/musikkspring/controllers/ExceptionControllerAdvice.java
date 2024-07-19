package org.gbbv.musikkspring.controllers;

import org.gbbv.musikkspring.exceptions.AlbumNotExistException;
import org.gbbv.musikkspring.exceptions.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
    Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);
    @ExceptionHandler(value = CustomException.class)
    public final String handleUpdateFailException(CustomException exception, Model model){
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }
    @ExceptionHandler(value = AlbumNotExistException.class)
    public final String handleUpdateFailException(AlbumNotExistException exception, Model model){
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }
    @ExceptionHandler(value = AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException exception, Model model){
        model.addAttribute("errorMessage", "403 - Access Denied");
        return "error-403";
    }
    @ExceptionHandler(value = Exception.class)
    public final String handleException(Exception exception, Model model){
        model.addAttribute("errorMessage", exception.getMessage());
        logger.error(exception.getMessage());
        return "error";
    }
}