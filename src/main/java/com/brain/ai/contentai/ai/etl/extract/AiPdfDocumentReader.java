package com.brain.ai.contentai.ai.etl.extract;

import java.util.List;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class AiPdfDocumentReader {

  public List<Document> getDocsFromPdf(Resource pdfResource) {
    PagePdfDocumentReader pdfReader =
        new PagePdfDocumentReader(
            pdfResource,
            PdfDocumentReaderConfig.builder()
                .withPageTopMargin(0)
                .withPageExtractedTextFormatter(
                    ExtractedTextFormatter.builder().withNumberOfTopTextLinesToDelete(0).build())
                .withPagesPerDocument(1)
                .build());

    return pdfReader.read();
  }
}
