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
	List<Position> findPositionList(PositionVo positionVo);
}
