package io.github.seonrizee.scheduler.service;

import io.github.seonrizee.scheduler.dto.request.ScheduleCreateRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleDeleteRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleUpdateRequestDto;
import io.github.seonrizee.scheduler.dto.response.ScheduleResponseDto;
import io.github.seonrizee.scheduler.dto.response.SchedulesResponseDto;
import io.github.seonrizee.scheduler.entity.Schedule;
import io.github.seonrizee.scheduler.exception.InvalidPasswordException;
import io.github.seonrizee.scheduler.exception.ScheduleNotFoundException;
import io.github.seonrizee.scheduler.mapper.ScheduleMapper;
import io.github.seonrizee.scheduler.repository.ScheduleRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentService commentService;
    private final ScheduleMapper scheduleMapper;

    @Override
    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleCreateRequestDto requestDto) {

        Schedule savedSchedule = scheduleRepository.save(scheduleMapper.toEntity(requestDto));

        return scheduleMapper.toResponseDto(savedSchedule);
    }

    @Override
    @Transactional(readOnly = true)
    public SchedulesResponseDto findAllSchedules(Optional<String> username) {

        List<Schedule> schedules;

        if (username.isPresent()) {
            schedules = scheduleRepository.findByUsernameOrderByUpdatedAtDesc((username.get()));
        } else {
            schedules = scheduleRepository.findAllByOrderByUpdatedAtDesc();
        }

        return scheduleMapper.toSchedulesResponseDto(schedules);
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleResponseDto findScheduleById(Long id) {

        Schedule foundSchedule = findScheduleByIdOrThrow(id);

        return scheduleMapper.toResponseDto(foundSchedule, commentService.findCommentsByScheduleId(id));
    }

    @Override
    @Transactional
    public ScheduleResponseDto updateScheduleById(Long id, ScheduleUpdateRequestDto requestDto) {

        Schedule foundSchedule = findScheduleByIdOrThrow(id);

        if (!foundSchedule.getPassword().equals(requestDto.getPassword())) {
            throw new InvalidPasswordException();
        }

        foundSchedule.updateById(requestDto.getTitle(), requestDto.getUsername());

        scheduleRepository.saveAndFlush(foundSchedule);

        return scheduleMapper.toResponseDto(foundSchedule);
    }

    @Override
    @Transactional
    public void deleteScheduleById(Long id, ScheduleDeleteRequestDto requestDto) {

        Schedule foundSchedule = findScheduleByIdOrThrow(id);

        if (!foundSchedule.getPassword().equals(requestDto.getPassword())) {
            throw new InvalidPasswordException();
        }

        scheduleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Schedule findScheduleByIdOrThrow(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(ScheduleNotFoundException::new);
    }
}
