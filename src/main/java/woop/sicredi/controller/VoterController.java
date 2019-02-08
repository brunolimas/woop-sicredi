package woop.sicredi.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woop.sicredi.application.CreateVoter;
import woop.sicredi.domain.Voter;
import woop.sicredi.domain.VoterRepository;

@RestController
@RequestMapping("/voter")
public class VoterController {

    @Autowired
    private CreateVoter createVoter;

    @Autowired
    private VoterRepository voterRepository;

    @GetMapping
    public ResponseEntity<Collection<Voter>> getVoters() {
        List<Voter> all = voterRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createVoter(@Valid @RequestBody Voter voter) {
        createVoter.execute(voter);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
