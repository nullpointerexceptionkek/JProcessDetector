# JProcessDetection
![MIT License](https://img.shields.io/github/license/nullpointerexceptionkek/JProcessDetector?style=flat-square)

## What is this?
This is a simple library for detecting processes on Windows, macOS, and Linux.
For example, you can check if the user have notepad.exe running(Windows)
You can also add a listener to the process, so you can detect when the process is started or stopped and run some code when it happens.

## Support

| OS      | Support                    |
|---------|----------------------------|
| Windows | ✔️                         |
| macOS   | Implemented but not tested |
| Linux   | ✔️                         |

## How to use?
You will need to instantiate a ScheduledExecutorService and then the ProcessDetector class, and override the OpenClose interface.
``` java
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

        new ProcessMonitor(scheduler, "Notepad.exe", new OpenCloseListener() {
            @Override
            public void onProcessOpen() {
                System.out.println("notepad Opened");
            }

            @Override
            public void onProcessClose() {
                System.out.println("notepad Closed");
            }
        }, 1, TimeUnit.SECONDS).startMonitoring();

        new ProcessMonitor(scheduler, "cmd.exe", new OpenCloseListener() {
            @Override
            public void onProcessOpen() {
                System.out.println("cmd Opened");
            }

            @Override
            public void onProcessClose() {
                System.out.println("cmd Closed");
            }
        }, 1, TimeUnit.SECONDS).startMonitoring();
    }
```
You could also use the static method to check if a process exists or not at the current time.
```java
class Main{
    public static void main(String[]args){
        boolean isOpen=ProcessMonitor.isProcessOpen("Notepad.exe");
        System.out.println("Is Notepad open? "+isOpen);
    }
}
```

## How to add?
You can copy the source code directly and change to your project, or you can add it as a dependency to your project(as a jar).
