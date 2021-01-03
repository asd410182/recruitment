package code.service.impl;

import code.dao.PositionVoDao;
import code.domain.Position;
import code.domain.PositionVo;
import code.service.IPositionVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("IPositionVoService")
public class PositionVoServiceImpl implements IPositionVoService {

	@Autowired
	private PositionVoDao positionVoDao;

	public  List<Position> findPositionList(PositionVo positionVo){
		return positionVoDao.findPositionList(positionVo);
	}


}
