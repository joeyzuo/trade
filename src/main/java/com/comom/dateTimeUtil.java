package com.comom;
import java.text.DateFormat;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Date;
public class dateTimeUtil {

    /**
     * long转date
     * @param dateLong
     * @return
     */
    public Date longToDate(long dateLong){
        Date date = new Date(dateLong);
        return date;
    }

    /**
     * date转long
     * @param date
     * @return
     */
    public long dateToLong(Date date){
        long dateLong = date.getTime();
        return  dateLong;
    }

    /**
     * string转date
     * @param str
     * @return
     * @throws ParseException
     */
    public Date stringToDate(String str) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(str);
        return date;
    }
    /**
     * date转string
     * @param date
     * @return
     */
    public String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        return str ;
    }

}
