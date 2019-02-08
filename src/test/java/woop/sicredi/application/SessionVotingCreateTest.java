package woop.sicredi.application;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import woop.sicredi.domain.Schedule;
import woop.sicredi.domain.ScheduleRepository;
import woop.sicredi.domain.SessionVoting;
import woop.sicredi.domain.exception.BusinessException;

@RunWith(MockitoJUnitRunner.class)
public class SessionVotingCreateTest {

    @InjectMocks
    private CreateSessionVoting createSessionVoting;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void startNewSessionVotingToSchedule() {
        when(scheduleRepository.findById(any()))
                        .thenReturn(Optional.of(new Schedule("Nova Pauta")));

        SessionVoting sessionVoting = new SessionVoting();
        createSessionVoting.execute("5bc358979a8f4903041402fa", sessionVoting);

        ArgumentCaptor<Schedule> argumentCaptor = ArgumentCaptor.forClass(Schedule.class);
        verify(scheduleRepository).save(argumentCaptor.capture());

        Schedule schedule = argumentCaptor.getValue();
        assertNotNull(schedule.getVoting());
    }

    @Test
    public void noStartVotingToScheduleInvalid() {
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("Não foi encontrado a pauta 5bc358979a8f4903041402fa");

        createSessionVoting.execute("5bc358979a8f4903041402fa", new SessionVoting());
    }
}