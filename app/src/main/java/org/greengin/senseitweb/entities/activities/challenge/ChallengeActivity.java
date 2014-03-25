package org.greengin.senseitweb.entities.activities.challenge;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.greengin.senseitweb.entities.projects.AbstractActivity;

@Entity
public class ChallengeActivity extends AbstractActivity {
	
	@OneToMany(orphanRemoval = true, cascade=CascadeType.ALL)
	Collection<ChallengeField> fields;
	
	@Basic
	Integer maxAnswers = 1;
	
	@Basic
	ChallengeActivityStage stage = ChallengeActivityStage.PROPOSAL;
	
	@OneToOne(orphanRemoval = true, cascade=CascadeType.ALL)
	ChallengeOutcome outcome = new ChallengeOutcome();
	
	
	public Collection<ChallengeField> getFields() {
		return fields;
	}

	public void setFields(Collection<ChallengeField> fields) {
		this.fields = fields;
	}

	public Integer getMaxAnswers() {
		return maxAnswers;
	}

	public void setMaxAnswers(Integer maxAnswers) {
		this.maxAnswers = maxAnswers;
	}

	public ChallengeActivityStage getStage() {
		return stage;
	}

	public void setStage(ChallengeActivityStage stage) {
		this.stage = stage;
	}

	public ChallengeOutcome getOutcome() {
		return outcome;
	}

	public void setOutcome(ChallengeOutcome outcome) {
		this.outcome = outcome;
	}	
	
}