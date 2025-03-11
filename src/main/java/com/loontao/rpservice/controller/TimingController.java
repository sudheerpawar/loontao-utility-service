package com.loontao.rpservice.controller;

import com.loontao.rpservice.entity.Timing;
import com.loontao.rpservice.exceptions.ResourceNotFoundException;
import com.loontao.rpservice.service.TimingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/timing")
public class TimingController {

    private final TimingService timingService;

    @Autowired
    public TimingController(TimingService timingService) {
        this.timingService = timingService;
    }

    @GetMapping
    public List<Timing> getAllTimings() {
        return timingService.getAllTimings();
    }

    @GetMapping("/{id}")
    public Timing getTimingById(@PathVariable Long id) {
        return timingService.getTimingById(id).orElseThrow(() -> new ResourceNotFoundException("Entry not found"));
    }

    @PostMapping
    public Timing createTiming(@RequestBody Timing timing) {
        return timingService.createTiming(timing);
    }

    @PutMapping("/{id}")
    public Timing updateTiming(@PathVariable Long id, @RequestBody Timing timingDetails) {
        return timingService.updateTiming(id, timingDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTiming(@PathVariable Long id) {
        timingService.deleteTiming(id);
    }
}
