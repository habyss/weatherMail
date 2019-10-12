package com.test.demo.entity.model;

/**
 * @author kun.han on 2019/6/13 14:29
 */
public class Weather {
    /**
     * city : 101020100
     * cityname : 上海
     * temp : 24℃
     * tempn : 20℃
     * weather : 小雨
     * wd : 东南风转东北风
     * ws : 3-4级
     * weathercode : d7
     * weathercoden : n7
     * fctime : 20190613113000
     */

    private String city;
    private String cityname;
    private String temp;
    private String tempn;
    private String weather;
    private String wd;
    private String ws;
    private String weathercode;
    private String weathercoden;
    private String fctime;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTempn() {
        return tempn;
    }

    public void setTempn(String tempn) {
        this.tempn = tempn;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

    public String getWs() {
        return ws;
    }

    public void setWs(String ws) {
        this.ws = ws;
    }

    public String getWeathercode() {
        return weathercode;
    }

    public void setWeathercode(String weathercode) {
        this.weathercode = weathercode;
    }

    public String getWeathercoden() {
        return weathercoden;
    }

    public void setWeathercoden(String weathercoden) {
        this.weathercoden = weathercoden;
    }

    public String getFctime() {
        return fctime;
    }

    public void setFctime(String fctime) {
        this.fctime = fctime;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "city='" + city + '\'' +
                ", cityname='" + cityname + '\'' +
                ", temp='" + temp + '\'' +
                ", tempn='" + tempn + '\'' +
                ", weather='" + weather + '\'' +
                ", wd='" + wd + '\'' +
                ", ws='" + ws + '\'' +
                ", weathercode='" + weathercode + '\'' +
                ", weathercoden='" + weathercoden + '\'' +
                ", fctime='" + fctime + '\'' +
                '}';
    }
}
