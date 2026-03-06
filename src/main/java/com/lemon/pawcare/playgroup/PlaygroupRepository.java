package com.lemon.pawcare.playgroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaygroupRepository extends JpaRepository<Playgroup, Long> {

    List<Playgroup> findByActivityType(String activityType);

    List<Playgroup> findByGroupName(String groupName);

    List<Playgroup> findBySizeLimitGreaterThan(int sizeLimit);

}