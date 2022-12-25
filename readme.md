# JProcessDetection
![MIT License](https://img.shields.io/github/license/nullpointerexceptionkek/JProcessDetector?style=flat-square)

## What is this?
This is a simple library for detecting processes on Windows, macOS, and Linux.
For example, you can check if the user have notepad.exe running(Windows)
You can also add a listener to the process, so you can detect when the process is started or stopped and run some code when it happens.

## How to use?
You will need to instantiate the ProcessDetector class, and override the OpenClose interface.
``` java
new ProcessMonitor().startMonitoring("Notepad.exe", new OpenCloseListener() {
            @Override
            public void onProcessOpen() {
                System.out.println("Process Opened");
            }

            @Override
            public void onProcessClose() {
                System.out.println("Process Closed");
            }
        }, 1, TimeUnit.SECONDS);
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
