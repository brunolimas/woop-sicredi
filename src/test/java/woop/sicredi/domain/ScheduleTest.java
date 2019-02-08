package woop.sicredi.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import woop.sicredi.domain.exception.BusinessException;

public class ScheduleTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void registerVote() {
        Schedule schedule = new Schedule("Pauta com votação em andamento");
        schedule.setVoting(new SessionVoting());
        schedule.registerVote(new Vote("", YesOrNo.YES));

        assertEquals(1, schedule.getVoting().getVotesPositive());
    }

    @Test
    public void noPermissionCreateSessionVotingToScheduleWhenAnotherSessionAlreadyProgress() {
        expectedException.expectMessage("Já existe uma sessão de votação em andamento para esta pauta");
        expectedException.expect(BusinessException.class);

        Schedule schedule = new Schedule("Pauta com votacao em andamento");
        schedule.setVoting(new SessionVoting());

        schedule.setVoting(new SessionVoting());
    }

    @Test
    public void noPermissionRegisterVoteWhenDoesNotExistOpenSessionVote() {
        expectedException.expectMessage("Ainda não existe uma sessão de votos para a pauta atual");
        expectedException.expect(BusinessException.class);

        Schedule schedule = new Schedule("Pauta sem sessão de votos");
        schedule.registerVote(new Vote("", YesOrNo.YES));
    }
}