package my.vono.web.config.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import my.vono.web.model.meeting.MeetingDetailDto;

public class ExcelPoi {

	public void xlsWiter(String result) {
		// 워크북 생성
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 워크시트 생성
		HSSFSheet sheet = workbook.createSheet();
		// 행 생성
		HSSFRow row = sheet.createRow(0);
		// 쎌 생성
		HSSFCell cell;

		
		// 헤더 정보 구성
		cell = row.createCell(0);
		cell.setCellValue("화자");

		cell = row.createCell(1);
		cell.setCellValue("내용");

		cell = row.createCell(2);
		cell.setCellValue("시간");

		// 리스트의 size 만큼 row를 생성

		try {
			JSONParser jsonParser = new JSONParser();

			// JSON데이터를 넣어 JSON Object 로 만들어 준다.
			JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
			JSONArray contentArray = (JSONArray) jsonObject.get("segments");
			for (int i = 0; i < contentArray.size(); i++) {
				JSONObject contentObject = (JSONObject) contentArray.get(i);
				row = sheet.createRow(i + 1);
				cell = row.createCell(0);
				JSONObject speakerObject = (JSONObject) contentObject.get("speaker");
				cell.setCellValue(String.valueOf(speakerObject.get("name")));

				cell = row.createCell(1);
				cell.setCellValue(String.valueOf(contentObject.get("text")));

				cell = row.createCell(2);
				cell.setCellValue(String.valueOf(contentObject.get("start")));

			}

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

//        for(int rowIdx=0; rowIdx < list.size(); rowIdx++) {
//            vo = list.get(rowIdx);
//            
//            // 행 생성
//            row = sheet.createRow(rowIdx+1);
//            
//            cell = row.createCell(0);
//            cell.setCellValue(vo.getCustId());
//            
//            cell = row.createCell(1);
//            cell.setCellValue(vo.getCustName());
//            
//            cell = row.createCell(2);
//            cell.setCellValue(vo.getCustAge());
//            
//            cell = row.createCell(3);
//            cell.setCellValue(vo.getCustEmail());
//            
//        }

		// 입력된 내용 파일로 쓰기
		File file = new File("C:\\VONO\\testWrite.xls");
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(file);
			workbook.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (workbook != null)
					workbook.close();
				if (fos != null)
					fos.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	

	public static List<MeetingDetailDto> xlsReader() {
		List<MeetingDetailDto> meetingDetailDto=new ArrayList<>();
		try {
		FileInputStream file = new FileInputStream("C:\\VONO\\testWrite.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		NumberFormat f = NumberFormat.getInstance();
		f.setGroupingUsed(false);	//지수로 안나오게 설정
		
		//시트 갯수
		int sheetNum = workbook.getNumberOfSheets();
		
		for(int s = 0; s < sheetNum; s++) {
			HSSFSheet sheet = workbook.getSheetAt(s);
			//행 갯수
			int rows = sheet.getPhysicalNumberOfRows();
			
			for(int r = 0 ; r < rows ; r++) {
				HSSFRow row = sheet.getRow(r);
				
				int cells = row.getPhysicalNumberOfCells();
				
			
				String speaker=null;
				String content=null;
				String date=null;
				for(int c = 0 ; c < cells; c++) {
					HSSFCell cell = row.getCell(c);
					
					
					String value = "";
					if(cell!=null) {
						//타입 체크
						switch(cell.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							value = f.format(cell.getNumericCellValue())+"";
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							value = cell.getBooleanCellValue()+"";
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value = cell.getErrorCellValue()+"";
							break;
						}
					}
					if(c==0)speaker=value;
					else if(c==1)content=value;
					else date=value;
					
				}
				meetingDetailDto.add(new MeetingDetailDto(speaker, content, date));
				System.out.println();
			}
		}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		for(MeetingDetailDto m:meetingDetailDto) {
		
			System.out.println(m.getSpeaker());
			System.out.println(m.getContent());
			System.out.println(m.getDate());
		}
		return meetingDetailDto;
		
	}


	public static void main(String[] args) {
		ExcelPoi x = new ExcelPoi();
//    	String s=ClovaSpeech.speech();
//    	x.xlsWiter(s);
		x.xlsReader();
	}

}
