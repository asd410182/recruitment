package code.service;

import code.domain.Position;
import code.domain.PositionVo;

import java.util.List;

public interface IPositionVoService {


	public List<Position> findPositionList(PositionVo positionVo);
}
