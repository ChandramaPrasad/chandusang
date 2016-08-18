package com.example.framework.model;

import java.util.Vector;

import com.example.framework.view.AbstractView;

/**
 * Model.java The Model Class to achieve MVC Design Pattern in the application.
 * 
 * 
 */
public class Model implements IModel {

	/**
	 * To store the different AbstractView for same model
	 */
	protected Vector<AbstractView> abstractViews;

	/**
	 * Constructor
	 */
	public Model() {
		abstractViews = new Vector<AbstractView>();
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		// abstractViews.removeAllElements();
		// abstractViews = null;
	}

	@Override
	public void register(AbstractView view) {

		// add AbstractView to array so we can inform
		// all the view about updates in model
		if (abstractViews != null && view != null)
			abstractViews.add(view);

	}

	@Override
	public void unRegister(AbstractView view) {

		// remove the AbstractView so it will not get update.
		if (abstractViews != null && view != null)
			abstractViews.remove(view);
	}

	@Override
	public void informView() {
		// TODO Auto-generated method stub

		// update each AbstractView register to this model
		if (abstractViews != null) {
			for (int i = 0; i < abstractViews.size(); i++) {
				if (abstractViews.elementAt(i) != null)
					abstractViews.elementAt(i).update();

			}
		}

	}

}
