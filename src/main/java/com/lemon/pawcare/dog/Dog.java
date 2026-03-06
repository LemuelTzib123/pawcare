package com.lemon.pawcare.dog;

import com.lemon.pawcare.owner.Owner;
import com.lemon.pawcare.playgroup.Playgroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dogs")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String breed;

    private int age;

    private boolean vaccinated;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToMany
    @JoinTable(
            name = "dog_playgroups",
            joinColumns = @JoinColumn(name = "dog_id"),
            inverseJoinColumns = @JoinColumn(name = "playgroup_id")
    )
    @JsonIgnore
    private List<Playgroup> playgroups = new ArrayList<>();


    public Dog() {
    }

    public Dog(String name, String breed, int age, boolean vaccinated) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.vaccinated = vaccinated;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Playgroup> getPlaygroups() {
        return playgroups;
    }

    public void setPlaygroups(List<Playgroup> playgroups) {
        this.playgroups = playgroups;
    }


    public void addPlaygroup(Playgroup playgroup) {
        this.playgroups.add(playgroup);
        playgroup.getDogs().add(this);
    }

    public void removePlaygroup(Playgroup playgroup) {
        this.playgroups.remove(playgroup);
        playgroup.getDogs().remove(this);
    }
}