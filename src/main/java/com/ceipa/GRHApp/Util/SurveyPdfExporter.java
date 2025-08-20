package com.ceipa.GRHApp.Util;

import com.ceipa.GRHApp.Model.DiscapacityResponse;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class SurveyPdfExporter {

    private final List<DiscapacityResponse> responses;

    public SurveyPdfExporter(List<DiscapacityResponse> responses) {
        this.responses = (responses != null) ? responses : Collections.emptyList();
    }

    public void export(HttpServletResponse response) throws IOException, DocumentException {
        if (!response.isCommitted()) {
            response.setContentType("application/pdf");
            if (response.getHeader("Content-Disposition") == null) {
                response.setHeader("Content-Disposition", "attachment; filename=resultados_inclusion_laboral.pdf");
            }
        }

        Document document = new Document(PageSize.A4, 36f, 36f, 36f, 36f);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
        Paragraph title = new Paragraph("Resultados de Encuesta - Inclusión Laboral", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        if (responses.isEmpty()) {
            document.add(new Paragraph("Sin respuestas para este diagnóstico."));
            document.close();
            return;
        }

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10f);
        table.setWidths(new float[]{1.2f, 6.0f, 6.0f});

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        document.close();
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(6f);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.BLACK);

        cell.setPhrase(new Phrase("#", font));          table.addCell(cell);
        cell.setPhrase(new Phrase("Pregunta", font));   table.addCell(cell);
        cell.setPhrase(new Phrase("Respuesta", font));  table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        Font rowFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
        int count = 1;

        for (DiscapacityResponse resp : responses) {
            String question = "—";
            String answer   = "—";

            if (resp != null) {
                if (resp.getQuestion() != null && resp.getQuestion().getDescription() != null) {
                    question = resp.getQuestion().getDescription();
                }
                if (resp.getAnswerOption() != null && resp.getAnswerOption().getText() != null) {
                    answer = resp.getAnswerOption().getText();
                } else if (resp.getManualAnswer() != null && !resp.getManualAnswer().isEmpty()) {
                    answer = resp.getManualAnswer();
                }
            }

            PdfPCell c1 = new PdfPCell(new Phrase(String.valueOf(count++), rowFont));
            PdfPCell c2 = new PdfPCell(new Phrase(question, rowFont));
            PdfPCell c3 = new PdfPCell(new Phrase(answer, rowFont));
            c1.setPadding(5f); c2.setPadding(5f); c3.setPadding(5f);
            c2.setNoWrap(false); c3.setNoWrap(false);

            table.addCell(c1);
            table.addCell(c2);
            table.addCell(c3);
        }
    }
}
