package pl.khayn.zabawy.files;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FilesSandbox {

	private static final String FILE_PATH = "src/main/resources/data.txt";
	private static final String EXCEL_FILE_PATH = "src/main/resources/data.xlsx";

	public static void main(String[] args) throws IOException {

		Optional<List<String>> optional = FilesSandbox.getLines();
		optional.ifPresentOrElse((value)
						-> {
					System.out.println(
							"Value is present, its: "
									+ value);
				},
				()
						-> {
					System.out.println(
							"Value is empty");
				});


		System.out.println(FilesSandbox.getLines());
		System.out.println(FilesSandbox.getLines().get().stream().filter(l -> l.contains("dupa")).collect(Collectors.toList()));

		FilesSandbox.readExcelFile();
	}

	public static void readExcelFile() throws IOException {
		FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
		Workbook workbook = new XSSFWorkbook(file);

		Sheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			Iterator<Cell> iterator = row.cellIterator();

			while (iterator.hasNext()) {
				Cell next = iterator.next();

				switch (next.getCellTypeEnum()) {
					case STRING:
						System.out.println(next.getStringCellValue());
						break;

					case NUMERIC:
						System.out.println(next.getNumericCellValue());
						break;

					default:
						break;
				}

			}
		}
	}

	public static Optional<List<String>> getLines() {
		try {
			return Optional.ofNullable(Files.readAllLines(Paths.get(FILE_PATH), StandardCharsets.UTF_8));

		} catch (IOException e) {
			return Optional.empty();
		}


	}
}
