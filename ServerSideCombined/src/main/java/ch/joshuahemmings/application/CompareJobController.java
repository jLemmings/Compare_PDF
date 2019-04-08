package ch.joshuahemmings.application;

import ch.joshuahemmings.draftable.DraftableCompare;
import ch.joshuahemmings.model.CompareJob;
import ch.joshuahemmings.pdfDiff.PdfDiffCompare;
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


@RestController
public class CompareJobController {

    private static final Logger logger = LogManager.getLogger();

    // TODO: Must be deleted, only for debugging
    @PostMapping("/demo")
    ResponseEntity diffDemo(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) {
        System.out.println("File 1: " + file1);
        System.out.println("File 2: " + file2);
        String url = "https://aintevenmad.ch";
        return new ResponseEntity<>("{\"url\" : \" " + url + " \"}", HttpStatus.OK);
    }

    @PostMapping("/pdfcompare")
    ResponseEntity<byte[]> pdfcompare(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) {
        logger.info("Received File 1: " + file1.getOriginalFilename() + ", File 2: " + file2.getOriginalFilename());
        PdfDiffCompare pdfDiffCompare = new PdfDiffCompare();
        File converted1 = convert(file1);
        logger.info("File 1: " + converted1);
        File converted2 = convert(file2);
        logger.info("File 2: " + converted2);

        CompareJob compareJob = new CompareJob(converted1, converted2);
        logger.info("Compare Job after convert. F1: " + compareJob.getFile1() + " F2: " + compareJob.getFile2());

        ResponseEntity<byte[]> returnComparison = pdfDiffCompare.pdfCompare(compareJob);

        logger.info(returnComparison);
        return returnComparison;
    }

    @PostMapping("/draftable")
    ResponseEntity compareJob(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) {
        CompareJob compareJob = new CompareJob(convert(file1), convert(file2));
        DraftableCompare draftableCompare = new DraftableCompare();
        draftableCompare.draftableCompare(compareJob);
        return new ResponseEntity<>("{\"url\" : \" " + compareJob.getCompareResultUrl() + " \"}", HttpStatus.OK);
    }

    private File convert(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
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


}
