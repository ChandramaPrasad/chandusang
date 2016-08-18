package com.example.app.model;

import com.example.framework.model.IModel;
import com.example.framework.view.AbstractView;

/**
 * ModelFacade.java
 * 
 * This class contains all the models realted to application and can be access
 * via this class only. All AbstractView/Screen can get their model object
 * reference from this class
 * 
 * 
 */

public class ModelFacade implements IModel {
	/**
	 * LocalModel Class reference
	 */
	public LocalModel localModel;
	/**
	 * RemoteModel Class reference
	 */
	private RemoteModel remoteModel;

	public ModelFacade() {
		// TODO Auto-generated constructor stub

		remoteModel = new RemoteModel();
		localModel = new LocalModel();
		// playerModel = new PlayerModel();

	}

	public LocalModel getLocalModel() {
		return localModel;
	}

	public RemoteModel getRemoteModel() {
		return remoteModel;
	}

	@Override
	public void initialize() {

		localModel.initialize();
		remoteModel.initialize();

	}

	@Override
	public void destroy() {

		localModel.destroy();
		remoteModel.destroy();

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
