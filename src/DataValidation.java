import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidation {
        public static String CheckEmpty(String data){
            if(data == null || data.isEmpty()) data = "";
            return data;
        }

        public static String CheckNumbers(String data){
            for(char c : data.toCharArray()){
                if(Character.isDigit(c)) {
                    data = "";
                    break;
                }

            }
            return data;

        }

        public static String CheckSpecialChars(String data){
            Pattern p = Pattern.compile("[^a-z0-9ęółśążźćńĘÓŁŚĄŻŹĆŃ ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(data);
            boolean b = m.find();

            if (b){
                data = "";
            }

            return  data;
        }


        private static boolean CheckCapitalised(String data){
            Pattern p = Pattern.compile("[^A-ZĘÓŁŚĄŻŹĆŃ ]");
            Matcher m = p.matcher(data);
            boolean b = m.find();

            if (b){
                return true;
            }else {

                return false;
            }
        }

        private static boolean CheckUncapitalised(String data){
            Pattern p = Pattern.compile("[^a-zęółśążźćń ]");
            Matcher m = p.matcher(data);
            boolean b = m.find();

            if (b){
                return true;
            }else {

                return false;
            }
        }

        public static String CheckNames(String data){
            String Wielka_Litera;
            String Male_Litery;
            Wielka_Litera = data.substring(0,1);
            Male_Litery = data.substring(1);

            if(CheckCapitalised(Wielka_Litera)) {
                data = "";
            }else if(CheckUncapitalised(Male_Litery)){
                data = "";
            }
            return data;
        }

        public static String CheckSize(Integer size, String data){
            if(data.length() > size) data = "";
            return data;
        }


}
