package woop.sicredi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vote {

    private String idVoter;
    private YesOrNo vote;

    public Vote(@JsonProperty("idVoter") String idVoter, @JsonProperty("vote") YesOrNo vote) {
        this.idVoter = idVoter;
        this.vote = vote;
    }

	public String getIdVoter() {
		return idVoter;
	}

	public void setIdVoter(String idVoter) {
		this.idVoter = idVoter;
	}

	public YesOrNo getVote() {
		return vote;
	}

	public void setVote(YesOrNo vote) {
		this.vote = vote;
	}
}
