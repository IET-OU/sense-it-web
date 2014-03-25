package org.greengin.senseitweb.controllers.activities.challenge;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.JsonView;
import org.greengin.senseitweb.entities.activities.challenge.ChallengeAnswer;
import org.greengin.senseitweb.json.mixins.Views;
import org.greengin.senseitweb.logic.project.challenge.ChallengeActivityActions;
import org.greengin.senseitweb.logic.voting.VoteCount;
import org.greengin.senseitweb.logic.voting.VoteRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/project/{projectId}/challenge/votes")
public class ChallengeVoteController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
	@JsonView({Views.VotableCount.class})
	public Collection<ChallengeAnswer> get(@PathVariable("projectId") Long projectId, HttpServletRequest request) {
		ChallengeActivityActions participant = new ChallengeActivityActions(projectId, request);
		Collection<ChallengeAnswer> answers = participant.getAnswersForParticipant();
		for (ChallengeAnswer a : answers) {
			a.selectVoteAuthor(participant.getCurrentUser());
		}
		return answers;
	}
	
	
    @RequestMapping(value = "/{answerId}", method = RequestMethod.POST)
    @ResponseBody
	@JsonView({Views.VotableCount.class})
	public VoteCount vote(@PathVariable("projectId") Long projectId, @PathVariable("answerId") Long answerId, VoteRequest voteData, HttpServletRequest request) {
		ChallengeActivityActions voter = new ChallengeActivityActions(projectId, request);
		
		return voter.vote(answerId, voteData);
	}
}