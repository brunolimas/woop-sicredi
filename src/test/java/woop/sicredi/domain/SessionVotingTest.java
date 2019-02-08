package woop.sicredi.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import woop.sicredi.domain.exception.BusinessException;

public class SessionVotingTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void finishSessionVotingAfterTimeToExpired() {
        SessionVoting sessionVoting = new SessionVoting (LocalDateTime.MIN, LocalDateTime.MIN);
        assertTrue(sessionVoting .isFinish());
    }

    @Test
    public void shouldLastOneMinuteDefault() {
        SessionVoting sessionVoting = new SessionVoting(LocalDateTime.MIN);
        assertEquals(LocalDateTime.MIN.plusMinutes(1), sessionVoting.getEnd());
    }

    @Test
    public void registerPositiveVote() {
        Vote votePositive = new Vote("5bc3c5849a8462393c27b20f", YesOrNo.YES);
        SessionVoting sessionVoting = new SessionVoting();
        sessionVoting.registerVote(votePositive);
        assertEquals(1, sessionVoting.getVotesPositive());
        assertEquals(0, sessionVoting.getVotesNegative());
    }

    @Test
    public void registerNegativeVote() {
        Vote voteNegative = new Vote("5bc3c5849a8462393c27b20f", YesOrNo.NO);
        SessionVoting sessionVoting = new SessionVoting();
        sessionVoting.registerVote(voteNegative);
        assertEquals(1, sessionVoting.getVotesPositive());
        assertEquals(0, sessionVoting.getVotesNegative());

    }

    @Test
    public void shoulNotAllowEvenVotersToVoteTwice() {
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("O eleitor já votou");

        String idVoter = "5bc3c5849a8462393c27b20f";
        Vote votePositive = new Vote(idVoter, YesOrNo.YES);
        Vote voteNegative = new Vote(idVoter, YesOrNo.NO);
        SessionVoting sessionVoting = new SessionVoting();
        sessionVoting.registerVote(votePositive);
        sessionVoting.registerVote(voteNegative);
    }

    @Test
    public void shouldNotAllowNullVotes() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("voto não pode ser null");

        SessionVoting sessionVoting = new SessionVoting();
        sessionVoting.registerVote(null);
    }

    @Test 
    public void noPermissionVoteRegisterShouldSessionFinish() {
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("A sessão de votos já está finalizada,"
        		+ " não é mais possivel registrar novos votos");

        Vote votePositive = new Vote("5bc3c5849a8462393c27b20f", YesOrNo.YES);
        SessionVoting sessionVoting = new SessionVoting(LocalDateTime.MIN, LocalDateTime.MIN);
        sessionVoting.registerVote(votePositive);
    }
}