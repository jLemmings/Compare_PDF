package ch.joshuahemmings;

import ch.joshuahemmings.model.CompareJob;
import ch.joshuahemmings.pdfDiff.PdfDiffCompare;
import ch.joshuahemmings.pdfDiff.impl.PdfDiffCompareImpl;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PdfDiffCompareImplTest {

    @Test
    public void testDraftableCompareDifferentDocuments() {
        PdfDiffCompare pdfDiffCompare = new PdfDiffCompareImpl();

        File leftFile = new File("/Users/joshuahemmings/Documents/Dev/School/WIPRO/ServerSideCombined/src/test/java/ch/joshuahemmings/version1.pdf");
        File rightFile = new File("/Users/joshuahemmings/Documents/Dev/School/WIPRO/ServerSideCombined/src/test/java/ch/joshuahemmings/version2.pdf");

        CompareJob compareJob = new CompareJob(leftFile, rightFile);

        ResponseEntity<byte[]> responseEntity = pdfDiffCompare.pdfCompare(compareJob);


        System.out.println(responseEntity != null);
        assertTrue(responseEntity != null);
    }

    @Test
    public void testDraftableCompareSameDocuments() {
        PdfDiffCompare pdfDiffCompare = new PdfDiffCompareImpl();

        File leftFile = new File("/Users/joshuahemmings/Documents/Dev/School/WIPRO/ServerSideCombined/src/test/java/ch/joshuahemmings/version1.pdf");

        CompareJob compareJob = new CompareJob(leftFile, leftFile);

        ResponseEntity<byte[]> responseEntity = pdfDiffCompare.pdfCompare(compareJob);


        assertTrue(responseEntity.getBody() == null);
    }
}