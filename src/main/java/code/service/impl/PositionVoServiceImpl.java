package code.service.impl;

import code.dao.PositionVoDao;
import code.domain.Position;
import code.domain.PositionVo;
import code.service.IPositionVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("IPositionVoService")
public class PositionVoServiceImpl implements IPositionVoService {

	@Autowired
	private PositionVoDao positionVoDao;

	public  List<Position> findPositionList(PositionVo positionVo){
		return positionVoDao.findPositionList(positionVo);
	}

	//查询职位
	//根据名称模糊查询职位信息
	public List<Position> findByPname(PositionVo positionVo){
		return positionVoDao.findByPname(positionVo);
	}

	//查询职位
	//根据职位状态查询职位信息
	public List<Position> findByPisopen(PositionVo positionVo){
		return positionVoDao.findByPisopen(positionVo);
	}

	//查询职位
	//根据名称和职位状态查询职位信息
	public List<Position> findByPnameAndPisoen(PositionVo positionVo){
		return positionVoDao.findByPnameAndPisoen(positionVo);
	}


}
