package educacionit.clase7.startedservice;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SaveDataLocal {
    SharedPreferences myPreferences
            = PreferenceManager.getDefaultSharedPreferences(GlobalClass.context);
    SharedPreferences.Editor myEditor = myPreferences.edit();
    public void saveTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        String fecha=dateFormat.format(date);
        this.myEditor.putString("LAST_TIME", fecha);
        this.myEditor.commit();
    }
    public String getValue(){

        String fechaultima=this.myPreferences.getString("LAST_TIME","unknown");
        return fechaultima;
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
