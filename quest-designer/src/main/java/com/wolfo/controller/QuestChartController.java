package com.wolfo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.diagram.ConnectEvent;
import org.primefaces.event.diagram.ConnectionChangeEvent;
import org.primefaces.event.diagram.DisconnectEvent;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.FlowChartConnector;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.springframework.beans.factory.annotation.Autowired;

import com.wolfo.beans.PfAnchorPosition;
import com.wolfo.beans.PfElementWrapper;
import com.wolfo.beans.PfEndPointWrapper;
import com.wolfo.beans.QAnswer;
import com.wolfo.beans.Quest;
import com.wolfo.beans.QuestElement;
import com.wolfo.database.DatabaseUtil;
import com.wolfo.database.DatabaseWrapperUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Named
public class QuestChartController {

	private static final String QUEST_EDITOR_FORM_EDIT_DIALOG = "quest-editor-form:editDialog";

	private QuestNodeLib questNodeLib;
	private QuestDialogEdit questDialogEdit;

	private DatabaseUtil databaseUtil;
	private DatabaseWrapperUtil wrapperUtil;

	@Autowired
	public void setDatabaseUtil(final DatabaseUtil databaseUtil) {
		this.databaseUtil = databaseUtil;
	}

	@Autowired
	public void setWrapperUtil(final DatabaseWrapperUtil wrapperUtil) {
		this.wrapperUtil = wrapperUtil;
	}

	@Getter
	@Setter
	private Quest selectedQuest;

	@Getter
	private List<Quest> allInitQuests;

	@Setter
	@Getter
	private String questMainName;

	@Autowired
	public void setQuestNodeLib(QuestNodeLib questNodeLib) {
		this.questNodeLib = questNodeLib;
	}

	@Autowired
	public void setQuestDialogEdit(QuestDialogEdit questDialogEdit) {
		this.questDialogEdit = questDialogEdit;
	}

	@Getter
	@Setter
	private DefaultDiagramModel model;

	@Getter
	private String questNameForEdit;

	@Getter
	private QuestElement currentQuestToEdit;

	private boolean suspendEvent;

	@PostConstruct
	public void init() {
		model = new DefaultDiagramModel();
		model.setMaxConnections(-1);
		FlowChartConnector connector = new FlowChartConnector();
		connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
		model.setDefaultConnector(connector);
		questNameForEdit = "";
		currentQuestToEdit = null;
		allInitQuests = databaseUtil.loadAllInitQuests();
	}

	public void onNodeMove(ActionEvent param) {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = params.get("node_id").replace("quest-editor-form:diagram-", "");
		String x = params.get("node_x");
		String y = params.get("node_y");

		Element element = model.findElement(id);
		if (element != null) {
			element.setX(x);
			element.setY(y);
		} else {
			log.warn("Didn't find element for ID " + id);
		}
	}

