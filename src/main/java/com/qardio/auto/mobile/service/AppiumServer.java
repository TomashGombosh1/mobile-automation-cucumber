package com.qardio.auto.mobile.service;

import java.io.IOException;
import java.net.ServerSocket;

import com.qardio.auto.mobile.config.ApplicationConfig;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author Tomash Gombosh
 */
@Log4j
public class AppiumServer implements AppiumService {
    private final AppiumDriverLocalService service = buildService();
    private final ApplicationConfig applicationConfig = new ApplicationConfig();

    @Override
    public void startServer() {
        service.start();
        log.info(String.format("Start appium service on the %s", applicationConfig.getAppiumUrl()));
    }

    @Override
    public void stopServer() {
        service.stop();
        log.info(String.format("Stop appium service on the %s", applicationConfig.getAppiumUrl()));
    }

    @Override
    public boolean checkIfServerIsRunning(final int port) {

        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            //If control comes here, then it means that the port is in use
            isServerRunning = true;
        }
        return isServerRunning;
    }

    private AppiumDriverLocalService buildService() {
        final AppiumServiceBuilder builder = new AppiumServiceBuilder();
        final DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("noReset", "false");
        builder.withIPAddress(new ApplicationConfig().getAppiumIp());
        builder.usingPort(Integer.parseInt(new ApplicationConfig().getAppiumPort()));
        builder.withCapabilities(cap);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
        return AppiumDriverLocalService.buildService(builder);
    }
}
