package code.dao;

import code.domain.Applyforlocation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.util.List;

public interface IApplyforlocationDao {

	//保存简历
	@Insert("insert into apply_for_location (aaid,apid,asubmitime,astatus,afilepath)values(#{aaid},#{apid},#{asubmitime},#{astatus},#{afilepath})")
	public Integer saveRecording(Applyforlocation applyforlocation);

	//通知面试
	@Update("update  apply_for_location set astatus = '等待面试' where apid = #{apid} and aaid = #{aaid}")
	public Integer acceptResume(Applyforlocation applyforlocation);

	//拒绝
	@Update("update  apply_for_location set astatus = '已拒绝' where apid = #{apid} and aaid = #{aaid}")
	public Integer refuseResume(Applyforlocation applyforlocation);

	//查找某个人投递某个简历
	@Select("select * from apply_for_location where apid = #{arg0} and aaid = #{arg1}")
	public Applyforlocation findByPAid(Integer apid,Integer aaid);


	//查找一个人对应的所有提交简历
	@Select("select * from apply_for_location where aaid = #{aaid}")
	public List<Applyforlocation> findByAaid(Integer aaid);

	//查找一个职位对应的所有提交简历的人
	@Select("select * from apply_for_location where apid = #{apid}")
	public List<Applyforlocation> findByApid(Integer apid);


	//已经通过的简历
	@Select("select * from apply_for_location where apid = #{apid} and astatus ='等待面试'")
	public List<Applyforlocation> PositionHavePass(Integer apid);

	//尚未处理的简历
	@Select("select * from apply_for_location where apid = #{apid} and astatus ='未处理'")
	public List<Applyforlocation> PositionNoHavePass(Integer apid);

	//被拒绝的简历
	@Select("select * from apply_for_location where apid = #{apid} and astatus ='已拒绝'")
	public List<Applyforlocation> PositionNoPass(Integer apid);

	//招聘者已经通过的简历
	@Select("select * from apply_for_location where aaid = #{aaid} and astatus ='等待面试'")
	public List<Applyforlocation> ResumeHasRefuse(Integer aaid);

	//招聘者尚未处理的简历
	@Select("select * from apply_for_location where apid = #{apid} and astatus ='未处理'")
	public List<Applyforlocation> ResumeWaitInterview(Integer aaid);

	//招聘者被拒绝的简历
	@Select("select * from apply_for_location where apid = #{aaid} and astatus ='已拒绝'")
	public List<Applyforlocation> ResumeHasAdopt(Integer aaid);

}
