package com.wolfo.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolfo.beans.PfElementWrapper;
import com.wolfo.beans.PfEndPointWrapper;
import com.wolfo.beans.Quest;

@Service
public class DatabaseWrapperUtilImpl implements DatabaseWrapperUtil {

	private PfElementWrapperRepository pfElementWrapperRepository;
	private PfEndPointWrapperRepository endPointWrapperRepository;


	@Autowired
	public void setPfElementWrapperRepository(final PfElementWrapperRepository pfElementWrapperRepository) {
		this.pfElementWrapperRepository = pfElementWrapperRepository;
	}


	@Autowired
	public void setEndPointWrapperRepository(final PfEndPointWrapperRepository endPointWrapperRepository) {
		this.endPointWrapperRepository = endPointWrapperRepository;
	}

	@Override
	public void save(final PfEndPointWrapper pfEndPointWrapper) {
		endPointWrapperRepository.saveAndFlush(pfEndPointWrapper);
	}

	@Override
	public void save(final PfElementWrapper pfElementWrapper) {
		pfElementWrapperRepository.saveAndFlush(pfElementWrapper);
	}

	@Override
	public PfElementWrapper loadByQuest(final Quest quest) {
		return pfElementWrapperRepository.findByQuest(quest);
	}

	@Override
	public void deleteEndPointWrapper(final PfElementWrapper elementWrapper) {
		endPointWrapperRepository.deleteAll(elementWrapper.getEndPointWrapperList());
		pfElementWrapperRepository.delete(elementWrapper);
	}
}
