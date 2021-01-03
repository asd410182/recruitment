package code.dao;

import code.domain.Applyforlocation;
import code.domain.ApplyforlocationVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyforlocationVoDao {

	@Select("select * from apply_for_location where apid = #{pid}")
	List<Applyforlocation> findApplicntList(ApplyforlocationVo applyforlocationVo);

	@Select("select * from apply_for_location where aaid = #{aid}")
	List<Applyforlocation> findPositionList(ApplyforlocationVo applyforlocationVo);
}
