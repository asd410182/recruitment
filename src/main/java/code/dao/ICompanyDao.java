package code.dao;

import code.domain.Company;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ICompanyDao{

	//更新公司信息
	@Update("update company set cname=#{cname}, cintroduction=#{cintroduction},caddress=#{caddress},cphone=#{cphone},cemail=#{cemail} where cid = #{cid}")
	public void updateCompany(Company company);

	//根据id查询公司信息
	@Select("select * from company where cid =#{cid}")
	public Company findByCid(Integer cid);

	//查询所有公司
	@Select("select * from company")
	public List<Company> findAllCompany();

	//    根据名称模糊查询公司信息
	@Select("select * from company where cname like #{name}")
	public List<Company> findByName(String name);

	//查找某个人投递某个简历的文件的名字
	@Select("select count(*) from position where pcid = #{cid}")
	public Integer countById(Integer cid);

}
