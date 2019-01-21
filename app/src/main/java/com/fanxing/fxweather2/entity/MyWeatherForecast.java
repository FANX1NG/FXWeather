package com.fanxing.fxweather2.entity;

import java.util.List;

/**
 * Created by 繁星 on 2018/12/31.
 */

public class MyWeatherForecast {
    private List<HeWeather6Bean> HeWeather6;

    public List<HeWeather6Bean> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<HeWeather6Bean> HeWeather6) {
        this.HeWeather6 = HeWeather6;
    }

    public static class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101010100","location":"北京","parent_city":"北京","admin_area":"北京","cnty":"中国","lat":"39.90498734","lon":"116.40528870","tz":"8.0"}
         * daily_forecast : [{"cond_code_d":"103","cond_code_n":"101","cond_txt_d":"晴间多云","cond_txt_n":"多云","date":"2017-10-26","hum":"57","pcpn":"0.0","pop":"0","pres":"1020","tmp_max":"16","tmp_min":"8","uv_index":"3","vis":"16","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"5"},{"cond_code_d":"101","cond_code_n":"501","cond_txt_d":"多云","cond_txt_n":"雾","date":"2017-10-27","hum":"56","pcpn":"0.0","pop":"0","pres":"1018","tmp_max":"18","tmp_min":"9","uv_index":"3","vis":"20","wind_deg":"187","wind_dir":"南风","wind_sc":"微风","wind_spd":"6"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2017-10-28","hum":"26","pcpn":"0.0","pop":"0","pres":"1029","tmp_max":"17","tmp_min":"5","uv_index":"2","vis":"20","wind_deg":"2","wind_dir":"北风","wind_sc":"3-4","wind_spd":"19"}]
         * status : ok
         * update : {"loc":"2017-10-26 23:09","utc":"2017-10-26 15:09"}
         */

        private BasicBean basic;
        private String status;
        private UpdateBean update;
        private List<DailyForecastBean> daily_forecast;

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public List<DailyForecastBean> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public static class BasicBean {
            /**
             * cid : CN101010100
             * location : 北京
             * parent_city : 北京
             * admin_area : 北京
             * cnty : 中国
             * lat : 39.90498734
             * lon : 116.40528870
             * tz : 8.0
             */

            private String location;

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }
        }

        public static class UpdateBean {
            /**
             * loc : 2017-10-26 23:09
             * utc : 2017-10-26 15:09
             */

            private String loc;

            public String getLoc() {
                return loc;
            }

            public void setLoc(String loc) {
                this.loc = loc;
            }
        }

        public static class DailyForecastBean {
            /**
             * cond_code_d : 103
             * cond_code_n : 101
             * cond_txt_d : 晴间多云
             * cond_txt_n : 多云
             * date : 2017-10-26
             * hum : 57
             * pcpn : 0.0
             * pop : 0
             * pres : 1020
             * tmp_max : 16
             * tmp_min : 8
             * uv_index : 3
             * vis : 16
             * wind_deg : 0
             * wind_dir : 无持续风向
             * wind_sc : 微风
             * wind_spd : 5
             */

            private String cond_code_d;
            private String cond_txt_d;
            private String date;
            private String pop;
            private String tmp_max;
            private String tmp_min;

            public String getCond_code_d() {
                return cond_code_d;
            }

            public void setCond_code_d(String cond_code_d) {
                this.cond_code_d = cond_code_d;
            }

            public String getCond_txt_d() {
                return cond_txt_d;
            }

            public void setCond_txt_d(String cond_txt_d) {
                this.cond_txt_d = cond_txt_d;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getTmp_max() {
                return tmp_max;
            }

            public void setTmp_max(String tmp_max) {
                this.tmp_max = tmp_max;
            }

            public String getTmp_min() {
                return tmp_min;
            }

            public void setTmp_min(String tmp_min) {
                this.tmp_min = tmp_min;
            }
        }
    }
}
