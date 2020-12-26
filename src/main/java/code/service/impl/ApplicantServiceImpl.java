package code.service.impl;

import code.dao.IApplicantDao;
import code.domain.Applicant;
import code.service.IApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("IApplicantService")
public class ApplicantServiceImpl implements IApplicantService {

	@Autowired
	private IApplicantDao applicantDao;

	//更新应聘者信息
	public void updateApplicant(Applicant applicant){
		applicantDao.updateApplicant(applicant);
	}

	//根据id查询应聘者信息
	public Applicant findById(Integer aid){
		return applicantDao.findById(aid);
	}

	//查询所有应聘者信息
	public List<Applicant> findAllApplicant(){
		return applicantDao.findAllApplicant();
	}

}
