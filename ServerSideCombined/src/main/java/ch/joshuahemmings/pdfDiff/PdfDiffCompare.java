package ch.joshuahemmings.pdfDiff;

import ch.joshuahemmings.model.CompareJob;
import de.redsix.pdfcompare.CompareResult;
import de.redsix.pdfcompare.CompareResultWithPageOverflow;
import de.redsix.pdfcompare.PdfComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

public class PdfDiffCompare {
    private static final Logger logger = LogManager.getLogger();

    public ResponseEntity<byte[]> pdfCompare(CompareJob compareJob) {
        logger.info(compareJob);
        File leftFile = compareJob.getFile1();
        File rightFile = compareJob.getFile2();

        byte[] document = null;
        HttpHeaders header = new HttpHeaders();
        try {
            final CompareResult result = new PdfComparator(leftFile, rightFile, new CompareResultWithPageOverflow()).compare();

            if (result.isEqual()) {
                logger.info("Compare job is: equal");
            } else {
                String path = System.getProperty("java.io.tmpdir");
                System.out.println("File written to: " + path);
                compareJob.setCompareResultFile(new File(path + "demo.pdf"));
                result.writeTo(path + "demo");
                logger.info("Compared file path: " + compareJob.getCompareResultFile());

                document = FileCopyUtils.copyToByteArray(compareJob.getCompareResultFile());
                logger.info(document);
                header.setContentType(new MediaType("application", "pdf"));
                header.set("Content-Disposition", "inline; filename=demo.pdf");
                header.setContentLength(document.length);
                logger.info(document);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(document, header, HttpStatus.OK);
    }

}
