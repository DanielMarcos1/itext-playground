package dev.danielmarcos.model;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class DocumentConversor {

    public static void pdftoDocx(String pdfPath, String docxPath) throws IOException {
        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String pdfText = pdfTextStripper.getText(document);

            // Split the text into lines based on single newlines (\n)
            String[] lines = pdfText.split("\n");

            try (XWPFDocument docxDocument = new XWPFDocument()) {
                XWPFParagraph paragraph = docxDocument.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.LEFT);
                boolean isFirstLine = true;

                // Iterate through each line and group them into paragraphs
                for (String line : lines) {
                    // Ignore empty lines (they will not be part of a paragraph)
                    if (line.trim().isEmpty()) {
                        // Add space between paragraphs if needed
                        paragraph.setSpacingAfter(200);  // Optional: Space after the paragraph
                        paragraph = docxDocument.createParagraph();  // Create new paragraph for next part
                        paragraph.setAlignment(ParagraphAlignment.LEFT);
                        isFirstLine = true;
                        continue;
                    }

                    // Add the text to the current paragraph
                    XWPFRun run = paragraph.createRun();
                    if (isFirstLine) {
                        // Apply any specific formatting if the paragraph is the first line (e.g., bold, etc.)
                        isFirstLine = false;
                    }
                    run.setText(line);

                    // Set paragraph spacing between lines (for visual break)
                    paragraph.setSpacingAfter(100);  // Optional: Control space between lines within the paragraph
                }

                // Write the document to a file
                try (FileOutputStream fos = new FileOutputStream(docxPath)) {
                    docxDocument.write(fos);
                }
            }
        }
    }
}
