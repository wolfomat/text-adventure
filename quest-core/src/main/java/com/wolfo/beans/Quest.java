package com.wolfo.beans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Quest implements Serializable {

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;

	@Getter
	@Setter
	private boolean isStartQuest;

	@Getter
	@Setter
	private String questSeries;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "qText_id")
	@Getter
	@Setter
	private QText questText;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "qQuestion_id")
	@Getter
	@Setter
	private QQuestion qQuestion;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quest", cascade = CascadeType.ALL)
	@Getter
	@Setter
	private List<QAnswer> qAnswers;

}
