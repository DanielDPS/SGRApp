package gcode.baseproject.interactors.db.entities;



import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tblcustomer")
public class CustomerEntity implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private  String cId;
    @ColumnInfo(name = "identifier")
    private  String cIdentifier;
    @ColumnInfo(name = "businessname")
    private  String cBusinessName;
    @ColumnInfo(name = "activity")
    private  String cActivity;
    @ColumnInfo(name = "direction")
    private  String cDirection;
    @ColumnInfo(name = "entity")
    private  String cEntity;
    @ColumnInfo(name = "mail1")
    private  String cMail1;
    @ColumnInfo(name = "mail2")
    private  String cMail2;
    @ColumnInfo(name = "mail3")
    private  String cMail3;
    @ColumnInfo(name = "numer1")
    private  String cNumber1;
    @ColumnInfo(name = "number2")
    private  String cNumber2;
    @ColumnInfo(name = "initialdate")
    private  String cInitialDate;
    @ColumnInfo(name = "creationpermission")
    private  String cCreationPermission;
    @ColumnInfo(name = "rfc")
    private  String cRfc;
    @ColumnInfo(name = "representantive")
    private  String cRepresentative;
    @ColumnInfo(name = "creation")
    private  String cCreation;
    @ColumnInfo(name = "edited")
    private  String cEdited;
    @ColumnInfo(name = "active")
    private  String cActive;

    public  void setCId(String cId){this.cId=cId;}
    public  String getCId(){return this.cId.equals("") ? "Campo vacio" : this.cId;}

    public  void setCIdentifier(String cIdentifier){
        this.cIdentifier = cIdentifier;
    }
    public  String getCIdentifier(){return this.cIdentifier.equals("")?"Campo vacio":this.cIdentifier;}
    public  void setCBusinessName(String cBusinessName){
        this.cBusinessName=cBusinessName;
    }
    public  String getCBusinessName(){return  this.cBusinessName.equals("")?"Campo vacio":this.cBusinessName;}

    public void setCActivity(String cActivity){
        this.cActivity=cActivity;
    }
    public String getCActivity(){
        return this.cActivity.equals("")?"Campo vacio":this.cActivity;
    }
    public void setCDirection(String cDirection){
        this.cDirection = cDirection;
    }
    public  String getCDirection(){return this.cDirection.equals("")?"Campo vacio":this.cDirection;}
    public  void setCEntity(String cEntity){
        this.cEntity =cEntity;
    }
    public String getCEntity(){return this.cEntity.equals("")?"Campo vacio":this.cEntity;}
    public void setCMail1(String cMail1){
        this.cMail1 = cMail1;
    }
    public  String getCMail1(){
        return this.cMail1.equals("")?"Campo vacio":this.cMail1;
    }
    public void setCMail2(String cMail2){
        this.cMail2=cMail2;
    }
    public  String getCMail2(){return this.cMail2.equals("") ?"Campo vacio":this.cMail2;}

    public void setCMail3(String cMail3){
        this.cMail3 = cMail3;
    }
    public String getCMail3(){return this.cMail3.equals("")?"Campo vacio":this.cMail3;}
    public void setCNumber1(String cNumber1){
        this.cNumber1= cNumber1;
    }
    public String getCNumber1(){return this.cNumber1.equals("")?"Campo vacio":this.cNumber1;}
    public void setCNumber2(String cNumber2){
        this.cNumber2 = cNumber2;
    }
    public String getCNumber2(){return this.cNumber2.equals("")?"Campo vacio":this.cNumber2;}
    public void setCInitialDate(String cInitialDate){
        this.cInitialDate =cInitialDate;
    }
    public  String getCInitialDate(){
        return this.cInitialDate.equals("")?"Campo vacio":this.cInitialDate;
    }
    public void setCCreationPermission(String cCreationPermission){
        this.cCreationPermission =cCreationPermission;
    }
    public String getCCreationPermission(){return this.cCreationPermission.equals("")?"Campo vacio":this.cCreationPermission;}
    public void setCRfc(String cRfc){
        this.cRfc = cRfc;
    }
    public String getCRfc(){return this.cRfc.equals("")?"Campo vacio":this.cRfc;}
    public  void setCRepresentative(String cRepresentative){
        this.cRepresentative = cRepresentative;
    }
    public String getCRepresentative(){return this.cRepresentative.equals("")?"Campo vacio":this.cRepresentative;}
    public  void setCCreation(String cCreation){
        this.cCreation = cCreation;
    }
    public String getCCreation(){
        return this.cCreation.equals("")?"Campo vacio":this.cCreation;
    }
    public void setCEdited(String cEdited){
        this.cEdited = cEdited;
    }
    public  String getCEdited(){
        return this.cEdited.equals("")?"Campo vacio":this.cEdited;
    }
    public void setCActive(String cActive){
        this.cActive =cActive;
    }
    public  String getCActive(){return this.cActive.equals("")?"Campo vacio":this.cActive; }
}
