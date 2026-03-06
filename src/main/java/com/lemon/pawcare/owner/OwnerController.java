package com.lemon.pawcare.owner;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerRepository ownerRepository;

    public OwnerController(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @GetMapping
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable Long id) {
        return ownerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        Owner savedOwner = ownerRepository.save(owner);
        return ResponseEntity.ok(savedOwner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Owner> updateOwner(
            @PathVariable Long id,
            @RequestBody Owner updatedOwner) {

        return ownerRepository.findById(id)
                .map(owner -> {
                    owner.setFirstName(updatedOwner.getFirstName());
                    owner.setLastName(updatedOwner.getLastName());
                    owner.setPhone(updatedOwner.getPhone());
                    owner.setEmail(updatedOwner.getEmail());
                    Owner saved = ownerRepository.save(owner);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {

        if (!ownerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        ownerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}