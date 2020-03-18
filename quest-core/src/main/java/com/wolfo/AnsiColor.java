package com.wolfo;

public enum AnsiColor {

	RESET("\u001B[0m"),

	BLACK_UNDERLINED("\033[4;30m"),
	RED_UNDERLINED("\033[4;31m"),
	GREEN_UNDERLINED("\033[4;32m"),
	YELLOW_UNDERLINED("\033[4;33m"),
	BLUE_UNDERLINED("\033[4;34m"),
	PURPLE_UNDERLINED("\033[4;35m"),
	CYAN_UNDERLINED("\033[4;36m"),
	WHITE_UNDERLINED("\033[4;37m"),

	BLACK_BOLD("\033[1;30m"),
	RED_BOLD("\033[1;31m"),
	GREEN_BOLD("\033[1;32m"),
	YELLOW_BOLD("\033[1;33m"), // YELLOW
	BLUE_BOLD("\033[1;34m"),
	PURPLE_BOLD("\033[1;35m"), // PURPLE
	CYAN_BOLD("\033[1;36m"),
	WHITE_BOLD("\033[1;37m"),

	BLACK("\u001B[30m"),
	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	YELLOW("\u001B[33m"),
	BLUE("\u001B[34m"),
	CYAN("\u001B[36m"),
	WHITE("\u001B[37m"),

	BLACK_BACKGROUND("\u001B[40m"),
	RED_BACKGROUND("\u001B[41m"),
	GREEN_BACKGROUND("\u001B[42m"),
	YELLOW_BACKGROUND("\u001B[43m"),
	BLUE_BACKGROUND("\u001B[44m"),
	PURPLE_BACKGROUND("\u001B[45m"),
	CYAN_BACKGROUND("\u001B[46m"),
	WHITE_BACKGROUND("\u001B[47m");

	private String value;

	public String getValue() {
		return value;
	}

	AnsiColor(String value) {
		this.value = value;
	}
}