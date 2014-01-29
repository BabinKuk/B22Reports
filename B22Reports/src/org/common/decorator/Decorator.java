package org.common.decorator;

import java.io.IOException;
import java.util.ArrayList;

import jxl.write.WriteException;

import org.common.model.IRow;

public abstract class Decorator {
	public abstract ArrayList<IRow> getTable();

	/**
	 * abs decorator method
	 * @throws IOException
	 * @throws WriteException
	 */
	public abstract void write() throws IOException, WriteException;
	
}
