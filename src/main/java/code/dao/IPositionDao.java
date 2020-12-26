package code.dao;

import code.domain.Position;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IPositionDao {

	//保存职位
	@Insert("insert into position (pname,pneed,pcontent,psalary,pisopen,plocation,pcid,pexperience,pacademic,preleasetime)values(#{pname},#{pneed},#{pcontent},#{psalary},#{pisopen},#{plocation},#{pcid},#{pexperience},#{pacademic},#{preleasetime})")
	public void savePosition(Position position);

	//修改职位状态
	@Update("update position set pisopen = #{pisopen} where pid = #{pid}")
	public void updatePisopen(Position position);
//	@Update("update position set pisopen = #{pisopen},pneed = #{pneed},pcontent = #{pcontent},psalary = #{psalary},pisopen = #{pisopen},plocation = #{plocation} where")
	//展示所有职位
	@Select("select * from position")
	public List<Position> findAllPosition();

	//根据职位id查找职位
	@Select("select * from position where pid = #{pid} ")
	public Position findByPid(Integer pid);

	//根据公司id查找职位列表
	@Select("select * from position where pcid = #{pcid} ")
	public List<Position> findByPcid(Integer pcid);

	//根据名称模糊查询职位信息
	@Select("select * from position where pname like #{name} ")
	public List<Position> findByName(String name);
}
