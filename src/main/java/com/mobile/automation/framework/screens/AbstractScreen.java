package com.mobile.automation.framework.screens;

import com.mobile.automation.framework.common.AppElement;
import com.mobile.automation.framework.common.ScrollTo;
import com.mobile.automation.framework.common.utils.Utils;
import com.mobile.automation.framework.config.ProjectConfig;
import com.mobile.automation.framework.config.drivers.DeviceOs;
import com.mobile.automation.framework.service.Actions;
import com.mobile.automation.framework.service.Alert;
import com.mobile.automation.framework.service.Element;
import com.mobile.automation.framework.service.Scroll;
import com.mobile.automation.framework.service.Swipe;
import com.mobile.automation.framework.service.Waits;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import static com.mobile.automation.framework.common.GlobalElements.BACK_BUTTON;

/**
 * @author Tomash Gombosh
 */
@Log4j
public abstract class AbstractScreen {
    protected final Actions actionsService;
    private final AppiumDriver driver;
    protected final Alert alertService;
    protected final Element elementService;
    protected final Scroll scrollService;
    protected final Swipe swipeService;
    protected final Waits waitService;
    private final ProjectConfig projectConfig;

    public AbstractScreen(final AppiumDriver driver) {
        this.driver = driver;
        projectConfig = new ProjectConfig();
        final Dimension winSize = driver.manage().window().getSize();
        final TouchAction touchAction = new TouchAction(driver);
        waitService = new Waits(driver, projectConfig);
        swipeService = new Swipe(driver, winSize, touchAction);
        scrollService = new Scroll(driver, swipeService, projectConfig);
        elementService = new Element(driver, scrollService, touchAction, waitService);
        actionsService = new Actions(driver, elementService, winSize, touchAction, projectConfig);
        alertService = new Alert(actionsService, waitService, elementService);
    }

    public abstract boolean isDisplayed();

    /**
     * check that list of missing required element is empty
     *
     * @return true or false
     */
    protected boolean allRequiredElementDisplayed() {
        return elementService.getMissingRequiredElements(elementService.getRequiredElements()).isEmpty();
    }

    /**
     * Gets the current value of a Text Input field.
     *
     * @param element the text input from which to grab the value
     * @return the current value of the text input.
     */
    public String getInputValue(final AppElement element) {
        return actionsService.getInputValue(element);
    }

    /**
     * Presses the device's Back button. Only functional on Android!
     */
    public void pressDeviceBack() {
        log.info("Pressing the Android device back key...");
        this.driver.navigate().back(); //todo needs review, looks like not working when one photo taken
    }

    /**
     * Waits for the specified timeout period for an element to be visible.
     *
     * @param element the By object representing the element to wait for.
     */
    public void waitToBeVisible(final AppElement element) {
        waitService.waitToBeVisible(element);
    }

    /**
     * Waits for the specified timeout period for an element to be invisible.
     *
     * @param element the By object representing the element to wait for.
     * @param timeout the length of time in seconds to wait, as an integer.
     */
    public void waitToBeInvisible(final AppElement element, final int timeout) {
        waitService.waitToBeInvisible(element, timeout);
    }

    public void waitToBeInvisible(final AppElement element) {
        waitService.waitToBeInvisible(element);
    }

    /**
     * Waits for the specified timeout period for an element to be clickable.
     *
     * @param element the By object representing the element to wait for.
     * @param timeout the length of time in seconds to wait, as an integer.
     */

    public void waitToBeClickable(final AppElement element, final int timeout) {
        waitService.waitToBeClickable(element, timeout);
    }

    public void waitToBeClickable(final AppElement element) {
        waitService.waitToBeClickable(element);
    }

    /**
     * Waits for the specified timeout period for an element to be present.
     *
     * @param element the By object representing the element to wait for.
     * @param timeout the length of time in seconds to wait, as an integer.
     */
    public void waitToBePresent(final AppElement element, final int timeout) {
        waitService.waitToBePresent(element, timeout);
    }

    public void waitToBePresent(final AppElement element) {
        waitService.waitToBePresent(element);
    }

    /**
     * Enters text into the specified element, then hides the keyboard.
     *
     * @param element the element into which to enter the text.
     * @param text    the text to be entered.
     */
    public void enterText(final AppElement element, final String text) {
        actionsService.enterText(element, text);
    }

    public void enterText(final AppElement element, final String text, final boolean needClear) {
        actionsService.enterText(element, text, needClear);
    }

    /**
     * Hides the keyboard.
     */
    public void hideKeyboard() {
        if (projectConfig.getPlatformName().equals(DeviceOs.ANDROID.getDeviceOs())) {
            this.driver.hideKeyboard();
        } else {
            this.tapPercent(50, 7);  // taps the title bar
        }
        // Wait for keyboard to fully hide.
        Utils.sleep(1000);
    }

    /**
     * Checks to see if an element can be found on the page.
     *
     * @param element the element to be found.
     * @return true if the element is present; false otherwise.
     */

    public boolean isElementPresent(final AppElement element) {
        return elementService.isElementPresent(element);
    }

    /**
     * Checks to see if an element is currently visible.
     *
     * @param element the element to check for visibility.
     * @return true if the element is visible; false otherwise.
     */
    public boolean isElementVisible(final AppElement element) {
        return elementService.isElementVisible(element);
    }

    /**
     * Taps the specified element.
     *
     * @param element the locator for the element to tap.
     */
    public void tap(final AppElement element) {
        actionsService.tap(element, true);
    }

    /**
     * Taps on the screen using percentages for x/y coordinates.
     *
     * @param widthPercent  the width percentage of where to tap.
     * @param heightPercent the height percentage of where to tap.
     */
    public void tapPercent(final int widthPercent, final int heightPercent) {
        actionsService.tapPercent(widthPercent, heightPercent);
    }

    public void tapPercent(final int heightPercent) {
        this.tapPercent(10, heightPercent);
    }

    /**
     * Taps at a specified (X,Y) point.
     *
     * @param widthX  the x-coordinate of where to tap.
     * @param heightY the y-coordinate of where to tap.
     */
    public void tapAt(final int widthX, final int heightY) {
        actionsService.tapAt(widthX, heightY);
    }

    /**
     * Scrolls up or down until a specified element is visible.
     *
     * @param scrollDirection "UP" or "DOWN".
     * @param element         the By locator to use to find the element.
     */
    public void scrollUntilVisible(final ScrollTo scrollDirection, final By element) {
        scrollService.scrollUntilVisible(scrollDirection, element);
    }

    /**
     * Scrolls up on the page until the element is visible.
     *
     * @param element the By locator to use to find the element.
     */

    public void scrollUpUntilVisible(final AppElement element) {
        elementService.scrollUpUntilVisible(element);
    }

    /**
     * Scrolls down until the element is visible.
     *
     * @param element the By locator to use to find the element.
     */
    public void scrollDownUntilVisible(final AppElement element) {
        elementService.scrollDownUntilVisible(element);
    }

    public void goBackToDashboard() {
        while (isElementVisible(BACK_BUTTON)) {
            tap(BACK_BUTTON);
            scrollService.scrollUp();
        }
    }

    public void longTap(final AppElement element) {
        actionsService.longTap(element);
    }

    public void scrollFromToElement(final AppElement startElement, final AppElement endElement) {
        elementService.scrollFromToElement(startElement, endElement);
    }
}


