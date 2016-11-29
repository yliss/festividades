package com.prodigious.festivitiesapp.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the FESTIVITIE database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Festivitie.findAll", query="SELECT f FROM Festivitie f"),
	@NamedQuery(name="Festivitie.getByName", query="SELECT f FROM Festivitie f where f.fteName=:name"),
	@NamedQuery(name="Festivitie.getByPlace", query="SELECT f FROM Festivitie f where f.ftePlace=:place"),
	@NamedQuery(name="Festivitie.getByStartDate", query="SELECT f FROM Festivitie f where f.fteStart=:start"),
	@NamedQuery(name="Festivitie.getByRange", query="SELECT f FROM Festivitie f where f.fteStart BETWEEN :startDate AND :endDate AND f.fteEnd BETWEEN :startDate AND :endDate")
})
public class Festivitie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="FTE_NAME")
	private String fteName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FTE_END")
	private Date fteEnd;

	@Column(name="FTE_PLACE")
	private String ftePlace;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FTE_START")
	private Date fteStart;

	public Festivitie() {
	}

	public String getFteName() {
		return this.fteName;
	}

	public void setFteName(String fteName) {
		this.fteName = fteName;
	}

	public Date getFteEnd() {
		return this.fteEnd;
	}

	public void setFteEnd(Date fteEnd) {
		this.fteEnd = fteEnd;
	}

	public String getFtePlace() {
		return this.ftePlace;
	}

	public void setFtePlace(String ftePlace) {
		this.ftePlace = ftePlace;
	}

	public Date getFteStart() {
		return this.fteStart;
	}

	public void setFteStart(Date fteStart) {
		this.fteStart = fteStart;
	}

}