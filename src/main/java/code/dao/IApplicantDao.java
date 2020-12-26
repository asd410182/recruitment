package code.dao;

import code.domain.Applicant;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IApplicantDao {

	//更新应聘者信息
	@Update("update applicant set aname =#{aname},aage=#{aage},aeducation=#{aeducation},ajobstatus=#{ajobstatus},aemail=#{aemail},aphone=#{aphone} where aid =#{aid}")
	public void updateApplicant(Applicant applicant);

	//根据id查询应聘者信息
	@Select("select * from applicant where aid = #{aid}")
	public Applicant findById(Integer aid);

	//查询所有应聘者信息
	@Select("select * from applicant")
	public List<Applicant> findAllApplicant();
}
