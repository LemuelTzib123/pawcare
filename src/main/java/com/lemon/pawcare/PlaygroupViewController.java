package com.lemon.pawcare.playgroup;

import com.lemon.pawcare.dog.DogRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/playgroups")
public class PlaygroupViewController {

    private final PlaygroupRepository playgroupRepository;
    private final DogRepository dogRepository;

    public PlaygroupViewController(PlaygroupRepository playgroupRepository,
                                   DogRepository dogRepository) {
        this.playgroupRepository = playgroupRepository;
        this.dogRepository = dogRepository;
    }

    // List playgroups
    @GetMapping
    public String listPlaygroups(Model model) {
        model.addAttribute("playgroups", playgroupRepository.findAll());
        return "playgroups";
    }

    // Show create form
    @GetMapping("/new")
    public String newPlaygroup(Model model) {
        model.addAttribute("playgroup", new Playgroup());
        model.addAttribute("dogs", dogRepository.findAll());
        return "playgroup-form";
    }

    // Save playgroup
    @PostMapping("/save")
    public String savePlaygroup(@ModelAttribute Playgroup playgroup) {
        playgroupRepository.save(playgroup);
        return "redirect:/playgroups";
    }

    // Edit playgroup
    @GetMapping("/edit/{id}")
    public String editPlaygroup(@PathVariable Long id, Model model) {
        Playgroup playgroup = playgroupRepository.findById(id).orElseThrow();
        model.addAttribute("playgroup", playgroup);
        return "playgroup-form";
    }

    // Delete playgroup
    @GetMapping("/delete/{id}")
    public String deletePlaygroup(@PathVariable Long id) {
        playgroupRepository.deleteById(id);
        return "redirect:/playgroups";
    }
}