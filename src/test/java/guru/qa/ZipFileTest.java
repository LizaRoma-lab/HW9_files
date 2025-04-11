package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ZipFileTest {
    private ClassLoader cl = FilesParsingTest.class.getClassLoader();

    @Test
    void parsePdfTest() throws Exception {
        try (InputStream is = getFileFromZip("zip/Downloads.zip", ".pdf")) {
            assertNotNull(is, "Файл .pdf не найден в архиве");
            PDF pdf = new PDF(is);
            assertTrue(pdf.text.contains("Согласие клиента на обработку ПД"), "PDF-файл не содержит 'Согласие клиента на обработку ПД'");

        }
    }

    @Test
    void parseExcelTest() throws Exception {
        try (InputStream is = getFileFromZip("zip/Downloads.zip", ".xlsx")) {
            assertNotNull(is, "Файл .xlsx не найден в архиве");

            XLS xls = new XLS(is.readAllBytes());

            boolean containsFootball = xls.excel.sheetIterator()
                    .next()
                    .rowIterator()
                    .hasNext() && xls.excel.getSheetAt(0)
                    .rowIterator()
                    .next()
                    .cellIterator()
                    .hasNext() && xls.excel.getSheetAt(0)
                    .rowIterator()
                    .next()
                    .cellIterator()
                    .next()
                    .toString()
                    .contains("Футбол");

            assertTrue(containsFootball, "Excel-файл не содержит 'Футбол'");
        }
    }

    @Test
    void parseCsvTest() throws Exception {
        try (InputStream is = getFileFromZip("zip/Downloads.zip", ".csv");
             InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReader(reader)) {
                assertNotNull(is, "Файл .csv не найден в архиве");
                List<String[]> csvData = csvReader.readAll();

                boolean containsImport = csvData.stream()
                        .flatMap(row -> List.of(row).stream())
                        .anyMatch(cell -> cell.contains("import"));

                assertTrue(containsImport, "CSV-файл не содержит 'Import'");
            }
    }

    private InputStream getFileFromZip(String zipFileName, String fileExtension) throws IOException {
        try (InputStream is = cl.getResourceAsStream(zipFileName);
             ZipInputStream zis = new ZipInputStream(is)) {

            assertNotNull(is, "Архив" + zipFileName + "не найден");
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(fileExtension)) {
                    return new ByteArrayInputStream(zis.readAllBytes());
                }
            }
        }
        return null;
    }
}
