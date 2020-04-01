package com.ibm.sba.mapper;

import com.ibm.sba.entity.MentorSkill;
import com.ibm.sba.entity.Skill;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface SkillMapper {
    @Select("SELECT * FROM SKILL")
    List<Skill> findAll();
}
