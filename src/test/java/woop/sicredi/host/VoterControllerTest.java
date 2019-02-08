package woop.sicredi.host;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import woop.sicredi.application.CreateVoter;
import woop.sicredi.controller.VoterController;
import woop.sicredi.domain.VoterRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(VoterController.class)
public class VoterControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CreateVoter createVoter;

    @MockBean
    private VoterRepository voterRepository;

    @Test
    public void createVoter() throws Exception {
        String content = "{\"name\":\"Sicredi De Woop Da Woop\", \"cpf\": \"23628031559\"}";
        mvc.perform(post("/voter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isCreated());
    }

    @Test
    public void noPermissionCreateVoterNameNull() throws Exception {
        String content = "{\"cpf\":\"23628031559\"}";
        mvc.perform(post("/voter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isBadRequest());
    }

    @Test
    public void noPermissionCreateVoterCpfNull() throws Exception {
        String content = "{\"name\":\"Sicredi De Woop Da Woop\"}";
        mvc.perform(post("/voter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isBadRequest());
    }

    @Test
    public void noPermissionCreateVoteCpfInvalid() throws Exception {
        String content = "{\"name\":\"Sicredi De Woop Da Woop\", \"cpf\":\"03877643080\"}";
        mvc.perform(post("/voter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isBadRequest());
    }

    @Test
    public void findAllVoter() throws Exception {
        mvc.perform(get("/voter")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

}