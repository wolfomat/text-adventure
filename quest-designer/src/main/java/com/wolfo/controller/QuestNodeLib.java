package com.wolfo.controller;

import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.endpoint.RectangleEndPoint;
import org.springframework.stereotype.Service;

import com.wolfo.beans.PfAnchorPosition;
import com.wolfo.beans.PfEndPointWrapper;
import com.wolfo.beans.Quest;
import com.wolfo.beans.QuestElement;

import com.wolfo.utils.QuestUtils;

@Service
public class QuestNodeLib {

	Element createNewElement(Quest quest, int num, boolean withEndpoint, final String x, final String y) {
		final String xPos = x == null ? "0em" : x;
		final String yPos = y == null ? "0em" : y;

		final Element e = new Element(new QuestElement("QuestElement" + num, quest), xPos, yPos);

		if (num > 1 && withEndpoint) {
			e.addEndPoint(createDotEndPoint(EndPointAnchor.CONTINUOUS_TOP));
		}

		return e;
	}

	Element createNewElement(int currentSize, String questName) {

		Quest quest = QuestUtils.createEmptyQuest();
		quest.setQuestSeries(questName);

		Element newElement = new Element(
				new QuestElement("QuestElement" + (currentSize + 1), quest));

		quest.getQAnswers().add(QuestUtils.createEmptyAnswer(quest));

		EndPoint endPointCA = createRectangleEndPoint(EndPointAnchor.CONTINUOUS_BOTTOM);
		newElement.addEndPoint(endPointCA);

		if (currentSize == 0) {
			quest.setStartQuest(true);
		}

		if (currentSize >= 1) {
			EndPoint leftCircleEnd = createDotEndPoint(EndPointAnchor.CONTINUOUS_TOP);
			newElement.addEndPoint(leftCircleEnd);
		}

		return newElement;

	}

	public EndPoint createFromWrapper(PfEndPointWrapper endPointWrapper) {
		if (endPointWrapper.getPfAnchorPosition().equals(PfAnchorPosition.CONTINUOUS_BOTTOM)) {
			return createRectangleEndPoint(EndPointAnchor.CONTINUOUS_BOTTOM);
		}

		if (endPointWrapper.getPfAnchorPosition().equals(PfAnchorPosition.CONTINUOUS_TOP)) {
			return createRectangleEndPoint(EndPointAnchor.CONTINUOUS_TOP);
		}
		return null;
	}

	public EndPoint createRectangleEndPoint(EndPointAnchor anchor) {
		RectangleEndPoint endPoint = new RectangleEndPoint(anchor);

		endPoint.setScope("network");
		endPoint.setSource(true);
		endPoint.setStyle("{fillStyle:'#98AFC7'}");
		endPoint.setHoverStyle("{fillStyle:'#5C738B'}");

		return endPoint;
	}

	public EndPoint createDotEndPoint(EndPointAnchor anchor) {
		DotEndPoint endPoint = new DotEndPoint(anchor);

		endPoint.setScope("network");
		endPoint.setTarget(true);
		endPoint.setStyle("{fillStyle:'#98AFC7'}");
		endPoint.setHoverStyle("{fillStyle:'#5C738B'}");

		return endPoint;
	}


}