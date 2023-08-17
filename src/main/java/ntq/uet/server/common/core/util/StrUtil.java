package ntq.uet.server.common.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

public class StrUtil extends StringUtils {
    public static final String SEPARATOR = ",";
    private static final String NUMBER_SET = "12345678901";
    private static Random random = new Random();

    public StrUtil() {
    }

    public static String cut(String input, int cutLength) {
        return substring(input, 0, cutLength);
    }

    public static String join(List<String> stringList) {
        return StringUtils.join(stringList, ",");
    }

    public static String join(String[] stringArray) {
        return StringUtils.join(stringArray, ",");
    }

    public static String[] split(String joinedStr) {
        return joinedStr == null ? null : joinedStr.split(",");
    }

    public static String[] toStringArray(List<String> stringList) {
        return (String[])stringList.toArray(new String[stringList.size()]);
    }

    public static List<String> splitToList(String joinedStr) {
        return joinedStr == null ? null : Arrays.asList(joinedStr.split(","));
    }

    public static String[] toSnakeCase(String[] camelCaseStrArray) {
        if (camelCaseStrArray == null) {
            return null;
        } else {
            String[] snakeCaseArray = new String[camelCaseStrArray.length];

            for(int i = 0; i < camelCaseStrArray.length; ++i) {
                snakeCaseArray[i] = toSnakeCase(camelCaseStrArray[i]);
            }

            return snakeCaseArray;
        }
    }

    public static List<String> toSnakeCase(List<String> camelCaseStrArray) {
        if (camelCaseStrArray == null) {
            return null;
        } else {
            List<String> snakeCaseList = new ArrayList(camelCaseStrArray.size());
            Iterator var2 = camelCaseStrArray.iterator();

            while(var2.hasNext()) {
                String camelCaseStr = (String)var2.next();
                snakeCaseList.add(toSnakeCase(camelCaseStr));
            }

            return snakeCaseList;
        }
    }

    public static String toSnakeCase(String camelCaseStr) {
        if (Validator.isEmpty(camelCaseStr)) {
            return null;
        } else if (camelCaseStr.toLowerCase().equals(camelCaseStr)) {
            return camelCaseStr;
        } else if (camelCaseStr.toUpperCase().equals(camelCaseStr)) {
            return camelCaseStr.toLowerCase();
        } else {
            char[] chars = camelCaseStr.toCharArray();
            StringBuilder sb = new StringBuilder();
            char[] var3 = chars;
            int var4 = chars.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                char c = var3[var5];
                if (Character.isUpperCase(c) && sb.length() > 0) {
                    sb.append("_");
                }

                sb.append(Character.toLowerCase(c));
            }

            return sb.toString();
        }
    }

    public static String toLowerCaseCamel(String snakeCaseStr) {
        if (Validator.isEmpty(snakeCaseStr)) {
            return null;
        } else if (!snakeCaseStr.contains("_")) {
            return snakeCaseStr.toUpperCase().equals(snakeCaseStr) ? snakeCaseStr.toLowerCase() : uncapFirst(snakeCaseStr);
        } else {
            StringBuilder sb = null;
            String[] words = snakeCaseStr.split("_");
            String[] var3 = words;
            int var4 = words.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String word = var3[var5];
                if (!Validator.isEmpty(word)) {
                    if (sb == null) {
                        sb = new StringBuilder(word.toLowerCase());
                    } else {
                        sb.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase());
                    }
                }
            }

            if (snakeCaseStr.endsWith("_")) {
                sb.append("_");
            }

            return sb != null ? sb.toString() : null;
        }
    }

    public static Long toLong(String strValue) {
        return toLong(strValue, (Long)null);
    }

    public static Long toLong(String strValue, Long defaultLong) {
        return Validator.isEmpty(strValue) ? defaultLong : Long.parseLong(strValue);
    }

    public static Integer toInt(String strValue) {
        return toInt(strValue, (Integer)null);
    }

    public static Integer toInt(String strValue, Integer defaultInt) {
        return Validator.isEmpty(strValue) ? defaultInt : Integer.parseInt(strValue);
    }

    public static boolean toBoolean(String strValue) {
        return toBoolean(strValue, false);
    }

    public static boolean toBoolean(String strValue, boolean defaultBoolean) {
        return Validator.notEmpty(strValue) ? Validator.isTrue(strValue) : defaultBoolean;
    }

    public static String removeDuplicateBlank(String input) {
        return Validator.isEmpty(input) ? input : input.trim().replaceAll(" +", " ");
    }

    public static String newUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String valueOf(Object o) {
        return o == null ? null : String.valueOf(o);
    }

    public static String newRandomNum(int length) {
        StringBuilder sb = new StringBuilder();
        sb.append("12345678901".charAt(random.nextInt(9)));

        for(int i = 1; i < length; ++i) {
            sb.append("12345678901".charAt(random.nextInt(10)));
        }

        return sb.toString();
    }

    public static String uncapFirst(String input) {
        return input != null ? Character.toLowerCase(input.charAt(0)) + input.substring(1) : null;
    }

    public static String capFirst(String input) {
        return input != null ? Character.toUpperCase(input.charAt(0)) + input.substring(1) : null;
    }

    public static List<String> readLines(InputStream input, String charset) throws IOException {
        InputStreamReader inputReader = new InputStreamReader(input, Charset.forName(charset));
        BufferedReader reader = new BufferedReader(inputReader);
        List<String> list = new ArrayList();

        String line;
        while((line = reader.readLine()) != null) {
            list.add(line);
        }

        return list;
    }

    public static String removeEsc(String columnName) {
        if (Validator.isEmpty(columnName)) {
            return columnName;
        } else {
            return startsWithAny(columnName, new CharSequence[]{"`", "\"", "["}) ? substring(columnName, 1, columnName.length() - 1) : columnName;
        }
    }

    public static String replace(CharSequence str, int startInclude, int endExclude, char replacedChar) {
        if (isEmpty(str)) {
            return valueOf(str);
        } else {
            int strLength = str.length();
            if (startInclude > strLength) {
                return valueOf(str);
            } else {
                if (endExclude > strLength) {
                    endExclude = strLength;
                }

                if (startInclude > endExclude) {
                    return valueOf(str);
                } else {
                    char[] chars = new char[strLength];

                    for(int i = 0; i < strLength; ++i) {
                        if (i >= startInclude && i < endExclude) {
                            chars[i] = replacedChar;
                        } else {
                            chars[i] = str.charAt(i);
                        }
                    }

                    return new String(chars);
                }
            }
        }
    }

}
