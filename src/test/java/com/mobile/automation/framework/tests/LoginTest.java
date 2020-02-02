package com.mobile.automation.framework.tests;

import javax.inject.Inject;

import com.mobile.automation.framework.screens.LoginScreen;

/**
 * @author Tomash Gombosh
 */
public class LoginTest extends BaseTest {
    @Inject
    private LoginScreen loginScreen;


    public void iAmOnTheLoginScreen() throws Throwable {
        loginScreen.isDisplayed();
    }
}
