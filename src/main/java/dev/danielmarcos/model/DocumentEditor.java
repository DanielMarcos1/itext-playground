package dev.danielmarcos.model;

import lombok.Getter;
import lombok.Setter;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DocumentEditor {

    public void editDocument(String inputFilePath, String outputFilePath, Map<String, String> replacements) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputFilePath);
            XWPFDocument document = new XWPFDocument(fis)) {

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                List<XWPFRun> runs = paragraph.getRuns();
                if(runs != null) {
                    for (XWPFRun run : runs) {
                        String text = run.getText(0);
                        if (text != null) {
                            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                                if (text.contains(entry.getKey())) {
                                    text = text.replace(entry.getKey(), entry.getValue());
                                }
                            }
                            run.setText(text, 0);
                        }
                    }
                }
            }

            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                document.write(fos);
            }
        }
    }
}
