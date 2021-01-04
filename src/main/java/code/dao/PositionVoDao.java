package code.dao;

import code.domain.Position;
import code.domain.PositionVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionVoDao {

	//查询职位
	@Select("select * from position where pcid = #{cid}")
	public List<Position> findPositionList(PositionVo positionVo);

	//查询职位
	//根据名称模糊查询职位信息
	@Select("select * from position where pname like #{pname} and pcid =#{cid}")
	public List<Position> findByPname(PositionVo positionVo);

	//查询职位
	//根据职位状态查询职位信息
	@Select("select * from position where pisopen = #{pisopen} and pcid =#{cid}")
	public List<Position> findByPisopen(PositionVo positionVo);

	//查询职位
	//根据名称和职位状态查询职位信息
	@Select("select * from position where pname like #{pname} and pisopen= #{pisopen} and pcid = #{cid}")
	public List<Position> findByPnameAndPisoen(PositionVo positionVo);

}
