package com.lemon.pawcare.dog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {

    List<Dog> findByBreed(String breed);
    List<Dog> findByVaccinated(boolean vaccinated);
    List<Dog> findByOwnerId(Long ownerId);

}