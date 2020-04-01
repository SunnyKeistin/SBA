package com.ibm.sba.mapper;

import com.ibm.sba.entity.MentorSkill;
import com.ibm.sba.entity.Skill;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MentorMapper {
    @Insert("INSERT INTO MENTOR_SKILLS(USER_ID,SKILL_ID,YEARS) "
            + "VALUES(#{userId}, #{skillId}, #{years}) ON DUPLICATE KEY UPDATE YEARS=VALUES(YEARS);")
    void saveMentorSkill(MentorSkill mentorSkill);

    @Delete("DELETE FROM MENTOR_SKILLS WHERE USER_ID = #{userId} AND SKILL_ID=#{skillId}")
    void deleteMentorSkill(MentorSkill mentorSkill);

    @Delete("DELETE FROM MENTOR_SKILLS WHERE USER_ID = #{mentorId}")
    void deleteMentorSkills(@Param("mentorId") Long mentorId);

    @Select("SELECT MS.USER_ID, MS.SKILL_ID, S.NAME AS SKILL_NAME, S.DESCRIPTION AS SKILL_DESCRIPTION, " +
            "MS.YEARS FROM MENTOR_SKILLS MS, SKILL S WHERE S.ID = MS.SKILL_ID AND MS.USER_ID = #{mentorId}")
    List<MentorSkill> getMentorSkills(@Param("mentorId") Long mentorId);

    @Select("SELECT MS.USER_ID, MS.SKILL_ID, S.NAME AS SKILL_NAME, S.DESCRIPTION AS SKILL_DESCRIPTION, MS.YEARS " +
            "FROM MENTOR_SKILLS MS, SKILL S WHERE S.ID = MS.SKILL_ID AND MS.SKILL_ID = #{skillId}")
    List<MentorSkill> getMentorsBySkill(@Param("skillId") Long skillId);
}
