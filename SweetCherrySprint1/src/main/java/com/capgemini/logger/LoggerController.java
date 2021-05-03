package com.capgemini.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerController {

	private LoggerController(){}

	public static Logger getLogger(Class<?> classname) {
		return LoggerFactory.getLogger(classname);
	}
}
