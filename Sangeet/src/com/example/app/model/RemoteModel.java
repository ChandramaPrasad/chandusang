package com.example.app.model;

import com.example.framework.model.IModel;
import com.example.framework.view.AbstractView;

/**
 * RemoteModel.java The RemoteModel Class , which helps to communicate with
 * server. Some times if we do not have special Model for View to get the data
 * from server. we can use this model to handle those implementation. But its
 * better to use seprate model for each view.
 * 
 * @author Ashish Kumar Patel
 * 
 */

public class RemoteModel implements IModel

{

	/**
	 * Constructor
	 */
	public RemoteModel() {
		// TODO Auto-generated constructor stub
		// getUserAgent();
	}

	@Override
	public void initialize() {

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void informView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void register(AbstractView view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unRegister(AbstractView view) {
		// TODO Auto-generated method stub

	}

}
