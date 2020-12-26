package code.service.impl;

import code.dao.IPositionDao;
import code.domain.Position;
import code.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("IPositionService")
public class PositionServiceImpl implements IPositionService {

	@Autowired
	private IPositionDao positionDao;

	//保存职位
	public void savePosition(Position position){
		positionDao.savePosition(position);
	}

//	//修改职位信息
//	public void updatePosition(Position position){
//		positionDao.updatePosition(position);
//	}

	//展示所有职位
	public List<Position> findAllPosition(){
		return positionDao.findAllPosition();
	}

	//根据职位id查找职位列表
	public Position findByPid(Integer pid){
		return positionDao.findByPid(pid);
	}

	//根据公司id查找职位列表
	public List<Position> findByPcid(Integer pcid){
		return positionDao.findByPcid(pcid);
	}

	//根据名称模糊查询职位信息
	public List<Position> findByName(String name){
		return positionDao.findByName(name);
	}
}
