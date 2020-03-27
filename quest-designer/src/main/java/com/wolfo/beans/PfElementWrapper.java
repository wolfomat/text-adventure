package com.wolfo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class PfElementWrapper implements Serializable {

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;

	@Getter
	@Setter
	private String x;

	@Getter
	@Setter
	private String y;

	@Getter
	@Setter
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Quest quest;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "element", cascade = CascadeType.ALL)
	@Getter
	@Setter
	private List<PfEndPointWrapper> endPointWrapperList = new ArrayList<>();


}
