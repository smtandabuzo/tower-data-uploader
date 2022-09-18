package co.za.eskom.towerdatauploader.controllers;

import co.za.eskom.towerdatauploader.entities.TowerDataEntity;
import co.za.eskom.towerdatauploader.models.TowerData;
import co.za.eskom.towerdatauploader.repositories.TowerDataRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tower-data-uploader")
public class TowerDataUploaderController {

    @Autowired
    TowerDataRepository towerDataRepository;

    @PostMapping("/import-tower-excel")
    public List<TowerData> importExcelFile(@RequestParam("file")MultipartFile files) throws IOException{
        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());

        //Read tower data from excel file sheet1
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                XSSFRow row = worksheet.getRow(index);
                TowerData towerData = new TowerData();
                towerData.twr_pref = getCellValue(row,0);
                towerData.tower_no = Integer.parseInt(getCellValue(row,1));
            }
        }

        // Save to db
        List<TowerDataEntity> entities = new ArrayList<>();
    }

    private String getCellValue(Row row, int cellNo) {
        DataFormatter formatter = new DataFormatter();

        Cell cell = row.getCell(cellNo);

        return formatter.formatCellValue(cell);
    }

    @GetMapping("/get-tower-data")
    public List<TowerData> getTowerData(){
        List<TowerData> result = new ArrayList<>();
        List<TowerDataEntity> entities = towerDataRepository.findAll();

        if(entities != null && entities.size() > 0){
            entities.forEach(x->{
                TowerData towerData = new TowerData();
                result.add(towerData);
            });
        }
    }

}
