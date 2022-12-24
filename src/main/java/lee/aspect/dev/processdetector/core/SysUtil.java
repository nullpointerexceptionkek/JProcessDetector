package lee.aspect.dev.processdetector.core;

public abstract class SysUtil {

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
    public static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }
    public static boolean isLinux() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("nix") || osName.contains("nux") || osName.contains("aix");
    }

}
