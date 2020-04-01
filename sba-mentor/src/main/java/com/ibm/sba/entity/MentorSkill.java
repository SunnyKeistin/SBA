package com.ibm.sba.entity;


public class MentorSkill {

	public MentorSkill() {

	}

    private Long userId;

    private Integer skillId;

    private String skillName;

    private String skillDescription;

	private Integer years;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getSkillId() {
		return skillId;
	}

	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getSkillDescription() {
		return skillDescription;
	}

	public void setSkillDescription(String skillDescription) {
		this.skillDescription = skillDescription;
	}

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	@Override
	public String toString() {
		return "MentorSkill{" +
				"userId=" + userId +
				", skillId=" + skillId +
				", skillName='" + skillName + '\'' +
				", skillDescription='" + skillDescription + '\'' +
				", years='" + years + '\'' +
				'}';
	}
}