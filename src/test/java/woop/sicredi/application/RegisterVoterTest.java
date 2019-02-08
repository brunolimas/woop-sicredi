package woop.sicredi.application;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import woop.sicredi.domain.Schedule;
import woop.sicredi.domain.ScheduleRepository;
import woop.sicredi.domain.SessionVoting;
import woop.sicredi.domain.Vote;
import woop.sicredi.domain.Voter;
import woop.sicredi.domain.VoterRepository;
import woop.sicredi.domain.YesOrNo;
import woop.sicredi.domain.exception.BusinessException;

@RunWith(MockitoJUnitRunner.class)
public class RegisterVoterTest {

    @InjectMocks
    private RegisterVote registerVote;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private VoterRepository voterRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void RegisterVote() {
        Schedule schedule = new Schedule("Nova pauta");
        schedule.setVoting(new SessionVoting());
        when(scheduleRepository.findById(any()))
                        .thenReturn(Optional.of(schedule));
        when(voterRepository.findById(any()))
                        .thenReturn(Optional.of(new Voter("", "")));

        Vote vote = new Vote("5bc3c5849a8462393c27b20f", YesOrNo.YES);
        registerVote.execute("5bc3c5949a8462393c27b210", vote);

        assertEquals(1, schedule.getVoting().getVotesPositive());
    }

    @Test 
    public void noPermissionRegisterVoteScheduleInvalid() {
        expectedException.expectMessage("Não foi encontrado a pauta 5bc3c5949a8462393c27b210");
        expectedException.expect(BusinessException.class);

        registerVote.execute("5bc3c5949a8462393c27b210", new Vote("5bc3c5849a8462393c27b20f", YesOrNo.YES));
    }

    @Test 
    public void noPermissionRegisterVoteInVoterInvalid() {
        expectedException.expectMessage("Não foi encontrado o eleitor 5bc3c5849a8462393c27b20f");
        expectedException.expect(BusinessException.class);

        when(scheduleRepository.findById(any()))
                        .thenReturn(Optional.of(new Schedule("")));

        registerVote.execute("5bc3c5949a8462393c27b210", new Vote("5bc3c5849a8462393c27b20f", YesOrNo.YES));
    }

}