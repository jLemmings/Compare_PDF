package ch.joshuahemmings.draftable;

import ch.joshuahemmings.model.CompareJob;
import com.draftable.api.client.Comparison;
import com.draftable.api.client.Comparisons;
import com.draftable.api.client.Comparisons.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.PropertyResourceBundle;

public class DraftableCompare {

    static final Logger logger = LogManager.getLogger();

    private static String accountId;
    private static String authToken;

    public CompareJob draftableCompare(CompareJob compareJob) {

        connectDraftable();

        File leftFile = new File(String.valueOf(compareJob.getFile1()));
        File rightFile = new File(String.valueOf(compareJob.getFile2()));
        logger.info("Left File: " + leftFile.getAbsolutePath());
        logger.info("Right File: " + rightFile.getAbsolutePath());
        Side left = Side.create(leftFile, "pdf");
        Side right = Side.create(rightFile, "pdf");
        logger.info("Left Document: " + left);
        logger.info("Right Document: " + right);


        Comparisons comparisons = new Comparisons(accountId, authToken);

        logger.info("---CREATING COMPARISON---");
        Comparison comparison;
        try {
            comparison = comparisons.createComparison(left, right);
            String viewerURL = comparisons.signedViewerURL(comparison.getIdentifier(), Duration.ofMinutes(30), false);
            compareJob.setCompareResultUrl(viewerURL);
            logger.info("---COMPARISON COMPLETED---");
            logger.info("Comparison URL: " + viewerURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return compareJob;

    }

    private static void connectDraftable() {
        try {
            // Reading content works
            File file = ResourceUtils.getFile("classpath:draftable.properties");

            FileInputStream fis = new FileInputStream(file);
            PropertyResourceBundle reader = new PropertyResourceBundle(fis);
            accountId = reader.getString("accountId");
            authToken = reader.getString("authToken");
            logger.info("AccountID: " + accountId + ", AuthToken: " + authToken);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
