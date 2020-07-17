package Helper;

import java.util.ArrayList;

public class Place_Info {
    public String continent;
    public String city;
    public boolean star;
    public int rate;
    public String url;
    public String explain;
    public ArrayList<spot_info> spot_infos = new ArrayList<>();

    public Place_Info(String continent, String city, boolean star, int rate, String url){
        this.continent = continent;
        this.city = city;
        this.star = star;
        this.rate = rate;
        this.url = url;
    }


    public static class spot_info{
        public String city_name;
        public String spot_name;
        public String url;
        public String spot_explain;
        public float lat,lng;

        public spot_info(String city_name, String spot_name, String spot_explain, String url,float lat,float lng){
            this.city_name = city_name;
            this.spot_name = spot_name;
            this.spot_explain = spot_explain;
            this.url = url;
            this.lat = lat;
            this.lng = lng;
        }
    }
}
