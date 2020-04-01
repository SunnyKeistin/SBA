package com.ibm.sba.service;

import com.ibm.sba.entity.Skill;
import com.ibm.sba.mapper.SkillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {
	private static final Logger logger = LoggerFactory.getLogger(SkillService.class);
	
	@Autowired
	private SkillMapper skillMapper;


	public List<Skill> getSkills() {
		logger.debug("entering getSkills.");
		List<Skill> list = skillMapper.findAll();
		logger.debug("returned skill list from db is {}", list);
		return list;
	}
}
