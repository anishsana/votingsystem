package com.anishsana.portfolio.votingsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="citizens")
@Entity
public class Citizen {

	@Id
	@Column(name="id")
	private long id;
	
	@Column(name="citizen_name")
	private String name;
	
	@Column(name="has_voted")
	private boolean hasVoted;
	
	public Citizen() {}
	
	public Citizen(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean getHasVoted() {
		return hasVoted;
	}

	public void setHasVoted(boolean hasVoted) {
		this.hasVoted = hasVoted;
	}
	
}
