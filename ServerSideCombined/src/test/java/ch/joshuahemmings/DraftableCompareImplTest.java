package ch.joshuahemmings;

import ch.joshuahemmings.draftable.impl.DraftableCompareImpl;
import ch.joshuahemmings.model.CompareJob;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class DraftableCompareImplTest {


    @Test
    public void testDraftableCompare() {
        DraftableCompareImpl draftableCompare = new DraftableCompareImpl();

        File leftFile = new File("/Users/joshuahemmings/Documents/Dev/School/WIPRO/ServerSideCombined/src/test/java/ch/joshuahemmings/version1.pdf");
        File rightFile = new File("/Users/joshuahemmings/Documents/Dev/School/WIPRO/ServerSideCombined/src/test/resources/version2.pdf");

        System.out.println(rightFile.exists());

        File testFile = new File("src/test/resources/version2.pdf");
        System.out.println(testFile.exists());

        CompareJob compareJob = new CompareJob(leftFile, rightFile);

        draftableCompare.draftableCompare(compareJob);

        System.out.println(compareJob.getCompareResultUrl());
        assertTrue(compareJob.getCompareResultUrl() != null);

    }
}