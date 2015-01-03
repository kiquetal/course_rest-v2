package py.com.edge.service;

import java.util.List;

import py.com.edge.models.Skill;
import py.com.edge.models.User;

public interface UsersServicesEdge {
public List<User> getAllUsers();
public void saveNewUserSkills(User u,String s) throws Exception;
public User findUserById(int id) throws Exception;
public boolean hasUserSkill(String name,User u);
public User UserFindUserByName(String name) throws Exception;
public List<Skill> getAllSkillsByUser(User u) throws Exception;
}
