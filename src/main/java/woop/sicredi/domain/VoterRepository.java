package woop.sicredi.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VoterRepository extends MongoRepository<Voter, String> {

	Voter findByCpf(String cpf);
}
