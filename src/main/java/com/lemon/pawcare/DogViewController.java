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

    // Show all dogs
    @GetMapping
    public String listDogs(Model model) {
        model.addAttribute("dogs", dogRepository.findAll());
        return "dogs";
    }

    // Show add form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("dog", new Dog());
        model.addAttribute("owners", ownerRepository.findAll());
        return "dogs-form";
    }

    // Save (handles BOTH create and update)
    @PostMapping("/save")
    public String saveDog(@ModelAttribute Dog dog) {

        if (dog.getId() != null) {
            // UPDATE existing dog
            Dog existingDog = dogRepository.findById(dog.getId())
                    .orElseThrow(() -> new RuntimeException("Dog not found"));

            existingDog.setName(dog.getName());
            existingDog.setBreed(dog.getBreed());
            existingDog.setAge(dog.getAge());
            existingDog.setVaccinated(dog.isVaccinated());

            if (dog.getOwner() != null && dog.getOwner().getId() != null) {
                Owner owner = ownerRepository.findById(dog.getOwner().getId())
                        .orElse(null);
                existingDog.setOwner(owner);
            } else {
                existingDog.setOwner(null);
            }

            dogRepository.save(existingDog);

        } else {
            // CREATE new dog
            if (dog.getOwner() != null && dog.getOwner().getId() != null) {
                Owner owner = ownerRepository.findById(dog.getOwner().getId())
                        .orElse(null);
                dog.setOwner(owner);
            }

            dogRepository.save(dog);
        }

        return "redirect:/dogs";
    }

    // Edit dog
    @GetMapping("/edit/{id}")
    public String editDog(@PathVariable Long id, Model model) {

        Dog dog = dogRepository.findById(id)
                .orElse(null);

        if (dog == null) {
            return "redirect:/dogs";
        }

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