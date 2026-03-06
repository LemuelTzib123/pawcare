package com.lemon.pawcare.dog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DogViewController {

    private final DogRepository dogRepository;

    public DogViewController(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @GetMapping("/dogs")
    public String dogs(Model model) {
        model.addAttribute("dogs", dogRepository.findAll());
        return "dogs";
    }

    @GetMapping("/dogs/new")
    public String newDogForm(Model model) {
        model.addAttribute("dog", new Dog());
        return "dogs-form";
    }

    @PostMapping("/dogs/save")
    public String saveDog(@ModelAttribute Dog dog) {
        dogRepository.save(dog);
        return "redirect:/dogs";
    }
}