package co.za.eskom.towerdatauploader.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "towerData")
public class TowerDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String twr_pref;
    public int twr_no;
    public int pl_no;
    public boolean isabend;
    public int twr_type;
    public char sub_type;
    public double cond_att;
    public int tube_no;
    public int sht_no;
    public char crd_type;
    public int data_source;
    public int accuracy;
    public Date date_captured;
    public int tower_no;
    public double lat;
    public double longitude;
    public double height;
}
