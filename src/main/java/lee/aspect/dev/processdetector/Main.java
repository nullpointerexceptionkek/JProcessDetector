package lee.aspect.dev.processdetector;

import lee.aspect.dev.processdetector.core.windows.ProcessMonitor;

public class Main{
    public static void main(String[] args) {
        ProcessMonitor.startMonitoring("Notepad", () -> System.out.println("idea process detected"));
    }
}
