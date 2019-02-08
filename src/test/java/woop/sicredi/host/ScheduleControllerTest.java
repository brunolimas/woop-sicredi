package woop.sicredi.host;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import woop.sicredi.application.CreateSessionVoting;
import woop.sicredi.application.RegisterVote;
import woop.sicredi.controller.ScheduleController;
import woop.sicredi.domain.Schedule;
import woop.sicredi.domain.ScheduleRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @MockBean
    private CreateSessionVoting createSessionVoting;

    @MockBean
    private RegisterVote registerVote;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void createShedule() throws Exception {
        String content = "{\"title\": \"Novo caso de teste\", \"description\":\"Criando uma pauta\"}";
        mvc.perform(post("/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isCreated());
    }

    @Test
    public void noPermissionCreateScheduleTitleNull() throws Exception {
        String content = "{\"description\":\"Teste apenas com descrição\"}";
        mvc.perform(post("/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isBadRequest());
    }

    @Test 
    public void shouldPermissionSessionCreateVotingToFinishDate() throws Exception {
        String content = "{\"start\": \"2018-10-14T23:03:49.568Z\"}";
        mvc.perform(post("/should/5bc3c5849a8462393c27b20f/session-voting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isCreated());
    }

    @Test
    public void registerVote() throws Exception {
        String content = "{\"idVoter\": \"5bc3c5849a8462393c27b20f\", \"vote\": \"YES\"}";
        mvc.perform(post("/schedule/5bc3c5849a8462393c27b20f/voter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isCreated());
    }

    @Test
    public void returnOkShouldHaveShedule() throws Exception {
        when(scheduleRepository.findById(any()))
                        .thenReturn(Optional.of(new Schedule("")));

        mvc.perform(get("/schedule/5bc3c5849a8462393c27b20f")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test 
    public void ReturnNullContentShouldNotExistShedule() throws Exception {
        mvc.perform(get("/schedule/5bc3c5849a8462393c27b20f")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
    }

    @Test 
    public void returnOkSchedulesWithPagination() throws Exception {
        mvc.perform(get("/schedule")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}
