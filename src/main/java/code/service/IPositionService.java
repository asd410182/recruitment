package code.service;

import code.domain.Position;

import java.util.List;

public interface IPositionService {

	//保存职位
	public void savePosition(Position position);

	//修改职位状态
	public void updatePisopen(Integer pid,String pisopen);

	//展示所有职位
	public List<Position> findAllPosition();

	//根据职位id查找职位列表
	public Position findByPid(Integer pid);

	//根据公司id查找职位列表
	public List<Position> findByPcid(Integer pcid);

	//应聘者根据公司id查找已开放职位列表
	public List<Position> findByPcidAndPisopen(Integer pcid);

	//根据名称模糊查询职位信息
	public List<Position> findByName(String name);

	//展示所有开放的职位
	public List<Position> findAllOpenPosition();
}
