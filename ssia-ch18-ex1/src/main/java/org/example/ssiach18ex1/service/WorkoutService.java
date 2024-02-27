package org.example.ssiach18ex1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ssiach18ex1.entitiy.Workout;
import org.example.ssiach18ex1.repository.WorkoutRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    // 사전 권한 부여로 운동 기록이 해당 사용자의 소유가 아니면 메서드가 호출되지 않게 한다.
    @PreAuthorize("#workout.user == authentication.name")
    public void saveWorkout(Workout workout) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(">> authentication.name : {}", username);
        log.info(">> workout.user : {}", workout.getUser());
        workoutRepository.save(workout);
    }

    // Repository 계층에 이미 필터링 적용
    public List<Workout> findWorkouts() {
        return workoutRepository.findAllByUser();
    }

    // End-Point 계층에 권한 부여를 적용
    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }

}
