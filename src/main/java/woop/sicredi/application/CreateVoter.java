package woop.sicredi.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import woop.sicredi.domain.Voter;
import woop.sicredi.domain.VoterRepository;
import woop.sicredi.domain.exception.BusinessException;

@Service
public class CreateVoter {

    @Autowired
    private VoterRepository voterRepository;

    public void execute(Voter voter) {
        Voter voterEqualsCpf = voterRepository.findByCpf(voter.getCpf());
        if (voterEqualsCpf != null) {
            throw new BusinessException("Já existe um eleitor com o cpf " + voter.getCpf());
        }
        voterRepository.save(voter);
    }
}
