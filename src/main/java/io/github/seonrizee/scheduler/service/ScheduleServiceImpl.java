package io.github.seonrizee.scheduler.service;

import io.github.seonrizee.scheduler.dto.request.ScheduleRequestDto;
import io.github.seonrizee.scheduler.dto.response.ScheduleResponseDto;
import io.github.seonrizee.scheduler.entity.Schedule;
import io.github.seonrizee.scheduler.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {

        Schedule savedSchedule = scheduleRepository.save(new Schedule(requestDto));

        return new ScheduleResponseDto(savedSchedule);
    }
}
