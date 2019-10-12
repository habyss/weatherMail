package com.test.demo.entity.model;

/**
 * @author kun.han on 2019/6/13 14:37
 */
public class WeatherDetail {
    /**
     * nameen : shanghai
     * cityname : 上海
     * city : 101020100
     * temp : 21
     * tempf : 69
     * WD : 静风
     * wde : NW
     * WS : 0级
     * wse :
     * SD : 87%
     * time : 14:10
     * weather : 雨
     * weathere : rain
     * weathercode : d301
     * qy : 1006
     * njd : 8.34km
     * sd : 87%
     * rain : 0.2
     * rain24h : 0
     * aqi : 36
     * limitnumber :
     * aqi_pm25 : 36
     * date : 06月13日(星期四)
     */

    private String nameen;
    private String cityname;
    private String city;
    private String temp;
    private String tempf;
    private String WD;
    private String wde;
    private String WS;
    private String wse;
    private String SD;
    private String time;
    private String weather;
    private String weathere;
    private String weathercode;
    private String qy;
    private String njd;
    private String sd;
    private String rain;
    private String rain24h;
    private String aqi;
    private String limitnumber;
    private String aqi_pm25;
    private String date;

    public String getNameen() {
        return nameen;
    }

    public void setNameen(String nameen) {
        this.nameen = nameen;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTempf() {
        return tempf;
    }

    public void setTempf(String tempf) {
        this.tempf = tempf;
    }

    public String getWD() {
        return WD;
    }

    public void setWD(String WD) {
        this.WD = WD;
    }

    public String getWde() {
        return wde;
    }

    public void setWde(String wde) {
        this.wde = wde;
    }

    public String getWS() {
        return WS;
    }

    public void setWS(String WS) {
        this.WS = WS;
    }

    public String getWse() {
        return wse;
    }

    public void setWse(String wse) {
        this.wse = wse;
    }

    public String getSD() {
        return SD;
    }

    public void setSD(String SD) {
        this.SD = SD;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeathere() {
        return weathere;
    }

    public void setWeathere(String weathere) {
        this.weathere = weathere;
    }

    public String getWeathercode() {
        return weathercode;
    }

    public void setWeathercode(String weathercode) {
        this.weathercode = weathercode;
    }

    public String getQy() {
        return qy;
    }

    public void setQy(String qy) {
        this.qy = qy;
    }

    public String getNjd() {
        return njd;
    }

    public void setNjd(String njd) {
        this.njd = njd;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getRain24h() {
        return rain24h;
    }

    public void setRain24h(String rain24h) {
        this.rain24h = rain24h;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getLimitnumber() {
        return limitnumber;
    }

    public void setLimitnumber(String limitnumber) {
        this.limitnumber = limitnumber;
    }

    public String getAqi_pm25() {
        return aqi_pm25;
    }

    public void setAqi_pm25(String aqi_pm25) {
        this.aqi_pm25 = aqi_pm25;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "WeatherDetail{" +
                "nameen='" + nameen + '\'' +
                ", cityname='" + cityname + '\'' +
                ", city='" + city + '\'' +
                ", temp='" + temp + '\'' +
                ", tempf='" + tempf + '\'' +
                ", WD='" + WD + '\'' +
                ", wde='" + wde + '\'' +
                ", WS='" + WS + '\'' +
                ", wse='" + wse + '\'' +
                ", SD='" + SD + '\'' +
                ", time='" + time + '\'' +
                ", weather='" + weather + '\'' +
                ", weathere='" + weathere + '\'' +
                ", weathercode='" + weathercode + '\'' +
                ", qy='" + qy + '\'' +
                ", njd='" + njd + '\'' +
                ", sd='" + sd + '\'' +
                ", rain='" + rain + '\'' +
                ", rain24h='" + rain24h + '\'' +
                ", aqi='" + aqi + '\'' +
                ", limitnumber='" + limitnumber + '\'' +
                ", aqi_pm25='" + aqi_pm25 + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
