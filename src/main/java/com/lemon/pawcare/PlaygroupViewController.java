package com.lemon.pawcare.playgroup;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PlaygroupViewController {

    private final PlaygroupRepository playgroupRepository;

    public PlaygroupViewController(PlaygroupRepository playgroupRepository) {
        this.playgroupRepository = playgroupRepository;
    }

    @GetMapping("/playgroups")
    public String playgroups(Model model) {
        model.addAttribute("playgroups", playgroupRepository.findAll());
        return "playgroups";
    }

    @GetMapping("/playgroups/new")
    public String newPlaygroup(Model model) {
        model.addAttribute("playgroup", new Playgroup());
        return "playgroup-form";
    }

    @PostMapping("/playgroups/save")
    public String savePlaygroup(@ModelAttribute Playgroup playgroup) {

        if (playgroup.getId() != null) {
            // 🔥 UPDATE
            Playgroup existing = playgroupRepository.findById(playgroup.getId())
                    .orElseThrow(() -> new RuntimeException("Playgroup not found"));

            existing.setGroupName(playgroup.getGroupName());
            existing.setActivityType(playgroup.getActivityType());
            existing.setSizeLimit(playgroup.getSizeLimit());

            playgroupRepository.save(existing);

        } else {
            // CREATE
            playgroupRepository.save(playgroup);
        }

        return "redirect:/playgroups";
    }

    @GetMapping("/playgroups/edit/{id}")
    public String editPlaygroup(@PathVariable Long id, Model model) {
        Playgroup pg = playgroupRepository.findById(id).orElse(null);
        if (pg == null) return "redirect:/playgroups";

        model.addAttribute("playgroup", pg);
        return "playgroup-form";
    }

    @GetMapping("/playgroups/delete/{id}")
    public String deletePlaygroup(@PathVariable Long id) {
        playgroupRepository.deleteById(id);
        return "redirect:/playgroups";
    }
}