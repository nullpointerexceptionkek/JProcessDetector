package lee.aspect.dev.processdetector.core.windows;

import lee.aspect.dev.processdetector.core.OpenListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ProcessMonitor {
    private static OpenListener listener;
    private static String processName;

    public void startMonitoring(String processName, OpenListener listener) {
        ProcessMonitor.processName = processName;
        ProcessMonitor.listener = listener;

        Thread thread = new Thread(() -> {
            while (true) {
                if (isProcessOpen(processName)) {
                    listener.onProcessOpen();
                }

                try {
                    Thread.sleep(1000); // sleep for 1 second
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        thread.start();
    }

    public void stopMonitoring() {
        processName = null;
        listener = null;
    }

    private static boolean isProcessOpen(String processName) {
        try {
            Process process = Runtime.getRuntime().exec("tasklist");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                if (line.contains(processName)) {
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

