package code.service;

import code.domain.Company;

import java.util.List;

public interface ICompanyService {

	//更新公司信息
	public void updateCompany(Company company);

	//根据id查询公司信息
	public Company findByCid(Integer cid);

	//查询所有公司
	public List<Company> findAllCompany();

	//    根据名称模糊查询公司信息
	public List<Company> findByName(String name);

	//查找公司发布简历的数量
	public Integer countPositionByCid(Integer cid);
}
