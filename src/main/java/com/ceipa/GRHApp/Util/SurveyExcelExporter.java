package com.ceipa.GRHApp.Util;


import com.ceipa.GRHApp.Model.Organization;
import com.ceipa.GRHApp.Model.Survey;
import com.ceipa.GRHApp.Model.SurveyDetail;
import com.ceipa.GRHApp.Service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Slf4j
@Component
public class SurveyExcelExporter  {

    @Autowired
    private SurveyService surveyService;


    private XSSFWorkbook workbook;
    private XSSFSheet sheet;


    private void writeHeaderLine() {
        this.workbook = new XSSFWorkbook();
        this. sheet = workbook.createSheet("Resultados encuesta");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Organizacion", style);
        createCell(row, 1, "Nombre del Usuario", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines(int diagnosticId) {
        List<Survey> surveyList =  surveyService.surveyList(diagnosticId);

        if (Objects.nonNull(surveyList) && !surveyList.isEmpty()) {
            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setFontHeight(14);
            style.setFont(font);
            int rowQuestionPosition = 1;

            Organization oldOrganization = null;
            boolean changeOrganization = false;
            for (Survey s : surveyList) {

                if (Objects.nonNull(s.getUserAccount().getOrganization())) {
                    if (Objects.isNull(oldOrganization)) {
                        oldOrganization = s.getUserAccount().getOrganization();
                        changeOrganization = true;
                    } else if (oldOrganization != s.getUserAccount().getOrganization()) {
                        oldOrganization = s.getUserAccount().getOrganization();
                        changeOrganization = true;
                    }
                } else {
                    oldOrganization =null;
                }


                if (s.getCompletedStatus()) {
                    Row rowQuestion = sheet.createRow(rowQuestionPosition);
                    Row rowAnswer = sheet.createRow(++rowQuestionPosition);
                    int columnCount = 2;
                    createCell(rowQuestion, 1, s.getUserAccount().getName(), style);
                    if(changeOrganization && Objects.nonNull(oldOrganization)) {
                        createCell(rowQuestion, 0, oldOrganization.getName(), style);
                        changeOrganization = false;
                    }
                    for (SurveyDetail sd : s.getSurveyDetailList()) {

                        if (sd.getAnswer().getId() != 999) {
                            createCell(rowQuestion, columnCount, sd.getQuestion().getId(), style);
                            createCell(rowAnswer, columnCount, sd.getAnswer().getId(), style);
                            columnCount++;
                        }

                        if (sd.getSubQuestion().getId() != 999) {
                            createCell(rowQuestion, columnCount, ""+sd.getQuestion().getId()+"."+sd.getSubQuestion().getId(), style);
                            createCell(rowAnswer, columnCount, sd.getSubAnswer().getScore(), style);
                            columnCount++;
                        }
                    }
                }
                rowQuestionPosition++;
            }
        }

    }

    public void export(HttpServletResponse response, int diagnosticId) throws IOException {

        try {
            writeHeaderLine();
            writeDataLines(diagnosticId);

            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (Exception e ) {
            log.error("Error al generar el excel " + e.getMessage());
        }

    }

}
