package com.lemon.pawcare.owner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OwnerViewController {

    private final OwnerRepository ownerRepository;

    public OwnerViewController(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @GetMapping("/owners")
    public String owners(Model model) {
        model.addAttribute("owners", ownerRepository.findAll());
        return "owners";
    }

    @GetMapping("/owners/new")
    public String newOwnerForm(Model model) {
        model.addAttribute("owner", new Owner());
        return "owner-form";
    }

    @PostMapping("/owners/save")
    public String saveOwner(@ModelAttribute Owner owner) {
        ownerRepository.save(owner);
        return "redirect:/owners";
    }

    @GetMapping("/owners/edit/{id}")
    public String editOwner(@PathVariable Long id, Model model) {
        Owner owner = ownerRepository.findById(id).orElseThrow();
        model.addAttribute("owner", owner);
        return "owner-form";
    }

    @GetMapping("/owners/delete/{id}")
    public String deleteOwner(@PathVariable Long id) {
        ownerRepository.deleteById(id);
        return "redirect:/owners";
    }
}