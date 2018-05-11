package com.anishsana.portfolio.votingsystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anishsana.portfolio.votingsystem.entity.Auditor;
import com.anishsana.portfolio.votingsystem.entity.Candidate;
import com.anishsana.portfolio.votingsystem.entity.Citizen;
import com.anishsana.portfolio.votingsystem.repositories.AuditorRepo;
import com.anishsana.portfolio.votingsystem.repositories.CandidateRepo;
import com.anishsana.portfolio.votingsystem.repositories.CitizenRepo;

@Controller
public class VotingController {
	
	public final Logger logger = Logger.getLogger(VotingController.class);

	@Autowired
	CitizenRepo citizenRepo;
	
	@Autowired
	CandidateRepo candidateRepo;
	
	@Autowired
	AuditorRepo auditorRepo;
	
	@RequestMapping("/")
	public String goToVote() {
		logger.info("Authenticating Voter");
		return "authenticateVote.html";
	}
	
	@RequestMapping("/authenticateVote")
	public String authenticateVote(@RequestParam String name, Model model, HttpSession session) {
		Citizen citizen = citizenRepo.findByName(name);
		logger.info("Found citizen: " + citizen.getName());
		
		if (citizen.getHasVoted() == false) {
			logger.info(citizen.getName() + " has not voted");
			List<Candidate> candidates = candidateRepo.findAll();
			model.addAttribute("candidates", candidates);
			session.setAttribute("citizen", citizen);
			logger.info("Re-routing " + citizen.getName() + " to voting booth");
			return "performVote.html";
		} else {
			logger.info(citizen.getName() + " has already voted");
			return "alreadyVoted.html";
		}
	}
	
	@RequestMapping("/voteFor")
	public String voteFor(@RequestParam long id, HttpSession session) {
		Citizen citizen = (Citizen) session.getAttribute("citizen");
		
		if (citizen.getHasVoted() == false) {		
			Candidate candidate = candidateRepo.findById(id);
			candidate.setNumberOfVotes(candidate.getNumberOfVotes() + 1);
			candidateRepo.save(candidate);
			citizen.setHasVoted(true);
			citizenRepo.save(citizen);
			session.removeAttribute("citizen");
			logger.info(citizen.getName() + " successfully voted for " + candidate.getName());
			return "voteSuccess.html";
		}
		logger.info(citizen.getName() + " has already voted");
		return "alreadyVoted.html";
	}
	
	@RequestMapping("/voteResults")
	public String getVoteCount(@RequestParam String name, @RequestParam String password, Model model) {
		Auditor auditor = auditorRepo.findByName(name);
		
		if (auditor != null && password.equals(auditor.getPassword())) {
			List<Candidate> candidates = candidateRepo.findAll();
			model.addAttribute("candidates", candidates);
			return "voteResults.html";
		} 
		
		return "authFail.html";
	}
}