	public void editQuest() {
		try {
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String id = params.get("node_id").replace("quest-editor-form:diagram-", "");
			Element element = model.findElement(id);
			if (element != null) {
				processShowQuestDialogEdit((element));
			} else {
				log.warn("Didn't find element for ID " + id);
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}

	private void processShowQuestDialogEdit(Element element) {
		questNameForEdit = ((QuestElement) element.getData()).getQuestName();
		PrimeFaces current = PrimeFaces.current();

		currentQuestToEdit = (QuestElement) element.getData();

		questDialogEdit.setCurrentElement(element);
		questDialogEdit.setCurrentQuest(currentQuestToEdit.getQuest());

		current.executeScript("PF('questEditDialog').show();");

		PrimeFaces.current().ajax().update(QUEST_EDITOR_FORM_EDIT_DIALOG);
	}

	public void addNode() {

		if (StringUtils.isEmpty(questMainName)) {
			sendFacesMessage(FacesMessage.SEVERITY_ERROR, "Game name not set", "Please set game name");
			return;
		}

		model.getElements().add(questNodeLib.createNewElement(model.getElements().size(), questMainName));

	}

	public void resetAll() {
		model.getElements().clear();
	}

	public void saveQuest() {

		final StringBuilder saveInfo = new StringBuilder();
		saveInfo.append("Quest has been ");
		String success = "saved.";

		try {
			model.getElements().forEach(this::deleteWrappers);
			model.getElements().forEach(this::preSaveQuests);
			model.getElements().forEach(this::saveOrUpdate);
			allInitQuests = databaseUtil.loadAllInitQuests();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			success = "not saved.";
		} finally {
			saveInfo.append(success);
			sendFacesMessage(FacesMessage.SEVERITY_INFO, "Quest '" + questMainName + "' was  " + success, saveInfo.toString());
		}

	}

	private void deleteWrappers(final Element element) {
		final QuestElement questElement = (QuestElement) element.getData();
		questElement.getQuest().setQuestSeries(questMainName);
		final PfElementWrapper wrapper = wrapperUtil.loadByQuest(questElement.getQuest());
		if (wrapper != null) {
			wrapperUtil.deleteEndPointWrapper(wrapper);
		}
	}

	private void preSaveQuests(final Element element) {
		final QuestElement questElement = (QuestElement) element.getData();
		databaseUtil.saveQuest(questElement.getQuest(), false);
	}

	public void saveOrUpdate(Element e) {
		final QuestElement questElement = (QuestElement) e.getData();
		questElement.getQuest().setQuestSeries(questMainName);

		PfElementWrapper wrapper = wrapperUtil.loadByQuest(questElement.getQuest());
		if (wrapper != null) {
			wrapperUtil.deleteEndPointWrapper(wrapper);
		}

		wrapper = new PfElementWrapper();
		wrapper.setX(e.getX());
		wrapper.setY(e.getY());
		wrapper.setQuest(questElement.getQuest());

		wrapperUtil.save(wrapper);
		deletePoints(wrapper);

		assignEndPointWrapper(e, wrapper);
	}

	private void deletePoints(PfElementWrapper wrapper) {
		wrapperUtil.deleteEndPointWrapper(wrapper);
	}

	private void assignEndPointWrapper(final Element element, final PfElementWrapper wrapper) {

		for (final EndPoint endPoint : element.getEndPoints()) {
			final PfEndPointWrapper pfEndPointWrapper = new PfEndPointWrapper();
			pfEndPointWrapper.setElement(wrapper);

			final PfAnchorPosition pfAnchorPosition = endPoint.getAnchor().equals(EndPointAnchor.CONTINUOUS_BOTTOM) ?
					PfAnchorPosition.CONTINUOUS_BOTTOM : PfAnchorPosition.CONTINUOUS_TOP;

			pfEndPointWrapper.setPfAnchorPosition(pfAnchorPosition);
			wrapper.getEndPointWrapperList().add(pfEndPointWrapper);
		}

		wrapperUtil.save(wrapper);
	}

	public void loadQuest() {

		if (selectedQuest != null) {
			final PfElementWrapper pfElementWrapper = wrapperUtil.loadByQuest(selectedQuest);

			if (pfElementWrapper != null) {
				createFromWrapper(pfElementWrapper);
			} else {
				createPlainFromQuest(selectedQuest);
			}

			questMainName = selectedQuest.getQuestSeries();
		}
	}

	private void createFromWrapper(PfElementWrapper pfElementWrapper) {
		final List<PfElementWrapper> toCreate = new ArrayList<>();
		toCreate.add(pfElementWrapper);

		// TODO: real recovered positions
		processAnswers(pfElementWrapper.getQuest(), toCreate);

		int num = 1;
		for (final PfElementWrapper wrapper : toCreate) {

			final Element e = createIfNoElement(wrapper.getQuest(), num++, false, wrapper.getX(), wrapper.getY());

			for (final PfEndPointWrapper endPointWrapper : wrapper.getEndPointWrapperList()) {
				final EndPoint endPoint = questNodeLib.createFromWrapper(endPointWrapper);
				if (endPoint != null) {
					e.getEndPoints().add(questNodeLib.createFromWrapper(endPointWrapper));
				}
			}
		}

		createConnections(model.getElements());

	}

	private void processAnswers(Quest quest, List<PfElementWrapper> toCreate) {
		for (final QAnswer qAnswer : quest.getQAnswers()) {

			final Quest nextQuest = qAnswer.getNextQuest();

			if (nextQuest != null) {
				final PfElementWrapper nextWrapper = wrapperUtil.loadByQuest(nextQuest);
				if (!toCreate.contains(nextWrapper)) {
					toCreate.add(nextWrapper);
				}
				processAnswers(nextQuest, toCreate);
			}
		}
	}

	private void createPlainFromQuest(final Quest quest) {
		final Element mainQuest = questNodeLib.createNewElement(quest, 1, true, null, null);

		model.getElements().add(mainQuest);

		processQuestChild(mainQuest);
		createConnections(model.getElements());
	}

	private void processQuestChild(final Element mainQuestElement) {
		final QuestElement data = (QuestElement) mainQuestElement.getData();

		for (final QAnswer qAnswer : data.getQuest().getQAnswers()) {
			mainQuestElement.addEndPoint(questNodeLib.createRectangleEndPoint(EndPointAnchor.CONTINUOUS_BOTTOM));
			processQuestChild(qAnswer.getNextQuest(), 2);
		}
	}

	private void processQuestChild(final Quest qChild, int currentNumber) {

		if (qChild == null) {
			return;
		}

		final Element nextQuest = createIfNoElement(qChild, currentNumber, true, null, null);

		for (final QAnswer qAnswer : qChild.getQAnswers()) {
			nextQuest.addEndPoint(questNodeLib.createRectangleEndPoint(EndPointAnchor.CONTINUOUS_BOTTOM));
			processQuestChild(qAnswer.getNextQuest(), ++currentNumber);
		}

	}

	private Element createIfNoElement(final Quest quest, int currentNumber, boolean withEndPoint,
			final String x, final String y) {

		final List<Element> result = model.getElements().stream()
				.filter(f -> isQuestNotNullAndEquals(quest, f))
				.collect(Collectors.toList());

		int listSize = result.size();
		if (listSize > 1) {
			log.warn("Quest has more than one Element. Number of Elements found: " + listSize);
		}

		Element nextQuestElement = null;
		if (result.isEmpty()) {
			nextQuestElement = questNodeLib.createNewElement(quest, currentNumber, withEndPoint, x, y);
			model.getElements().add(nextQuestElement);
		}
		nextQuestElement = nextQuestElement == null ? result.get(0) : nextQuestElement;

		return nextQuestElement;
	}

	private boolean isQuestNotNullAndEquals(final Quest quest, final Element f) {
		return ((QuestElement) f.getData()).getQuest() != null && ((QuestElement) f.getData()).getQuest().equals(quest);
	}

	private void createConnections(final List<Element> elements) {

		for (final Element e : elements) {
			QuestElement currentElement = (QuestElement) e.getData();

			for (QAnswer qAnswer : currentElement.getQuest().getQAnswers()) {
				int currentIndex = obtainIndexNumber(currentElement, qAnswer);
				final Element nextElement = findQuestElement(elements, qAnswer.getNextQuest());
				if (nextElement != null && currentIndex < e.getEndPoints().size()) {
					model.connect(new Connection(e.getEndPoints().get(currentIndex),
							nextElement.getEndPoints().get(0)));
				}
			}
		}

	}

	private int obtainIndexNumber(final QuestElement currentElement, final QAnswer qAnswer) {
		int currentIndex = currentElement.getQuest().getQAnswers().indexOf(qAnswer);
		if (!currentElement.getQuest().isStartQuest()) {
			currentIndex++;

		}
		return currentIndex;
	}

	private Element findQuestElement(List<Element> elements, Quest quest) {
		for (final Element e : elements) {

			final QuestElement currentElement = (QuestElement) e.getData();

			if (currentElement.getQuest().equals(quest)) {
				return e;
			}

		}

		return null;
	}

	public void onConnect(ConnectEvent event) {
		if (!suspendEvent) {
			final QuestElement sourceElement = (QuestElement) event.getSourceElement().getData();
			final QuestElement targetElement = (QuestElement) event.getTargetElement().getData();

			connectQuests(sourceElement, targetElement, true);
		} else {
			suspendEvent = false;
		}

		PrimeFaces.current().ajax().update(QUEST_EDITOR_FORM_EDIT_DIALOG);
	}

	public void onDisconnect(DisconnectEvent event) {
		if (!suspendEvent) {

			final QuestElement sourceElement = (QuestElement) event.getSourceElement().getData();
			final QuestElement targetElement = (QuestElement) event.getTargetElement().getData();

			disconnectQuests(sourceElement, targetElement, true);

		} else {
			suspendEvent = false;
		}

		PrimeFaces.current().ajax().update(QUEST_EDITOR_FORM_EDIT_DIALOG);
	}

	private void disconnectQuests(QuestElement source, QuestElement target, boolean withMessage) {

		final QAnswer qAnswer = obtainQAnwer(source.getQuest(), target.getQuest());

		if (qAnswer == null) {
			return;
		}

		int index = source.getQuest().getQAnswers().indexOf(qAnswer);

		if (withMessage) {
			final StringBuilder disconnectInfo = new StringBuilder();
			disconnectInfo.append("Answer [").append(index);
			disconnectInfo.append("] of [").append(source).append("] disconnected of [");
			disconnectInfo.append(target);
			disconnectInfo.append("]");
			sendFacesMessage(FacesMessage.SEVERITY_INFO, "Answer Disconnected", disconnectInfo.toString());
		}

	}

	private void connectQuests(QuestElement source, QuestElement target, boolean withMessage) {

		for (final QAnswer qAnswer : source.getQuest().getQAnswers()) {

			if (qAnswer.getNextQuest() == null) {
				qAnswer.setNextQuest(target.getQuest());

				if (withMessage) {
					final StringBuilder connectInfo = new StringBuilder();
					connectInfo.append("Answer[").append(source.getQuest().getQAnswers().indexOf(qAnswer));
					connectInfo.append("] of [").append(source).append("] to [");
					connectInfo.append(target).append("]");
					sendFacesMessage(FacesMessage.SEVERITY_INFO, "Answer Connected", connectInfo.toString());
				}
				break;
			}

		}

	}

	private QAnswer obtainQAnwer(Quest source, Quest target) {
		for (final QAnswer qAnswer : source.getQAnswers()) {
			if (qAnswer.getNextQuest().equals(target)) {
				qAnswer.setNextQuest(null);
				return qAnswer;
			}
		}

		return null;
	}

	public void onConnectionChange(ConnectionChangeEvent event) {

		final QuestElement orgSourceElement = (QuestElement) event.getOriginalSourceElement().getData();
		final QuestElement orgTargetElement = (QuestElement) event.getOriginalTargetElement().getData();

		final QuestElement newSourceElement = (QuestElement) event.getNewSourceElement().getData();
		final QuestElement newTargetElement = (QuestElement) event.getNewTargetElement().getData();

		disconnectQuests(orgSourceElement, orgTargetElement, false);
		connectQuests(newSourceElement, newTargetElement, true);

	}

	private void sendFacesMessage(FacesMessage.Severity severity, String summary, String detail) {
		final FacesMessage msg = new FacesMessage(severity, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		PrimeFaces.current().ajax().update("quest-editor-form:msgs");
	}

	// Dialog Edit functions

}
