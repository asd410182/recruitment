package code.dao;

import code.domain.Applyforlocation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;


import java.util.List;

public interface IApplyforlocationDao {

	@Insert("insert into apply_for_location (aaid,apid,asubmitime,astatus,afilepath)values(#{aaid},#{apid},#{asubmitime},#{astatus},#{afilepath})")
	public void saveRecording(Applyforlocation applyforlocation);

	//查找一个人对应的所有提交简历
	@Select("select * from apply_for_location where aaid = #{aaid}")
	public List<Applyforlocation> findByAaid(Integer aaid);

	//查找一个职位对应的所有提交简历的人
	@Select("select * from apply_for_location where apid = #{apid}")
	public List<Applyforlocation> findByApid(Integer apid);

	//查找某个人投递某个简历
	@Select("select * from apply_for_location where apid = #{arg0} and aaid = #{arg1}")
	public Applyforlocation findByPAid(Integer apid,Integer aaid);

	//已经通过的简历
	@Select("select * from apply_for_location where apid = #{apid} and astatus ='等待面试'")
	public List<Applyforlocation> PositionHavePass(Integer apid);

	//尚未处理的简历
	@Select("select * from apply_for_location where apid = #{apid} and astatus ='未处理'")
	public List<Applyforlocation> PositionNoHavePass(Integer apid);

	//被拒绝的简历
	@Select("select * from apply_for_location where apid = #{apid} and astatus ='已拒绝'")
	public List<Applyforlocation> PositionNoPass(Integer apid);

}
