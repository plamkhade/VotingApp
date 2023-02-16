package com.demo.repositroy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.Candidate;
import com.demo.model.VoteCount;

@Repository
public interface CandidateRepositroy extends CrudRepository<Candidate, Integer> {
	Optional<Candidate> findByUserId(Integer userId);

	@Query("SELECT new com.demo.model.VoteCount(v.candidateName, COUNT(v.candidateName)) "
			+ "FROM Candidate AS v GROUP BY v.candidateName")
	List<VoteCount> countVoteByCandidateName();
}
