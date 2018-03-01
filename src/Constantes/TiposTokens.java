package Constantes;

import Logica.Token;

import java.util.ArrayList;
import java.util.regex.Pattern;

public final class TiposTokens {
    public static final String INTEGER="INTEGER";
    public static final String DOUBLE="DOUBLE";
    public static final String STRING="STRING";
    public static final String CHAR="CHAR";
    public static final String BOOLEAN="BOOLEAN";
    public static final String PLUS="PLUS";
    public static final String EOF="EOF";
    public static final String MINUS="MINUS";
    public static final String TIMES="TIMES";
    public static final String DIV="DIV";
    public static final String MOD="MOD";


    public static final ArrayList<Token> arregloToken=new ArrayList<Token>();
    public static final String[] KEYWORDS = new String[] {
             "note","translate","alarm","email","map","convert","buy","call",
            "show","int","string","char","boolean","double"
    };

    public static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    public static final String PAREN_PATTERN = "\\(|\\)";
    public static final String BRACE_PATTERN = "\\{|\\}";
    public static final String BRACKET_PATTERN = "\\[|\\]";
    public static final String SEMICOLON_PATTERN = "\\;";
    public static final String STRING_PATTERN = "\'([^\"\\\\]|\\\\.)*\'";
    public static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    public static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    public static final String sampleCode = String.join("\n", new String[] {
            "package com.example;",
            "",
            "import java.util.*;",
            "",
            "public class Foo extends Bar implements Baz {",
            "",
            "    /*",
            "     * multi-line comment",
            "     */",
            "    public static void main(String[] args) {",
            "        // single-line comment",
            "        for(String arg: args) {",
            "            if(arg.length() != 0)",
            "                System.out.println(arg);",
            "            else",
            "                System.err.println(\"Warning: empty string as argument\");",
            "        }",
            "    }",
            "",
            "}"
    });
}
