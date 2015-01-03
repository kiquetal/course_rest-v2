package py.com.edge.service;

import java.util.List;

import py.com.edge.models.Skill;

public interface SkillsServiceEdge {

	public List<Skill> getAllSkills() throws Exception;
	public int getSkillOrInsertNew(String skillName) throws Exception;
	public Skill findSkillByName(String skillName) throws Exception;
	public Skill getSkillById(int id)throws Exception;
}
