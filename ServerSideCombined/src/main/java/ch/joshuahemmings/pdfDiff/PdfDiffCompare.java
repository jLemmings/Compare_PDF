package ch.joshuahemmings.pdfDiff;

import ch.joshuahemmings.model.CompareJob;
import org.springframework.http.ResponseEntity;

public interface PdfDiffCompare {
    ResponseEntity<byte[]> pdfCompare(CompareJob compareJob);
}
