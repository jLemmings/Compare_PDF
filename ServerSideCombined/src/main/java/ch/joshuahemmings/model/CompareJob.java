package ch.joshuahemmings.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.OutputStream;


@Data
@NoArgsConstructor
public class CompareJob {
    private File file1;
    private File file2;

    private String compareResultUrl;
    private OutputStream compareResultOutputStream;
    private File compareResultFile;

    public CompareJob(File file1, File file2) {
        this.file1 = file1;
        this.file2 = file2;
    }
}
