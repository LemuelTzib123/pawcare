package com.lemon.pawcare.playgroup;

import com.lemon.pawcare.dog.Dog;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playgroups")
public class Playgroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String groupName;

    private String activityType;

    private int sizeLimit;

    @ManyToMany(mappedBy = "playgroups")
    @JsonIgnore
    private List<Dog> dogs = new ArrayList<>();

    public Playgroup() {
    }

    public Playgroup(String groupName, String activityType, int sizeLimit) {
        this.groupName = groupName;
        this.activityType = activityType;
        this.sizeLimit = sizeLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public int getSizeLimit() {
        return sizeLimit;
    }

    public void setSizeLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public List<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }

    public void addDog(Dog dog) {
        dogs.add(dog);
        dog.getPlaygroups().add(this);
    }

    public void removeDog(Dog dog) {
        dogs.remove(dog);
        dog.getPlaygroups().remove(this);
    }
}