package code.service;

import code.domain.Applyforlocation;
import code.domain.ApplyforlocationVo;

import java.util.List;

public interface IApplyforlocationVoService {
	public List<Applyforlocation> findApplicntList(ApplyforlocationVo applyforlocationVo);

	public List<Applyforlocation> findPositionList(ApplyforlocationVo applyforlocationVo);

	public List<Applyforlocation> findApplicntListByName(ApplyforlocationVo applyforlocationVo);

	public List<Applyforlocation> findPositionListByName(ApplyforlocationVo applyforlocationVo);
}
