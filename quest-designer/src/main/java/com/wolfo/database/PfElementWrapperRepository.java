package com.wolfo.database;

import com.wolfo.beans.PfElementWrapper;
import com.wolfo.beans.Quest;

public interface PfElementWrapperRepository extends PfElementWrapperCrud, PfElementWrapperJpa{

	PfElementWrapper findByQuest(Quest quest);

}
