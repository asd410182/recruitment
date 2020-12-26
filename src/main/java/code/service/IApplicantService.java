package code.service;

import code.dao.IApplicantDao;
import code.domain.Applicant;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface IApplicantService {

	//更新应聘者信息
	public void updateApplicant(Applicant applicant);

	//根据id查询应聘者信息
	public Applicant findById(Integer aid);

	//查询所有应聘者信息
	public List<Applicant> findAllApplicant();

}

