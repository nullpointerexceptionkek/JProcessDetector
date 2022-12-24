package lee.aspect.dev.processdetector;

import lee.aspect.dev.processdetector.core.windows.ProcessMonitor;

public class Main{
    public static void main(String[] args) {
        ProcessMonitor pm = new ProcessMonitor();
        pm.startMonitoring("Notepad.exe", () -> System.out.println("Notepad is open!"));
        ProcessMonitor pm2 = new ProcessMonitor();
        pm2.startMonitoring("chrome.exe", () -> System.out.println("Chrome is open!"));
    }
}
