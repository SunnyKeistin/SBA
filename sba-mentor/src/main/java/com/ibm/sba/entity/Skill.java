package com.ibm.sba.entity;

public class Skill {
	
	public Skill() {
		
	}
	
	public Skill(String name, String description, Double baseFee) {
		this.name = name;
		this.description = description;
		this.baseFee = baseFee;
	}


    private Integer id;

    private String name;

	private String description;

	private Double baseFee;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getBaseFee() {
		return baseFee;
	}

	public void setBaseFee(Double baseFee) {
		this.baseFee = baseFee;
	}

	@Override
	public String toString() {
		return "Skill{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", baseFee=" + baseFee +
				'}';
	}
}