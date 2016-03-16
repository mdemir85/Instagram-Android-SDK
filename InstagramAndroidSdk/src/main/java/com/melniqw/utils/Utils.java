/*******************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Alexey <menliqw> Melnikov.
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
 ******************************************************************************/

package com.melniqw.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static final String TAG = Utils.class.getName();

    public static String extractPattern(String string, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(string);
        if(!m.find())
            return null;
        return m.toMatchResult().group(1);
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        InputStreamReader r = new InputStreamReader(is);
        StringWriter sw = new StringWriter();
        char[] buffer = new char[1024];
        try {
            for(int n; (n = r.read(buffer)) != -1;)
                sw.write(buffer, 0, n);
        }
        finally{
            try {
                is.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sw.toString();
    }

    public static void closeStream(Object oin) {
        if(oin != null)
            try {
                if(oin instanceof InputStream)
                    ((InputStream)oin).close();
                if(oin instanceof OutputStream)
                    ((OutputStream)oin).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private static String patternStringProfileId = "^(id)?(\\d{1,10})$";
    private static Pattern patternProfileId = Pattern.compile(patternStringProfileId);
    public static String parseProfileId(String text) {
        Matcher m = patternProfileId.matcher(text);
        if (!m.find())
            return null;
        return m.group(2);
    }
}
