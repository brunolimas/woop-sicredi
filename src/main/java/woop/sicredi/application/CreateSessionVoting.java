package woop.sicredi.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import woop.sicredi.domain.Schedule;
import woop.sicredi.domain.ScheduleRepository;
import woop.sicredi.domain.SessionVoting;
import woop.sicredi.domain.exception.BusinessException;

@Service
public class CreateSessionVoting {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public void execute(String idSchedule, SessionVoting sessionVoting) {
        Schedule schedule = scheduleRepository.findById(idSchedule)
                        .orElseThrow(() -> new BusinessException("Não foi encontrado a pauta " + idSchedule));

        schedule.setVoting(sessionVoting);
        scheduleRepository.save(schedule);
    }
}
