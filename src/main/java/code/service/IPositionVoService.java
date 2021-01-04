package code.service;

import code.domain.Position;
import code.domain.PositionVo;

import java.util.List;

public interface IPositionVoService {


	public List<Position> findPositionList(PositionVo positionVo);

	//查询职位
	//根据名称模糊查询职位信息
	public List<Position> findByPname(PositionVo positionVo);

	//查询职位
	//根据职位状态查询职位信息
	public List<Position> findByPisopen(PositionVo positionVo);

	//查询职位
	//根据名称和职位状态查询职位信息
	public List<Position> findByPnameAndPisoen(PositionVo positionVo);
}
