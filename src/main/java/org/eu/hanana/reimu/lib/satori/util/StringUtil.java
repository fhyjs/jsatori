package org.eu.hanana.reimu.lib.satori.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StringUtil {
    public static String getFullErrorMessage(Throwable throwable){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        throwable.printStackTrace(new PrintStream(outputStream));
        return outputStream.toString();
    }
}
