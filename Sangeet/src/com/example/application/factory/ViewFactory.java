/**
 * 
 */
package com.example.application.factory;

import com.example.application.controller.ApplicationController;
import com.example.framework.view.AbstractView;
import com.motion.actionbar.BaseActivity;
import com.motion.audiocontroller.AudioControllerActivity;
import com.motion.sangeet.DevotionalAlbumActivity;
import com.motion.sangeet.SongsActivity;

/**
 * ViewFactory.java The Class which returns the AbstractView (Screen) to the
 * application frame. Developer should use this class to get the reference of
 * any screen in the application. He should not create the screen by him/her
 * self
 * 
 * 
 */
public class ViewFactory {

	/**
	 * Splash Screen ID
	 */
	public static final int HOME_SCREEN = 0;
	public static final int DEVOTIONAL_ALBUM_SCREEN = 1;
	public static final int SONGS_PLAYER_SCREEN = 2;
	public static final int AUDIO_CONTROLLER_SCREEN = 3;

	// public static final int MAIN_SCREEN = 1;

	// public static final int REGISTER_SCREEN = 2;
	//
	// public static final int MAP_SCREEN = 3;

	/**
	 * Reference of Application Controller
	 */
	ApplicationController applicationController = null;

	/**
	 * Singleton instance of ViewFactory
	 */
	private static ViewFactory instance = null;

	/**
	 * Constructor
	 */

	private ViewFactory() {
		applicationController = ApplicationController.getInstance();

	}

	/**
	 * Get Single instance of ViewFactory
	 * 
	 * @return ViewFactory single instance
	 */
	public static ViewFactory getInstance() {
		if (instance == null) {
			instance = new ViewFactory();
		}
		return instance;
	}

	/**
	 * To get the different AbstractView from given id This function should only
	 * use when we have single activity in whole applicaiton
	 * 
	 * 
	 * @param id
	 *            AbstractView/Screen ID
	 * @return AbstractView
	 */
	public AbstractView getAbstractView(int id) {
		try {
			switch (id) {

			case HOME_SCREEN: {
				return null;
			}

			}

		} catch (Exception e) {
			// System.out.println("ViewFactory.getAbstractView()" + e);

		}
		return null;
	}

	/**
	 * This function should only be used when whole application is made by
	 * multiple activity.
	 * 
	 * @param id
	 * @return
	 */
	public Class getActivityClass(int id) {

		switch (id) {

		case HOME_SCREEN: {
			return BaseActivity.class;
		}

		case DEVOTIONAL_ALBUM_SCREEN: {
			return DevotionalAlbumActivity.class;
		}

		case SONGS_PLAYER_SCREEN: {
			return SongsActivity.class;
		}

		case AUDIO_CONTROLLER_SCREEN: {
			return AudioControllerActivity.class;
		}

		default:
			break;
		}

		return null;

	}

	public void releaseScreen(AbstractView absView) {

	}

	/**
	 * Release All Screen before exiting the application
	 */
	public void releaseAllScreen() {

	}

}
