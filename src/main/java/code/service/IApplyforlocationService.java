package code.service;

import code.domain.Applyforlocation;

import java.util.List;

public interface IApplyforlocationService {

	//保存递交简历记录
	public void saveRecording(Applyforlocation applyforlocation);

	//查找一个人对应的所有提交简历的id
	public List<Applyforlocation> findByAaid(Integer aaid);

	//查找一个职位对应的所有提交简历的人id
	public List<Applyforlocation> findByApid(Integer apid);

	//查找某个人投递某个简历的文件的名字
	public Applyforlocation findByPAid(Integer apid,Integer aaid);

	//已经通过的简历
	public List<Applyforlocation> PositionHavePass(Integer apid);

	//尚未处理的简历
	public List<Applyforlocation> PositionNoHavePass(Integer apid);

	//被拒绝的简历
	public List<Applyforlocation> PositionNoPass(Integer apid);
}
