package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public class MyLogger {
	private final String filename = "file.log";
	private final static Logger logger = Logger.getGlobal();
	private OutputStream os;
	private FileHandler handler;
	private SimpleFormatter formatter;
	
	public MyLogger() throws SecurityException, IOException {
		this.formatter = new SimpleFormatter();
		this.handler = new FileHandler(filename, true);
		logger.addHandler(handler);
	}
}
