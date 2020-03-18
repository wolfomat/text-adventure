package com.wolfo.database;

import com.wolfo.beans.PfElementWrapper;
import com.wolfo.beans.PfEndPointWrapper;
import com.wolfo.beans.Quest;

public interface DatabaseWrapperUtil {

	void save(PfEndPointWrapper pfEndPointWrapper);
	void save(PfElementWrapper pfElementWrapper);

	PfElementWrapper loadByQuest(Quest quest);

	void deleteEndPointWrapper(PfElementWrapper elementWrapper);

}
