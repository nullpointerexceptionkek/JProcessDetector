package lee.aspect.dev.processdetector;

import lee.aspect.dev.processdetector.core.OpenCloseListener;
import lee.aspect.dev.processdetector.core.ProcessMonitor;

import java.util.concurrent.TimeUnit;

public class Main{
    public static void main(String[] args) {
        new ProcessMonitor().startMonitoring("firefox", new OpenCloseListener() {
            @Override
            public void onProcessOpen() {
                System.out.println("Process Opened");
            }

            @Override
            public void onProcessClose() {
                System.out.println("Process Closed");
            }
        }, 1, TimeUnit.SECONDS);
    }
}
