package code.dao;

import code.domain.Position;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IPositionDao {

	//添加职位
	@Insert("insert into position (pname,pneed,pcontent,psalary,pisopen,plocation,pcid,pexperience,pacademic,preleasetime)values(#{pname},#{pneed},#{pcontent},#{psalary},#{pisopen},#{plocation},#{pcid},#{pexperience},#{pacademic},#{preleasetime})")
	public Integer savePosition(Position position);

	//删除职位
	@Delete("delete from position where pid =#{pid}")
	public Integer deletePosition(Integer pid);

	//修改职位
	@Update("update position set pname = #{pname},pneed = #{pneed},pcontent = #{pcontent},psalary = #{psalary},pisopen = #{pisopen}, plocation = #{plocation},pexperience = #{pexperience},pacademic = #{pacademic}" +
			"where pid = #{pid}")
	public Integer updatePosition(Position position);

	//查询账单类型
	@Select("select pisopen from position GROUP BY pisopen")
	public List<String> findPisopen();

	//修改职位状态
	@Update("update position set pisopen = #{arg1} where pid = #{arg0}")
	public Integer updatePisopen(Integer pid,String pisopen);

//	@Update("update position set pisopen = #{pisopen},pneed = #{pneed},pcontent = #{pcontent},psalary = #{psalary},pisopen = #{pisopen},plocation = #{plocation} where")
	//展示所有职位
	@Select("select * from position")
	public List<Position> findAllPosition();

	//展示所有开放的职位
	@Select("select * from position where pisopen='1'")
	public List<Position> findAllOpenPosition();

	//根据职位id查找职位
	@Select("select * from position where pid = #{pid} ")
	public Position findByPid(Integer pid);

	//根据公司id查找职位列表
	@Select("select * from position where pcid = #{pcid} ")
	public List<Position> findByPcid(Integer pcid);

	//应聘者根据公司id查找已开放职位列表
	@Select("select * from position where pcid = #{pcid} and pisopen='1'")
	public List<Position> findByPcidAndPisopen(Integer pcid);

	//根据名称模糊查询职位信息(职位必须是开放的)
	@Select("select * from position where pname like #{name} and pisopen='1'")
	public List<Position> findByName(String name);
}
