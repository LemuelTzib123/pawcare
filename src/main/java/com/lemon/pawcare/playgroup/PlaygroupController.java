package com.lemon.pawcare.playgroup;

import com.lemon.pawcare.dog.Dog;
import com.lemon.pawcare.dog.DogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playgroups")
public class PlaygroupController {

    private final PlaygroupRepository playgroupRepository;
    private final DogRepository dogRepository;

    public PlaygroupController(PlaygroupRepository playgroupRepository,
                               DogRepository dogRepository) {
        this.playgroupRepository = playgroupRepository;
        this.dogRepository = dogRepository;
    }

    @GetMapping
    public List<Playgroup> getAllPlaygroups() {
        return playgroupRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playgroup> getPlaygroupById(@PathVariable Long id) {
        return playgroupRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Playgroup> createPlaygroup(
            @RequestBody Playgroup playgroup) {

        Playgroup saved = playgroupRepository.save(playgroup);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playgroup> updatePlaygroup(
            @PathVariable Long id,
            @RequestBody Playgroup updatedPlaygroup) {

        return playgroupRepository.findById(id)
                .map(playgroup -> {
                    playgroup.setGroupName(updatedPlaygroup.getGroupName());
                    playgroup.setActivityType(updatedPlaygroup.getActivityType());
                    playgroup.setSizeLimit(updatedPlaygroup.getSizeLimit());
                    Playgroup saved = playgroupRepository.save(playgroup);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaygroup(@PathVariable Long id) {

        if (!playgroupRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        playgroupRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{playgroupId}/dogs/{dogId}")
    public ResponseEntity<Playgroup> addDogToPlaygroup(
            @PathVariable Long playgroupId,
            @PathVariable Long dogId) {

        Playgroup playgroup = playgroupRepository.findById(playgroupId)
                .orElseThrow(() -> new RuntimeException("Playgroup not found"));

        Dog dog = dogRepository.findById(dogId)
                .orElseThrow(() -> new RuntimeException("Dog not found"));

        playgroup.getDogs().add(dog);
        dog.getPlaygroups().add(playgroup);

        playgroupRepository.save(playgroup);

        return ResponseEntity.ok(playgroup);
    }
}