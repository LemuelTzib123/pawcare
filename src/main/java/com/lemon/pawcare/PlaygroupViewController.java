package com.lemon.pawcare.playgroup;

import com.lemon.pawcare.dog.DogRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PlaygroupViewController {

    private final PlaygroupRepository playgroupRepository;
    private final DogRepository dogRepository;

    public PlaygroupViewController(PlaygroupRepository playgroupRepository, DogRepository dogRepository) {
        this.playgroupRepository = playgroupRepository;
        this.dogRepository = dogRepository;
    }

    // Show all playgroups
    @GetMapping("/playgroups")
    public String playgroups(Model model) {
        model.addAttribute("playgroups", playgroupRepository.findAll());
        return "playgroups";
    }

    // Show create form
    @GetMapping("/playgroups/new")
    public String newPlaygroup(Model model) {
        model.addAttribute("playgroup", new Playgroup());
        model.addAttribute("dogs", dogRepository.findAll());
        return "playgroup-form";
    }

    // Save playgroup
    @PostMapping("/playgroups/save")
    public String savePlaygroup(@ModelAttribute Playgroup playgroup) {
        playgroupRepository.save(playgroup);
        return "redirect:/playgroups";
    }

}