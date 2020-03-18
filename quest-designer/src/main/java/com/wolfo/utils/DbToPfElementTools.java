package com.wolfo.utils;

import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolfo.controller.QuestNodeLib;
import com.wolfo.beans.PfAnchorPosition;
import com.wolfo.beans.PfElementWrapper;
import com.wolfo.beans.PfEndPointWrapper;
import com.wolfo.beans.Quest;

@Service
public class DbToPfElementTools {

	private QuestNodeLib questNodeLib;

	@Autowired
	public void setQuestNodeLib(QuestNodeLib questNodeLib) {
		this.questNodeLib = questNodeLib;
	}

	public PfElementWrapper convertToPrimefaces(Element e) {
		PfElementWrapper pfElementWrapper = new PfElementWrapper();
		pfElementWrapper.setQuest((Quest) e.getData());
		pfElementWrapper.setX(e.getX());
		pfElementWrapper.setY(e.getY());

		e.getEndPoints().forEach(p ->
			pfElementWrapper.getEndPointWrapperList().add(createFrom(p, pfElementWrapper))
		);

		return pfElementWrapper;
	}

	private PfEndPointWrapper createFrom(EndPoint endPoint, PfElementWrapper pfElementWrapper) {
		PfEndPointWrapper endPointWrapper = new PfEndPointWrapper();

		PfAnchorPosition endPointType = endPoint.getAnchor().equals(EndPointAnchor.CONTINUOUS_BOTTOM)
				? PfAnchorPosition.CONTINUOUS_BOTTOM : PfAnchorPosition.CONTINUOUS_TOP;
		endPointWrapper.setPfAnchorPosition(endPointType);
		endPointWrapper.setElement(pfElementWrapper);

		return endPointWrapper;
	}

}
