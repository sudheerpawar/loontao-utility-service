package com.loontao.rpservice.service;

import com.loontao.rpservice.entity.Timing;
import com.loontao.rpservice.repository.TimingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimingService {

    private TimingRepository timingRepository;

    public TimingService(TimingRepository timingRepository) {
        this.timingRepository = timingRepository;
    }

    public List<Timing> getAllTimings() {
        return timingRepository.findAll();
    }

    public Optional<Timing> getTimingById(Long id) {
        return timingRepository.findById(id);
    }

    public Timing createTiming(Timing timing) {
        return timingRepository.save(timing);
    }

    public Timing updateTiming(Long id, Timing timingDetails) {
        return timingRepository.findById(id).map(timing -> {
            timing.setDay(timingDetails.getDay());
            timing.setStartTime(timingDetails.getStartTime());
            timing.setEndTime(timingDetails.getEndTime());
            timing.setContact(timingDetails.getContact());
            return timingRepository.save(timing);
        }).orElseThrow(() -> new RuntimeException("Timing not found"));
    }

    public void deleteTiming(Long id) {
        timingRepository.deleteById(id);
    }


}
