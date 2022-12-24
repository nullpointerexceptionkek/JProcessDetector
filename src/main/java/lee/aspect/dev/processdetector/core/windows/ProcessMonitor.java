package lee.aspect.dev.processdetector.core.windows;

import lee.aspect.dev.processdetector.core.OpenListener;
import lee.aspect.dev.processdetector.core.SysUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;


public class ProcessMonitor {
    private String processName;
    private OpenListener listener;
    private boolean isProcessOpen;
    private long waitDuration;
    private TimeUnit waitDurationUnit;

    private boolean OnlyCallOnce = true;
    private final Object lock = new Object();

    public ProcessMonitor(){}

    public ProcessMonitor(String processName, OpenListener listener) {
        this.processName = processName;
        this.listener = listener;
    }

    public void startMonitoring(String processName, OpenListener listener, long waitDuration, TimeUnit waitDurationUnit) {
        this.processName = processName;
        this.listener = listener;
        this.waitDuration = waitDuration;
        this.waitDurationUnit = waitDurationUnit;
        this.isProcessOpen = isProcessOpen(processName);
        if (isProcessOpen && OnlyCallOnce) {
            listener.onProcessOpen();
        }

        Thread thread = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if(OnlyCallOnce){
                        boolean newIsProcessOpen = isProcessOpen(processName);
                        if (newIsProcessOpen != isProcessOpen) {
                            isProcessOpen = newIsProcessOpen;
                            if (isProcessOpen) {
                                listener.onProcessOpen();
                            }
                        }
                    }
                    else{
                        if (isProcessOpen) {
                            listener.onProcessOpen();
                        }
                    }

                    try {
                        lock.wait(waitDurationUnit.toMillis(waitDuration));
                    } catch (InterruptedException e) {
                        break;
                    }
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
        if(SysUtil.isWindows()){
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
        throw new UnsupportedOperationException("This method is not supported on this operating system.");
    }
}

