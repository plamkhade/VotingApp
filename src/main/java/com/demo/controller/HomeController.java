package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.model.Candidate;
import com.demo.model.LoggedUser;
import com.demo.model.User;
import com.demo.model.VoteCount;
import com.demo.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("loggeduser", new LoggedUser());
		return "home";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute LoggedUser loggedUser, Model model) {
		if (loggedUser.getUsername().equals("admin") && loggedUser.getPassword().equals("admin")) {
			List<VoteCount> votes = this.userService.findAllVote();
			model.addAttribute("votes", votes);
			return "adminpage";
		} else {
			User foundUser = this.userService.login(loggedUser);
			if (foundUser == null) {
				model.addAttribute("error", "Username or Password is invalid");
				model.addAttribute("loggeduser", new LoggedUser());
				return "login";
			} else {
				Candidate voter = new Candidate();
				voter.setUserId(foundUser.getId());
				model.addAttribute("voter", voter);
				return "votingPage";
			}
		}
	}

	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String addUser(@ModelAttribute User user, Model model) {
		boolean result = this.userService.addUser(user);
		if (result)
			return "redirect:/login";
		else {
			model.addAttribute("error", "User Already Exists");
			model.addAttribute("user", new User());
			return "register";
		}
	}

	@PostMapping("/voted/{userId}")
	public String voted(@ModelAttribute Candidate candidate, Model model, @PathVariable Integer userId) {
		Candidate findVote = this.userService.findByUserId(userId);

		if (findVote != null) {
			model.addAttribute("error", "You Already Voted");
			model.addAttribute("loggeduser", new LoggedUser());
			return "login";
		} else {
			this.userService.addVote(candidate);
			return "redirect:/";
		}
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/home")
	public String homes() {
		return "home";
	}

}
