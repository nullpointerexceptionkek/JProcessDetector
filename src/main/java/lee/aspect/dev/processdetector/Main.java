package lee.aspect.dev.processdetector;

import lee.aspect.dev.processdetector.core.windows.ProcessMonitor;

import java.util.concurrent.TimeUnit;

public class Main{
    public static void main(String[] args) {

        ProcessMonitor pm2 = new ProcessMonitor();
        pm2.startMonitoring("chrome.exe", () -> System.out.println("Chrome is open!"),1,TimeUnit.SECONDS);

    }
}
