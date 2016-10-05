package de.thorbenkuck.rhfw.handler.scanner;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PathScanner {

    private static final char PKG_SEPARATOR = '.';

    private static final char DIR_SEPARATOR = '/';

    private static final String CLASS_FILE_SUFFIX = ".class";

    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

    private List<String> packagesToScan;

    private PathScanner() {
    }

    public PathScanner(List<String> packagesToScan) {
        this();
        this.packagesToScan = packagesToScan;
    }

    public PathScanner(String packageToScan) {
        this();
        this.packagesToScan = new ArrayList<>();
        this.packagesToScan.add(packageToScan);
    }

    public List<Class<?>> find() {
        int packageCount = packagesToScan.size();
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for(int i = 0 ; i < packageCount ; i++) {
            //System.out.print("$ Classes found: ");
            String scannedPath = packagesToScan.get(i).replace(PKG_SEPARATOR, DIR_SEPARATOR);
            URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
            if (scannedUrl == null) {
                throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, packagesToScan.get(i)));
            }
            File scannedDir = new File(scannedUrl.getFile());
            for (File file : scannedDir.listFiles()) {
                classes.addAll(find(file, packagesToScan.get(i)));
            }
            //System.out.println(classes.size() + " in scanned package: "+packagesToScan.get(i));
        }

        return classes;
    }

    private List<Class<?>> find(File file, String scannedPackage) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        String resource = scannedPackage + PKG_SEPARATOR + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
            int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }
}
