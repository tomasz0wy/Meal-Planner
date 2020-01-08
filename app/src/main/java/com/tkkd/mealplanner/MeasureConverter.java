package com.tkkd.mealplanner;

class MeasureConverter {

    static String convertToSmaller(float value, String mesName){
        if(mesName.equals("l") || mesName.equals("tbsp") || mesName.equals("ml") || mesName.equals("cup")){
            return convertToMl(value, mesName);
        }else if(mesName.equals("kg") || mesName.equals("g") || mesName.equals("tsp")){
            return convertToGrams(value, mesName);
        }else {
            return value + "  ";
        }
    }

    static String convertToBigger(float value, String mesName){
        if(mesName.equals("ml")){
            return convertFromMl(value);
        }else if(mesName.equals("g")){
            return convertFromGrams(value);
        }else {
            return value + "   ";
        }
    }

    private static String convertToMl(float value, String mesName){
        switch (mesName){
            case "l":
                return value*1000 + "ml";
            case "tsp":
                return value*5 + "ml";
            case "tbsp":
                return value*15 + "ml";
            case "ml":
                return value + "ml";
            case "cup":
                return value*240 + "ml";
            default:
                return 0 + "ml";
        }
    }

    private static String convertToGrams(float value, String mesName){
        switch (mesName){
            case "kg":
                return value*1000 + " g";
            case "g":
                return value + " g";
            case "tsp":
                return value*5 + " g";
            default:
                return 0 + " g";
        }
    }

    private static String convertFromMl(float value){
        if(value >= 1000){
            float answer = value/1000;
            return answer + "  l";
        }else{
            return value + " ml";
        }
    }

    private static String convertFromGrams(float value){
        if(value >= 1000){
            float answer = value/1000;
            return answer + " kg";
        }else{
            return value + "  g";
        }
    }
}
