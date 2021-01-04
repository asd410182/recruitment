package code.service.impl;

import code.dao.ApplyforlocationVoDao;
import code.domain.Applyforlocation;
import code.domain.ApplyforlocationVo;
import code.service.IApplyforlocationVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("IApplyforlocationVoService")
public class ApplyforlocationVoServiceImpl implements IApplyforlocationVoService {
	@Autowired
	private ApplyforlocationVoDao applyforlocationVoDao;

	public List<Applyforlocation> findApplicntList(ApplyforlocationVo applyforlocationVo){
		return applyforlocationVoDao.findApplicntList(applyforlocationVo);
	}

	public List<Applyforlocation> findPositionList(ApplyforlocationVo applyforlocationVo){
		return applyforlocationVoDao.findPositionList(applyforlocationVo);
	}

	public List<Applyforlocation> findApplicntListByName(ApplyforlocationVo applyforlocationVo){
		return applyforlocationVoDao.findApplicntListByName(applyforlocationVo);
	}

	public List<Applyforlocation> findPositionListByName(ApplyforlocationVo applyforlocationVo){
		return applyforlocationVoDao.findPositionListByName(applyforlocationVo);
	}
}

