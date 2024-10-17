package ru.shift.io.parsingParameter;

public class UtilOptions {
    private String outputFileName;
    private WriteMode writeMode;
    private String inputFiles;

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFilePath) {
        this.outputFileName = outputFilePath;
    }

    public WriteMode getWriteMode() {
        return writeMode;
    }

    public void setWriteMode(WriteMode writeMode) {
        this.writeMode = writeMode;
    }

    public String getInputFiles() {
        return inputFiles;
    }

    public void setInputFiles(String inputFiles) {
        this.inputFiles = inputFiles;
    }
}
