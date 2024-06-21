package com.soonphe.timber.view.utils;

import androidx.annotation.Nullable;

import java.util.IllegalFormatException;

/**
 * 文本相关工具类
 *
 * @author soonphe
 * @since 1.0
 */
public final class StringUtils {
    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    public static boolean isTrimEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static boolean isSpace(String s) {
        if (s == null) {
            return true;
        } else {
            int i = 0;

            for(int len = s.length(); i < len; ++i) {
                if (!Character.isWhitespace(s.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean equals(CharSequence s1, CharSequence s2) {
        if (s1 == s2) {
            return true;
        } else {
            int length;
            if (s1 != null && s2 != null && (length = s1.length()) == s2.length()) {
                if (s1 instanceof String && s2 instanceof String) {
                    return s1.equals(s2);
                } else {
                    for(int i = 0; i < length; ++i) {
                        if (s1.charAt(i) != s2.charAt(i)) {
                            return false;
                        }
                    }

                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public static boolean equalsIgnoreCase(String s1, String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    public static String null2Length0(String s) {
        return s == null ? "" : s;
    }

    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    public static String upperFirstLetter(String s) {
        if (s != null && s.length() != 0) {
            return !Character.isLowerCase(s.charAt(0)) ? s : (char)(s.charAt(0) - 32) + s.substring(1);
        } else {
            return "";
        }
    }

    public static String lowerFirstLetter(String s) {
        if (s != null && s.length() != 0) {
            return !Character.isUpperCase(s.charAt(0)) ? s : (char)(s.charAt(0) + 32) + s.substring(1);
        } else {
            return "";
        }
    }

    public static String reverse(String s) {
        if (s == null) {
            return "";
        } else {
            int len = s.length();
            if (len <= 1) {
                return s;
            } else {
                int mid = len >> 1;
                char[] chars = s.toCharArray();

                for(int i = 0; i < mid; ++i) {
                    char c = chars[i];
                    chars[i] = chars[len - i - 1];
                    chars[len - i - 1] = c;
                }

                return new String(chars);
            }
        }
    }

    public static String toDBC(String s) {
        if (s != null && s.length() != 0) {
            char[] chars = s.toCharArray();
            int i = 0;

            for(int len = chars.length; i < len; ++i) {
                if (chars[i] == 12288) {
                    chars[i] = ' ';
                } else if ('！' <= chars[i] && chars[i] <= '～') {
                    chars[i] -= 'ﻠ';
                } else {
                    chars[i] = chars[i];
                }
            }

            return new String(chars);
        } else {
            return "";
        }
    }

    public static String toSBC(String s) {
        if (s != null && s.length() != 0) {
            char[] chars = s.toCharArray();
            int i = 0;

            for(int len = chars.length; i < len; ++i) {
                if (chars[i] == ' ') {
                    chars[i] = 12288;
                } else if ('!' <= chars[i] && chars[i] <= '~') {
                    chars[i] += 'ﻠ';
                } else {
                    chars[i] = chars[i];
                }
            }

            return new String(chars);
        } else {
            return "";
        }
    }

    public static String format(@Nullable String str, Object... args) {
        String text = str;
        if (str != null && args != null && args.length > 0) {
            try {
                text = String.format(str, args);
            } catch (IllegalFormatException var4) {
                var4.printStackTrace();
            }
        }

        return text;
    }
}
