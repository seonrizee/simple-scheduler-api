package io.github.seonrizee.scheduler.service;

import io.github.seonrizee.scheduler.dto.request.ScheduleCreateRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleDeleteRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleUpdateRequestDto;
import io.github.seonrizee.scheduler.dto.response.ScheduleResponseDto;
import io.github.seonrizee.scheduler.dto.response.SchedulesResponseDto;
import io.github.seonrizee.scheduler.entity.Schedule;
import io.github.seonrizee.scheduler.exception.InvalidPasswordException;
import io.github.seonrizee.scheduler.exception.ScheduleNotFoundException;
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

    @Override
    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleCreateRequestDto requestDto) {

        Schedule savedSchedule = scheduleRepository.save(new Schedule(requestDto));

        return new ScheduleResponseDto(savedSchedule);
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

        List<ScheduleResponseDto> scheduleResponseDtoList = schedules.stream()
                .map(ScheduleResponseDto::new)
                .toList();

        return new SchedulesResponseDto(scheduleResponseDtoList);
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleResponseDto findScheduleById(Long id) {

        Schedule foundSchedule = findScheduleByIdOrThrow(id);

        return new ScheduleResponseDto(foundSchedule, commentService.findCommentsByScheduleId(id));
    }

    @Override
    @Transactional
    public ScheduleResponseDto updateScheduleById(Long id, ScheduleUpdateRequestDto requestDto) {

        Schedule foundSchedule = findScheduleByIdOrThrow(id);

        if (!foundSchedule.getPassword().equals(requestDto.getPassword())) {
            throw new InvalidPasswordException();
        }

        foundSchedule.updateById(requestDto);

        scheduleRepository.saveAndFlush(foundSchedule);

        return new ScheduleResponseDto(foundSchedule);
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
