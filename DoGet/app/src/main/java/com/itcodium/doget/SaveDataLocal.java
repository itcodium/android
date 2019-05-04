package com.itcodium.doget;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class SaveDataLocal {
    SharedPreferences myPreferences
            = PreferenceManager.getDefaultSharedPreferences(GlobalClass.context);
    SharedPreferences.Editor myEditor = myPreferences.edit();
    public SaveDataLocal(){
        // this.saveTime("NULL");
    }


    public void saveTime(String value){

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = new Date();
            String fecha=dateFormat.format(date);
            String pString= fecha+";"+value;
       // Toast.makeText(GlobalClass.context,"saveTime-> "+pString, Toast.LENGTH_SHORT).show();
            this.myEditor.putString("LAST_SAVE", pString);
            this.myEditor.commit();

    }

    public String getValue(){
        String fechaultima=this.myPreferences.getString("LAST_SAVE","NULL;NULL");
        // Toast.makeText(GlobalClass.context,"ULTIMA FECHA: "+fechaultima, Toast.LENGTH_SHORT).show();
         try{
            if(fechaultima.split(";")[1]=="PENDIENTE"){
                return "2000/01/01 00:00";
            }
        }catch (Exception Ex){
         //+++   Toast.makeText(GlobalClass.context,Ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return fechaultima.split(";")[0];
    }

    public long  getDiffFromLastSaved(){
        String lastSaved;
        long resultadoDiff=0;

        try{
            lastSaved=this.myPreferences.getString("LAST_SAVE","NULL;NULL");

            // Toast.makeText(GlobalClass.context,"< Last > "+ lastSaved, Toast.LENGTH_LONG).show();
            String [] last=  lastSaved.split(";");

           // Toast.makeText(GlobalClass.context,"< Last [1] > "+ last[1], Toast.LENGTH_LONG).show();
             // Toast.makeText(GlobalClass.context,"< LEN > "+  Integer.toString(last.length) , Toast.LENGTH_SHORT).show();

            if(last[0].equals("NULL")){
                return 1000000;
            }
            resultadoDiff=this.getDateDiff(last[0]);
            //  Toast.makeText(GlobalClass.context,"< resultadoDiff > "+lastSaved+" - "+  resultadoDiff , Toast.LENGTH_SHORT).show();

            if(last.length>1 && last[1]=="PENDIENTE"){
                return 0;
            }
        }catch (Exception ex){
           // Toast.makeText(GlobalClass.context,"CATCH -> "+ ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

         //Toast.makeText(GlobalClass.context,"(*) DIFF -> "+Long.toString(resultadoDiff), Toast.LENGTH_SHORT).show();

        return resultadoDiff;
    }


    public long getDateDiff(String pDate){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH);
        Date date;
        Date today = new Date();
        long diff=0;
        try {
            date = dateFormat.parse(pDate);
            diff = today.getTime() - date.getTime();
            // diff =(diff / (1000 * 60 * 60 )); // Horas
            diff =(diff / (1000 * 60  )); // minutos

        }catch (Exception e){
            diff=-1;
        }
        return diff;
    }


}
