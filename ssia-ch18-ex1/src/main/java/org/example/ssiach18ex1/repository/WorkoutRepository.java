package org.example.ssiach18ex1.repository;

import org.example.ssiach18ex1.entitiy.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    // Security Context 에서 인증된 사용자 이름의 값을 가져오는 SpEL 식
    @Query("SELECT w FROM Workout w WHERE w.user = ?#{authentication.name}")
    List<Workout> findAllByUser();

}
