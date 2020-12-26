package code.service.impl;

import code.dao.IUserDao;
import code.domain.Applicant;
import code.domain.User;
import code.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("IUserService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;

	//保存
	public void save(User user) {
		userDao.save(user);
	}

	//    用户账号密码匹配
	public Integer login(String account, String apassword) {
		return userDao.login(account, apassword);
	}

	//    根据名称查询用户id
	public Integer findByAccount(String account) {
		return userDao.findByAccount(account);
	}

	//    根据id查询身份
	public String findRoleById(Integer uid) {
		return userDao.findRoleById(uid);
	}
}