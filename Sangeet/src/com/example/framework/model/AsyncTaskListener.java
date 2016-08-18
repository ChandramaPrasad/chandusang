package com.example.framework.model;

public interface AsyncTaskListener {

	public void onPreExecute();

	public Object doInBackground(String... params);

	public void onPostExecute(Object result);

}
