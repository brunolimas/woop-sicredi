package woop.sicredi.domain;

import static java.util.Objects.requireNonNull;

import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class Voter {

    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    @Pattern(regexp = "(\\d){11}")
    private String cpf;

    @JsonCreator
    public Voter(@JsonProperty(value = "name", required = true) String name,
                    @JsonProperty(value = "cpf", required = true) String cpf) {
        requireNonNull(name, "nome não pode ser null");
        requireNonNull(cpf, "cpf não pode ser null");
        this.name = name;
        this.cpf = cpf;
    }

    public String getId() {
        return id;
    }


    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
        return cpf;
    }
}
