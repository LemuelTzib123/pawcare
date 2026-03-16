package com.lemon.pawcare.dog;

import com.lemon.pawcare.owner.Owner;
import com.lemon.pawcare.owner.OwnerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dogs")
public class DogController {

    private final DogRepository dogRepository;
    private final OwnerRepository ownerRepository;

    public DogController(DogRepository dogRepository, OwnerRepository ownerRepository) {
        this.dogRepository = dogRepository;
        this.ownerRepository = ownerRepository;
    }

    // Get all dogs
    @GetMapping
    public List<Dog> getAllDogs() {
        return dogRepository.findAll();
    }

    // Get dog by ID
    @GetMapping("/{id}")
    public ResponseEntity<Dog> getDogById(@PathVariable Long id) {
        return dogRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a dog and optionally assign an owner
    @PostMapping
    public ResponseEntity<Dog> createDog(
            @RequestBody Dog dog,
            @RequestParam(required = false) Long ownerId) {

        if (ownerId != null) {
            Owner owner = ownerRepository.findById(ownerId)
                    .orElseThrow(() -> new RuntimeException("Owner not found"));
            dog.setOwner(owner);
        }

        Dog savedDog = dogRepository.save(dog);
        return ResponseEntity.ok(savedDog);
    }

    // Update dog information and optionally change owner
    @PutMapping("/{id}")
    public ResponseEntity<Dog> updateDog(
            @PathVariable Long id,
            @RequestBody Dog updatedDog,
            @RequestParam(required = false) Long ownerId) {

        return dogRepository.findById(id)
                .map(dog -> {

                    dog.setName(updatedDog.getName());
                    dog.setBreed(updatedDog.getBreed());
                    dog.setAge(updatedDog.getAge());
                    dog.setVaccinated(updatedDog.isVaccinated());

                    if (ownerId != null) {
                        Owner owner = ownerRepository.findById(ownerId)
                                .orElseThrow(() -> new RuntimeException("Owner not found"));
                        dog.setOwner(owner);
                    }

                    Dog savedDog = dogRepository.save(dog);
                    return ResponseEntity.ok(savedDog);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a dog
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDog(@PathVariable Long id) {

        if (!dogRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        dogRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}