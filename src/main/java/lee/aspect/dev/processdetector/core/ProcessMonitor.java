package lee.aspect.dev.processdetector.core;

import com.sun.istack.internal.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;


public class ProcessMonitor {
    private final Object lock = new Object();
    private boolean isProcessOpen;
    private boolean processCloseCalled = true;
    private Thread thread;

    public ProcessMonitor(boolean processCloseCalled) {
        this.processCloseCalled = processCloseCalled;
    }

    public ProcessMonitor() {
    }

    @SuppressWarnings("Duplicates")
    private static boolean isProcessOpen(String processName) {
        if (SysUtil.isWindows()) {
            try {
                Process process = Runtime.getRuntime().exec("tasklist");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains(processName)) {
                        reader.close();
                        return true;
                    }
                }
                reader.close();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if (SysUtil.isMac() || SysUtil.isLinux()) {
            try {
                //Process process = Runtime.getRuntime().exec("ps -e");
                Process process = Runtime.getRuntime().exec("pgrep " + processName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                if(reader.readLine() != null) {
                    reader.close();
                    return true;
                }
                /*
                String line;
                while ((line = reader.readLine()) != null) {

                    if (line.contains(processName)) {
                        return true;
                    }
                }

                 */
                reader.close();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        throw new UnsupportedOperationException("This method is not supported on this operating system.");
    }

    public void startMonitoring(String processName, @NotNull OpenCloseListener listener, long waitDuration, TimeUnit waitDurationUnit) {
        this.isProcessOpen = isProcessOpen(processName);
        if (isProcessOpen) {
            listener.onProcessOpen();
        }

        thread = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    boolean newIsProcessOpen = isProcessOpen(processName);
                    if (newIsProcessOpen != isProcessOpen) {
                        isProcessOpen = newIsProcessOpen;
                        if (isProcessOpen) {
                            processCloseCalled = false;
                            listener.onProcessOpen();
                        }
                    } else if (!newIsProcessOpen) {
                        if (!processCloseCalled) {
                            listener.onProcessClose();
                            processCloseCalled = true;
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
        if(thread != null) {
            thread.interrupt();
        }
    }
}

