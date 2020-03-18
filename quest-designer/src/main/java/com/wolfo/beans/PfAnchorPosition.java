package com.wolfo.beans;

public enum PfAnchorPosition {

	CONTINUOUS_TOP("CONTINUOUS_TOP"),
	CONTINUOUS_BOTTOM("CONTINUOUS_BOTTOM");

	private String anchor;

	PfAnchorPosition(String anchor) {
		this.anchor = anchor;
	}

	public String getAnchor() {
		return anchor;
	}

}
