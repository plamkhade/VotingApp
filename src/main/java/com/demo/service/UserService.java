package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Candidate;
import com.demo.model.LoggedUser;
import com.demo.model.User;
import com.demo.model.VoteCount;
import com.demo.repositroy.CandidateRepositroy;
import com.demo.repositroy.UserRepositroy;

@Service
public class UserService {

	@Autowired
	private UserRepositroy userRepositroy;

	@Autowired
	private CandidateRepositroy candidateRepositroy;

	public boolean addUser(User user) {
		user.setRole("USER");
		User foundUser = this.userRepositroy.findByUsername(user.getUsername()).orElse(null);
		if (foundUser == null) {
			this.userRepositroy.save(user);
			return true;
		} else
			return false;
	}

	public User login(LoggedUser loggedUser) {
		User foundUser = this.userRepositroy
				.findByUsernameAndPassword(loggedUser.getUsername(), loggedUser.getPassword()).orElse(null);

		return foundUser;
	}

	public Candidate findByUserId(Integer id) {
		Candidate findVote = this.candidateRepositroy.findByUserId(id).orElse(null);
		return findVote;
	}

	public void addVote(Candidate candidate) {
		this.candidateRepositroy.save(candidate);
	}

	public List<VoteCount> findAllVote() {
		return this.candidateRepositroy.countVoteByCandidateName();
	}

}
