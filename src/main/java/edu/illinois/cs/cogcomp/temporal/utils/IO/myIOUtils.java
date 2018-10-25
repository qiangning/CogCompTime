package edu.illinois.cs.cogcomp.temporal.utils.IO;

import edu.illinois.cs.cogcomp.core.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class myIOUtils extends IOUtils {
    @NotNull
    public static String removeLastSeparatorIfExists(String path){
        while(path.endsWith(File.separator))// hopefully we don't have cases like "/foo///.../"
            path = path.substring(0,path.length()-1);
        return path;
    }
    public static String getParentDir(String path){
        path = removeLastSeparatorIfExists(path);
        int slashIndex = path.lastIndexOf(File.separator);
        return path.substring(0,slashIndex);
    }
    public static String getFileOrDirName(String path) {
        path = removeLastSeparatorIfExists(path);
        int slashIndex = path.lastIndexOf(File.separator);
        return path.substring(slashIndex + 1);
    }
}
