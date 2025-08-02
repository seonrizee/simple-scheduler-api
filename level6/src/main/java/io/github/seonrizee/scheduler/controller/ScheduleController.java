package io.github.seonrizee.scheduler.controller;

import io.github.seonrizee.scheduler.dto.request.PasswordRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleUpdateRequestDto;
import io.github.seonrizee.scheduler.dto.response.CommonDto;
import io.github.seonrizee.scheduler.dto.response.ScheduleResponseDto;
import io.github.seonrizee.scheduler.dto.response.SchedulesResponseDto;
import io.github.seonrizee.scheduler.service.ScheduleService;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<CommonDto<ScheduleResponseDto>> createSchedule(@RequestBody ScheduleRequestDto requestDto) {

        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto);
        return new ResponseEntity<>(new CommonDto<>(HttpStatus.CREATED, "success", responseDto),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CommonDto<SchedulesResponseDto>> getSchedules(
            @RequestParam(required = false) Optional<String> username) {

        SchedulesResponseDto responseDto = scheduleService.findAllSchedules(username);

        return new ResponseEntity<>(new CommonDto<>(HttpStatus.OK, "success", responseDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonDto<ScheduleResponseDto>> getSchedule(@PathVariable Long id) {

        ScheduleResponseDto responseDto = scheduleService.findScheduleById(id);

        return new ResponseEntity<>(new CommonDto<>(HttpStatus.OK, "success", responseDto), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonDto<ScheduleResponseDto>> updateSchedule(
            @RequestBody ScheduleUpdateRequestDto requestDto,
            @PathVariable Long id) {
        ScheduleResponseDto responseDto = scheduleService.updateSchedulebyId(id, requestDto);

        return new ResponseEntity<>(new CommonDto<>(HttpStatus.OK, "success", responseDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonDto<Void>> deleteSchedule(@RequestBody PasswordRequestDto requestDto,
                                                          @PathVariable Long id) {

        scheduleService.deleteScheduleById(id, requestDto);

        return new ResponseEntity<>(new CommonDto<>(HttpStatus.OK, "success", null), HttpStatus.OK);
    }
}
