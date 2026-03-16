package com.lemon.pawcare.dog;

import com.lemon.pawcare.owner.Owner;
import com.lemon.pawcare.owner.OwnerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dogs")
public class DogViewController {

    private final DogRepository dogRepository;
    private final OwnerRepository ownerRepository;

    public DogViewController(DogRepository dogRepository,
                             OwnerRepository ownerRepository) {
        this.dogRepository = dogRepository;
        this.ownerRepository = ownerRepository;
    }

    // List all dogs
    @GetMapping
    public String listDogs(Model model) {
        model.addAttribute("dogs", dogRepository.findAll());
        return "dogs";
    }

    // Show add form
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("dog", new Dog());
        model.addAttribute("owners", ownerRepository.findAll());
        return "dogs-form";
    }

    // Save dog
    @PostMapping("/save")
    public String saveDog(@ModelAttribute Dog dog,
                          @RequestParam(required = false) Long ownerId) {

        if (ownerId != null) {
            Owner owner = ownerRepository.findById(ownerId).orElse(null);
            dog.setOwner(owner);
        }

        dogRepository.save(dog);
        return "redirect:/dogs";
    }

    // Edit dog
    @GetMapping("/edit/{id}")
    public String editDog(@PathVariable Long id, Model model) {
        Dog dog = dogRepository.findById(id).orElseThrow();
        model.addAttribute("dog", dog);
        model.addAttribute("owners", ownerRepository.findAll());
        return "dogs-form";
    }

    // Delete dog
    @GetMapping("/delete/{id}")
    public String deleteDog(@PathVariable Long id) {
        dogRepository.deleteById(id);
        return "redirect:/dogs";
    }
}