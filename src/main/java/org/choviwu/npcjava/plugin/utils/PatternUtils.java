package org.choviwu.npcjava.plugin.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {

    public static String replaceUrl(String url) {
        String pattern = "(http(.*?)\\s)";

        Pattern pt = Pattern.compile(pattern);

        Matcher namemacher = pt.matcher(url);

        if (namemacher.find()) {
            url = url.replace(namemacher.group(0), "");

        }
        return url;
    }
}
