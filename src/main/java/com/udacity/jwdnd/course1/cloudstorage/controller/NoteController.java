package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;

@Controller
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;
    private final HomeController homeController;

    public NoteController(NoteService noteService, HomeController homeController) {
        this.noteService = noteService;
        this.homeController = homeController;
    }

    @GetMapping(value = "/delete")
    public String findAll(Model model, @RequestParam("noteId") int id) {
        if (noteService.deleteById(id) < 0) {
            model.addAttribute("errMsg", "Something went wrong. Please try again.");
        } else {
            model.addAttribute("successMsg", "Delete note success.");
        }
        homeController.getDataAllPage(model);
        return "home";
    }

    @PostMapping()
    public String insert(
            @RequestParam("noteId") String noteId,
            @RequestParam("noteTitle") String title,
            @RequestParam("noteDescription") String description,
            Model model) {

        if (noteService.createNote(noteId, title, description) < 0) {
            model.addAttribute("errMsg", "Something went wrong. Please try again.");
        } else {
            if (noteId.isEmpty()) {
                model.addAttribute("successMsg", "Add note success.");
            } else {
                model.addAttribute("successMsg", "Update note success.");
            }
        }
        homeController.getDataAllPage(model);

        return "home";
    }

}
