package ch.joshuahemmings.application;

import ch.joshuahemmings.draftable.impl.DraftableCompareImpl;
import ch.joshuahemmings.model.CompareJob;
import ch.joshuahemmings.pdfDiff.impl.PdfDiffCompareImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;


@RestController
public class CompareJobController {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Spring Post Mapping for pdfcompare Library
     * @param file1, left file
     * @param file2, right file
     */
    @PostMapping("/pdfcompare")
    ResponseEntity<byte[]> pdfCompare(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) {
        logger.info("Received File 1: " + file1.getOriginalFilename() + ", File 2: " + file2.getOriginalFilename());
        PdfDiffCompareImpl pdfDiffCompare = new PdfDiffCompareImpl();
        File converted1 = convert(file1);
        logger.info("File 1: " + converted1);
        File converted2 = convert(file2);
        logger.info("File 2: " + converted2);

        CompareJob compareJob = new CompareJob(converted1, converted2);
        logger.info("Compare Job after convert. F1: " + compareJob.getFile1() + " F2: " + compareJob.getFile2());

        ResponseEntity<byte[]> returnComparison = pdfDiffCompare.pdfCompare(compareJob);
        logger.info(returnComparison);

        // Delete all PDFs
        cleanUp();
        return returnComparison;
    }

    /**
     * Spring Post Mapping for Draftable API
     * @param file1, left file
     * @param file2, right file
     */
    @PostMapping("/draftable")
    ResponseEntity draftableCompare(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) {
        CompareJob compareJob = new CompareJob(convert(file1), convert(file2));
        DraftableCompareImpl draftableCompare = new DraftableCompareImpl();
        draftableCompare.draftableCompare(compareJob);
        cleanUp();
        return new ResponseEntity<>("{\"url\" : \" " + compareJob.getCompareResultUrl() + " \"}", HttpStatus.OK);
    }

    /**
     * Convert MultipartFile to Java.io.File
     * @param file
     */
    private File convert(MultipartFile file) {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convFile;
    }

    /**
     * Deletes every PDF file in project directory
     */
    private void cleanUp() {
        File folder = new File(".");
        File fileList[] = folder.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            String pes = fileList[i].getName();
            if (pes.endsWith(".pdf")) {
                logger.info("DELETING FILE: " + fileList[i]);
                fileList[i].delete();
            }
        }
    }


}
