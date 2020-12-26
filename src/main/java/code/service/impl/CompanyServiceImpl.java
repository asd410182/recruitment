package code.service.impl;

import code.dao.ICompanyDao;
import code.domain.Company;
import code.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ICompanyService")
public class CompanyServiceImpl implements ICompanyService {

	@Autowired
	private ICompanyDao companyDao;

	//更新公司信息
	public void updateCompany(Company company){
		companyDao.updateCompany(company);
	}

	//根据id查询公司信息
	public Company findByCid(Integer cid){
		return companyDao.findByCid(cid);
	}

	//查询所有公司
	public List<Company> findAllCompany(){
		return companyDao.findAllCompany();
	}

	//    根据名称模糊查询公司信息
	public List<Company> findByName(String name){
		return companyDao.findByName(name);
	}

	//查找公司发布简历的数量
	public Integer countPositionByCid(Integer cid){
		return companyDao.countById(cid);
	}
}
