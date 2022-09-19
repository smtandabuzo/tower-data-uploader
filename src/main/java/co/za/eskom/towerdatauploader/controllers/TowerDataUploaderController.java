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
    public List<TowerData> importExcelFile(@RequestParam("file") MultipartFile files) throws IOException {
        List<TowerData> towers = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());

        //Read tower data from excel file sheet1
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                XSSFRow row = worksheet.getRow(index);
                TowerData towerData = new TowerData();
                towerData.twr_pref = getCellValue(row, 0);
                towerData.twr_no = Integer.parseInt(getCellValue(row, 1));
                towerData.pl_no = Integer.parseInt(getCellValue(row, 2));
                towerData.isabend = Boolean.parseBoolean(getCellValue(row, 3));
                towerData.twr_type = Integer.parseInt(getCellValue(row, 4));
                towerData.sub_type = getCellValue(row, 5);
                towerData.cond_att = Double.parseDouble(getCellValue(row, 6));
                towerData.tube_no = Integer.parseInt(getCellValue(row, 7));
                towerData.sht_no = Integer.parseInt(getCellValue(row, 8));
                towerData.crd_type = getCellValue(row, 9);
                towerData.data_source = Integer.parseInt(getCellValue(row, 10));
                towerData.accuracy = Integer.parseInt(getCellValue(row, 11));
                towerData.date_captured = getCellValue(row, 12);
                towerData.tower_no = Integer.parseInt(getCellValue(row, 13));
                towerData.lat = Double.parseDouble(getCellValue(row, 14));
                towerData.longitude = Double.parseDouble(getCellValue(row, 15));
                towerData.height = Double.parseDouble(getCellValue(row, 16));

                towers.add(towerData);
            }
        }

        // Save to db
        List<TowerDataEntity> towerDataEntities = new ArrayList<>();
        if (towers.size() > 0) {
            towers.forEach(x -> {
                TowerDataEntity towerDataEntity = new TowerDataEntity();
                towerDataEntity.twr_pref = x.twr_pref;
                towerDataEntity.tower_no = x.tower_no;
                towerDataEntity.pl_no = x.pl_no;
                towerDataEntity.isabend = x.isabend;
                towerDataEntity.twr_type = x.twr_type;
                towerDataEntity.sub_type = x.sub_type;
                towerDataEntity.cond_att = x.cond_att;
                towerDataEntity.tube_no = x.tube_no;
                towerDataEntity.sht_no = x.sht_no;
                towerDataEntity.crd_type = x.crd_type;
                towerDataEntity.data_source = x.data_source;
                towerDataEntity.accuracy = x.accuracy;
                towerDataEntity.date_captured = x.date_captured;
                towerDataEntity.lat = x.lat;
                towerDataEntity.longitude = x.longitude;
                towerDataEntity.height = x.height;

                towerDataEntities.add(towerDataEntity);
            });

            towerDataRepository.saveAll(towerDataEntities);
        }

        return towers;
    }

    private String getCellValue(Row row, int cellNo) {
        DataFormatter formatter = new DataFormatter();

        Cell cell = row.getCell(cellNo);

        return formatter.formatCellValue(cell);
    }

    @GetMapping("/get-tower-data")
    public List<TowerData> getTowerData() {
        List<TowerData> result = new ArrayList<>();
        List<TowerDataEntity> towerDataEntities = towerDataRepository.findAll();

        if (towerDataEntities != null && towerDataEntities.size() > 0) {
            towerDataEntities.forEach(x -> {
                TowerData towerData = new TowerData();
                towerData.tower_no = x.tower_no;
                towerData.data_source = x.data_source;
                towerData.twr_pref = x.twr_pref;
                towerData.id = x.id;
                towerData.lat = x.lat;
                towerData.height = x.height;
                towerData.longitude = x.longitude;
                towerData.date_captured = x.date_captured;
                towerData.accuracy = x.accuracy;
                towerData.crd_type = x.crd_type;
                towerData.cond_att = x.cond_att;
                towerData.sht_no = x.sht_no;
                towerData.tube_no = x.tube_no;
                towerData.isabend = x.isabend;
                towerData.pl_no = x.pl_no;
                towerData.sub_type = x.sub_type;
                towerData.twr_type = x.twr_type;
                result.add(towerData);
            });
        }
        return result;
    }
}
