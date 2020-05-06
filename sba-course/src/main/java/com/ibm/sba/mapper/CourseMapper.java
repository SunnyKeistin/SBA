package com.ibm.sba.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ibm.sba.entity.Course;
import com.ibm.sba.entity.CourseComment;
import com.ibm.sba.model.MentorFilter;
import com.ibm.sba.utils.SimpleSelectInExtendedLanguageDriver;

@Mapper
public interface CourseMapper{

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
    List<Course> findAllByMentor(Long mentorId);

	List<Course> findAllByUser(Long userId);
	
	@Insert("INSERT INTO Course(COURSE_ID,USER_ID,MENTOR_ID, COURSE_NAME, COURSE_DESCRIPTION, START_DATE, END_DATE, FEE, STATUS,PERCENT) "
    		+ "VALUES(#{COURSE_ID}, #{USER_ID}, #{MENTOR_ID}, #{COURSE_NAME}, #{COURSE_DESCRIPTION}, #{START_DATE}, #{END_DATE}, #{FEE}, #{STATUS}, #{PERCENT})")
	public void save(Course course);

}
