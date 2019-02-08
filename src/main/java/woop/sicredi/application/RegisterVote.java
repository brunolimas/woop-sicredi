package woop.sicredi.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import woop.sicredi.domain.Schedule;
import woop.sicredi.domain.ScheduleRepository;
import woop.sicredi.domain.Vote;
import woop.sicredi.domain.VoterRepository;
import woop.sicredi.domain.exception.BusinessException;

@Service
public class RegisterVote {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private VoterRepository voterRepository;

    public void execute(String idSchedule, Vote vote) {
        Schedule schedule = scheduleRepository.findById(idSchedule)
                        .orElseThrow(() -> new BusinessException("Não foi encontrado a pauta " + idSchedule));

        String idVoter = vote.getIdVoter();
        voterRepository.findById(idVoter)
                        .orElseThrow(() -> new BusinessException("Não foi encontrado o eleitor " + idVoter));

        schedule.registerVote(vote);
        scheduleRepository.save(schedule);
    }
}
