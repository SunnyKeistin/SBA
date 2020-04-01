package com.ibm.sba.mapper;

import com.ibm.sba.entity.Course;
import com.ibm.sba.entity.CourseComment;
import com.ibm.sba.model.MentorFilter;
import com.ibm.sba.utils.SimpleSelectInExtendedLanguageDriver;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Select({"<script>",
            "SELECT C.COURSE_ID, C.USER_ID, C.MENTOR_ID, C.COURSE_NAME, C.COURSE_DESCRIPTION, C.START_DATE, ",
            "C.END_DATE,C.FEE, C.STATUS, C.PERCENT FROM COURSE C WHERE C.SKILL_ID = #{skillId} ",
            " AND C.USER_ID IS NULL AND C.STATUS = 1",
            "<if test='startTimeSlot != null'> AND DATE_FORMAT(C.START_DATE, '%Y%m%d%H0000') &lt;= #{startTimeSlot} </if>",
            "<if test='endTimeSlot != null'> AND DATE_FORMAT(C.END_DATE, '%Y%m%d%H0000') &gt;= #{endTimeSlot} </if>",
            "</script>"})
    List<Course> getAvailableCoursesBySkill(MentorFilter filter);

    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    @Select("SELECT * FROM COURSE_COMMENT WHERE MENTOR_ID IN (#{mentorIds})")
    List<CourseComment> getCourseCommentsForMentors(@Param("mentorIds") List<Long> mentorIds);
}
