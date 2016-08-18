package com.example.framework.model;


import android.os.AsyncTask;

import com.example.framework.net.HttpRequestHandlerImpl;
import com.example.framework.view.AbstractView;

public abstract class BaseModel extends Model implements AsyncTaskListener {

	public static final int STATE_START = 1;
	public static final int STATE_INPROCESS = 2;
	public static final int STATE_SUCCESS = 3;
	public static final int STATE_ERROR = 4;
	public static final int STATE_NO_DATA = 5;
	public static final int STATE_END = 6;
	int communicationState = -1;

	protected HttpRequestHandlerImpl httpRequestHandlerImpl;
	protected Object requestData;
	protected Object responseData = null;

	protected String errorMessage;

	private BaseModelAsyncTask baseModelAsyncTask;

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		super.initialize();

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();

		if (httpRequestHandlerImpl != null) {
			httpRequestHandlerImpl.cancelRequest();
		}

		if (baseModelAsyncTask != null) {
			baseModelAsyncTask.cancel(true);
		}
	}

	@Override
	public void register(AbstractView view) {
		// TODO Auto-generated method stub
		super.register(view);
	}

	@Override
	public void unRegister(AbstractView view) {
		// TODO Auto-generated method stub
		super.unRegister(view);
	}

	@Override
	public void informView() {
		super.informView();
	}

	/**
	 * Set the current state of model
	 * 
	 * @param communicationState
	 */
	public void setCommunicationState(int communicationState) {
		this.communicationState = communicationState;
	}

	public int getCommunicationState() {
		return communicationState;
	}

	// protected abstract void onPreExecute();
	//
	// protected abstract JSONObject doInBackground(String... params);
	//
	// protected abstract void onPostExecute(JSONObject result);

	protected void startAsyncTask() {

		baseModelAsyncTask = new BaseModelAsyncTask(this);
		baseModelAsyncTask.execute("");
	}

	protected void cancelAsyncTask() {
		if (baseModelAsyncTask != null) {

			baseModelAsyncTask.cancel(true);
		}

	}

	public class BaseModelAsyncTask extends AsyncTask<String, Void, Object> {

		AsyncTaskListener asyncTaskListener;

		public BaseModelAsyncTask(AsyncTaskListener asyncTaskListener) {

			this.asyncTaskListener = asyncTaskListener;

		}

		@Override
		protected Object doInBackground(String... params) {
			return asyncTaskListener.doInBackground(params);
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			asyncTaskListener.onPostExecute(result);

		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			asyncTaskListener.onPreExecute();

		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
		}

		@Override
		protected void onCancelled(Object result) {
			// TODO Auto-generated method stub
			super.onCancelled(result);
		}

	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
