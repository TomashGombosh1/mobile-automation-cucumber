package com.mobile.automation.framework.module;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.mobile.automation.framework.screens.DashboardScreen;
import com.mobile.automation.framework.screens.SignInScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

/**
 * @author Tomash Gombosh
 */
public class ScreensModule extends AbstractModule {
    @Inject
    private AppiumDriver<MobileElement> driver;

    @Override
    protected void configure() {
    }

    @Provides
    public DashboardScreen getDashboardScreen() {
        return new DashboardScreen(driver);
    }

    @Provides
    public SignInScreen getSignInScreen() {
        return new SignInScreen(driver);
    }

}
