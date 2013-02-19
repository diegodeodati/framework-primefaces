package it.betplus.web.framework.managedbeans;

import it.betplus.web.framework.exceptions.DataLayerException;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class LoginBean extends GeneralBean implements Serializable {

	private static final long serialVersionUID = -1608079561797678268L;

	protected static final Log log = LogFactory.getLog(LoginBean.class);

	protected String username;
	protected String password;

	protected Object loggedUser;

	protected String loginOutcome;
	protected String logoutOutcome;

	public LoginBean() {
		super();
	}

	// *** Business methods ***//

	// login into system
	public String login() {

		try {

			// check username and password
			if ((username != null && !username.equals(""))
					&& (password != null && !password.equals(""))) {

				// check values into DB
				Object userDto = getUserAuthenticationDTO(username, password);

				if (userDto != null) {

					// put user into session bean
					setLoggedUserFromDTO(userDto);

					// store user variable into session for filter
					FacesContext.getCurrentInstance().getExternalContext()
							.getSessionMap().put("currentUser", username);

					log.info("User " + username + " logged into applicaiton ");

					// redirect page for user type
					return forwardToLoginOutcome();

				} else {

					// error message invalid username & password
					sendErrorMessageToUser(
							getFromBundleMsgs("error_type_login"),
							getFromBundleMsgs("error_invalidLogin"));
					return null;

				}

			} else {

				// error message invalid username & password
				sendErrorMessageToUser(getFromBundleMsgs("error_type_login"),
						getFromBundleMsgs("error_loginRequired"));
				return null;

			}

		} catch (DataLayerException dle) {
			sendErrorMessageToUser(getFromBundleMsgs("error_type_data"),
					getFromBundleMsgs("error_noDataRetrieved"));
			log.error("Unhandled exception - LoginBean ", dle);
		} catch (Exception e) {
			sendErrorMessageToUser(getFromBundleMsgs("error_type_data"), e
					.getCause().getMessage());
			log.error("Unhandled exception - LoginBean ", e);
		}

		return null;

	}

	// log out from system
	public String logout() {

		// remove user variable from session for filter
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.remove("currentUser");

		// remove user from session bean
		resetLoginSession();

		log.info("User " + username + " logged out from application");

		return logoutOutcome;

	}

	// Forward to c onfigured outcome after login
	public String forwardToLoginOutcome() {

		return loginOutcome;

	}

	// retrieve the DB authentication object
	public abstract Object getUserAuthenticationDTO(String username,
			String password) throws DataLayerException, Exception;

	// set logged user into the current bean
	public abstract void setLoggedUserFromDTO(Object dto);

	// reset all data of the user session
	public void resetLoginSession() {

		this.loggedUser = null;
		this.username = "";
		this.password = "";

	}

	// set the outcome for redirection configured into faces-config.xml
	public void configureOutcomes(String loginOutcome, String logoutOutcome) {

		this.loginOutcome = loginOutcome;
		this.logoutOutcome = logoutOutcome;

	}

	// *** get & set methods ***//
	public Object getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(Object loggedUser) {
		this.loggedUser = loggedUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginOutcome() {
		return loginOutcome;
	}

	public void setLoginOutcome(String loginOutcome) {
		this.loginOutcome = loginOutcome;
	}

	public String getLogoutOutcome() {
		return logoutOutcome;
	}

	public void setLogoutOutcome(String logoutOutcome) {
		this.logoutOutcome = logoutOutcome;
	}

}
