package woop.sicredi.domain;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;

import woop.sicredi.domain.exception.BusinessException;

public class SessionVoting {

    private LocalDateTime start;
    private LocalDateTime end;
    private Collection<Vote> votes = new LinkedList<>();

    public SessionVoting() {
        this(LocalDateTime.now());
    }

	public SessionVoting(LocalDateTime start) {
        this.start = start;
        this.end = start.plusMinutes(1);
    }

    public SessionVoting(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public void registerVote(Vote vote) {
        requireNonNull(vote, "voto não pode ser null");
        if (voterIsVoted(vote.getIdVoter())) {
            throw new BusinessException("O eleitor já votou");
        }
        if (isFinish()) {
            throw new BusinessException("A sessão de votos já está finalizada, não é mais possivel registrar novos votos");
        }
        votes.add(vote);
    }

    private boolean voterIsVoted(String idVoter) {
        return votes.stream().anyMatch(vote -> vote.getIdVoter().equals(idVoter));
    }

    public long getVotesPositive() {
        return votes.stream().filter(vote -> vote.getVote() == YesOrNo.YES).count();
    }

    public long getVotesNegative() {
        return votes.stream().filter(vote -> vote.getVote() == YesOrNo.NO).count();
    }

    public boolean isFinish() {
        return LocalDateTime.now().isAfter(end);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}
