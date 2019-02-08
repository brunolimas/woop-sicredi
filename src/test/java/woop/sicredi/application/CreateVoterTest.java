package woop.sicredi.application;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import woop.sicredi.domain.Voter;
import woop.sicredi.domain.VoterRepository;
import woop.sicredi.domain.exception.BusinessException;

@RunWith(MockitoJUnitRunner.class)
public class CreateVoterTest {

    @InjectMocks
    private CreateVoter createVoter;

    @Mock
    private VoterRepository voterRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    
    @Test
    //Criando um eleitor.
    public void createVoter() {
        Voter voter = new Voter("Woop Sicredi", "123456789");
        createVoter.execute(voter);

        ArgumentCaptor<Voter> argumentCaptor = ArgumentCaptor.forClass(Voter.class);
        verify(voterRepository).save(argumentCaptor.capture());

        Voter voterSave = argumentCaptor.getValue();
        assertEquals(voter, voterSave);
    }

    @Test
    //Não permite criar eleitor com mesmo cpf de outro eleitor.
    public void doesNotAllowToCreateVoterWithSameCpfOfAnotherVoter() {
        expectedException.expect(BusinessException.class);
        expectedException.expectMessage("Já existe um eleitor com o cpf 123456789");

        when(voterRepository.findByCpf(any()))
                        .thenReturn(new Voter("Woop Sicredi", "123456789"));

        Voter voter = new Voter("Sicredi Woop", "123456789");
        createVoter.execute(voter);
    }
}