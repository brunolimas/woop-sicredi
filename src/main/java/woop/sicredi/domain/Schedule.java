package woop.sicredi.domain;

import static java.util.Objects.requireNonNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import woop.sicredi.domain.exception.BusinessException;

@Document
public class Schedule {

    @Id
    private String id;
    private String title;
    private String description;
    private SessionVoting voting;

    @JsonCreator
    public Schedule(@JsonProperty(value = "title", required = true) String title) {
        requireNonNull(title, "titulo não pode ser null");
        this.title = title;
    }

    public void registerVote(Vote vote) {
        if (voting == null) {
            throw new BusinessException("Ainda não existe uma sessão de votos para a pauta atual");
        }
        voting.registerVote(vote);
    }
    
    public void setVoting(SessionVoting voting) {
    	if (this.voting != null && !this.voting.isFinish()) {
    		throw  new BusinessException("Já existe uma sessão de votação em andamento para esta pauta");
    	}
    	this.voting = voting;
    }
    
    public String getTitle() {
		return title;
	}
    
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
        return id;
    }

    public SessionVoting getVoting() {
        return voting;
    }
}
