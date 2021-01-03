package code.service;

import code.domain.Applyforlocation;
import code.domain.ApplyforlocationVo;

import java.util.List;

public interface IApplyforlocationVoService {
	List<Applyforlocation> findPositionList(ApplyforlocationVo applyforlocationVo);
}
