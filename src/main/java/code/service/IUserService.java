package code.service;

import code.domain.User;

public interface IUserService {
	//保存
	public void save(User user);

	//    用户账号密码匹配
	public Integer login(String account, String apassword);

	//    根据名称查询用户id
	public Integer findByAccount(String account);

	//    根据id查询身份
	public String findRoleById(Integer uid);
}
