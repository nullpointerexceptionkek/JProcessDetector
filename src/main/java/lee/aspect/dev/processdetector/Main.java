/*
 *
 * MIT License
 *
 * Copyright (c) 2022 lee
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package lee.aspect.dev.processdetector;

import lee.aspect.dev.processdetector.core.OpenCloseListener;
import lee.aspect.dev.processdetector.core.ProcessMonitor;

import java.util.concurrent.TimeUnit;

public class Main{
    public static void main(String[] args) {
        boolean isOpen = ProcessMonitor.isProcessOpen("Notepad.exe");
        System.out.println("Is Notepad open? " + isOpen);

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
    }
}
