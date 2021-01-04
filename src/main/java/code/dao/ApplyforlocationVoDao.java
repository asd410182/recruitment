package code.dao;

import code.domain.Applyforlocation;
import code.domain.ApplyforlocationVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyforlocationVoDao {

	@Select("select * from apply_for_location where apid = #{pid} and astatus=#{astatus}")
	List<Applyforlocation> findApplicntList(ApplyforlocationVo applyforlocationVo);

	@Select("select * from apply_for_location,applicant " +
			"where apply_for_location.aaid =applicant.aid " +
			"and apid = #{pid} " +
			"and astatus= #{astatus} " +
			"and aname like #{aname}")
	List<Applyforlocation> findApplicntListByName(ApplyforlocationVo applyforlocationVo);

	@Select("select * from apply_for_location where aaid = #{aid} and astatus=#{astatus}")
	List<Applyforlocation> findPositionList(ApplyforlocationVo applyforlocationVo);

	@Select("select * from apply_for_location,applicant " +
			"where apply_for_location.apid =position.pid " +
			"and aaid = #{aid} " +
			"and astatus= #{astatus} " +
			"and pname like #{pname}")
	List<Applyforlocation> findPositionListByName(ApplyforlocationVo applyforlocationVo);
}
