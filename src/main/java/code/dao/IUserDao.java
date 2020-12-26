package code.dao;

import code.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao {

	//	插入一个新用户
	@Insert("insert into all_user(uaccount,upassword,urole)values(#{phone},#{pwd},#{role})")
	public void save(User user);


	//   账号密码匹配
	@Select("select uid from all_user where uaccount = #{arg0} and upassword = #{arg1}")
	public Integer login(String account, String pwd);//多个参数的时候要用arg0

	//    根据名称查询用户id
	@Select("select uid from all_user  where uaccount = #{account}")
	public Integer findByAccount(String account);

	//    根据id查询身份
	@Select("select urole from all_user where uid = #{uid}")
	public String findRoleById(Integer uid);
}
