package woop.sicredi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import woop.sicredi.application.CreateSessionVoting;
import woop.sicredi.application.RegisterVote;
import woop.sicredi.domain.Schedule;
import woop.sicredi.domain.ScheduleRepository;
import woop.sicredi.domain.SessionVoting;
import woop.sicredi.domain.Vote;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CreateSessionVoting createSessionVoting;

    @Autowired
    private RegisterVote registerVote;

    @PostMapping
    public ResponseEntity createSchedule(@RequestBody Schedule schedule) {
        scheduleRepository.save(schedule);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Schedule>> getSchedule(@RequestParam(value = "page", defaultValue = "0") int page,
                    @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<Schedule> all = scheduleRepository.findAll(PageRequest.of(page, size));
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/{idSchedule}")
    public ResponseEntity<Schedule> getSchedule(@PathVariable("idSchedule") String idSchedule) {
        return scheduleRepository.findById(idSchedule)
                        .map(schedule -> new ResponseEntity<>(schedule, HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping("/{idSchedule}/session-voting")
    public ResponseEntity createSessionVoting(@PathVariable("idSchedule") String idSchedule,
                    @RequestBody SessionVoting sessionVoting ) {
    	createSessionVoting.execute(idSchedule, sessionVoting);
    	return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/{idSchedule}/votes")
    public ResponseEntity votes(@PathVariable("idSchedule") String idSchedule, @RequestBody Vote vote) {
        registerVote.execute(idSchedule, vote);
    	return new ResponseEntity(HttpStatus.CREATED);
    }
}
