package org.example.ssiach18ex1.controller;

import lombok.RequiredArgsConstructor;
import org.example.ssiach18ex1.entitiy.Workout;
import org.example.ssiach18ex1.service.WorkoutService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping("/")
    public void add(@RequestBody Workout workout) {
        workoutService.saveWorkout(workout);
    }

    @GetMapping("/")
    public List<Workout> findAll() {
        return workoutService.findWorkouts();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
    }

}
