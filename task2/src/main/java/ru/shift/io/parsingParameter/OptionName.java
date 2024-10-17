package ru.shift.io.parsingParameter;

public enum OptionName {
    FILE_WRITE("file"),
    CONSOLE_WRITE("console"),
    HELP("h");

    private final String optionName;

    OptionName(String name) {
        this.optionName = name;
    }

    public String getOptionName() {
        return optionName;
    }
}
