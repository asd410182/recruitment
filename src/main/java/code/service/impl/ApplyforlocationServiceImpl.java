package code.service.impl;

import code.dao.IApplyforlocationDao;
import code.domain.Applyforlocation;
import code.service.IApplicantService;
import code.service.IApplyforlocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("IApplyforlocationService")
public class ApplyforlocationServiceImpl implements IApplyforlocationService {

	@Autowired
	private IApplyforlocationDao applyforlocationDao;
	//保存递交简历记录
	public void saveRecording(Applyforlocation applyforlocation){
		applyforlocationDao.saveRecording(applyforlocation);
	}

	//查找一个人对应的所有提交简历的id
	public List<Applyforlocation> findByAaid(Integer aaid){
		return applyforlocationDao.findByAaid(aaid);
	}

	//查找一个职位对应的所有提交简历的人id
	public List<Applyforlocation> findByApid(Integer apid){
		return applyforlocationDao.findByApid(apid);
	}

	//查找某个人投递某个简历的文件的名字
	public Applyforlocation findByPAid(Integer apid,Integer aaid){
		return applyforlocationDao.findByPAid(apid,aaid);
	}
	//已经通过的简历
	public List<Applyforlocation> PositionHavePass(Integer apid){
		return applyforlocationDao.PositionHavePass(apid);
	}

	//尚未处理的简历
	public List<Applyforlocation> PositionNoHavePass(Integer apid){
		return applyforlocationDao.PositionNoHavePass(apid);
	}

	//被拒绝的简历
	public List<Applyforlocation> PositionNoPass(Integer apid){
		return applyforlocationDao.PositionNoPass(apid);
	}
}
