package co.za.eskom.towerdatauploader.controllers;

import co.za.eskom.towerdatauploader.models.TowerData;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tower-data-uploader")
public class TowerDataUploaderController {

    @PostMapping("/import-tower-excel")
    public List<TowerData> importExcelFile(@RequestParam("file")MultipartFile files) throws IOException{
        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());

        //Read tower data from excel file sheet1
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                XSSFRow row = worksheet.getRow(index);
            }
        }
    }
}
