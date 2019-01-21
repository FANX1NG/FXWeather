package com.fanxing.fxweather2.entity;

import java.util.List;

/**
 * Created by 繁星 on 2018/12/16.
 */

public class MyWeatherNow {

    private List<HeWeather6Bean> HeWeather6;

    public List<HeWeather6Bean> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<HeWeather6Bean> HeWeather6) {
        this.HeWeather6 = HeWeather6;

    }

    public static class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101280101","location":"广州","parent_city":"广州","admin_area":"广东","cnty":"中国","lat":"23.12517738","lon":"113.28063965","tz":"+8.00"}
         * update : {"loc":"2018-12-30 13:57","utc":"2018-12-30 05:57"}
         * status : ok
         * now : {"cloud":"75","cond_code":"101","cond_txt":"多云","fl":"7","hum":"51","pcpn":"0.0","pres":"1028","tmp":"10","vis":"10","wind_deg":"66","wind_dir":"东北风","wind_sc":"3","wind_spd":"13"}
         */

        private BasicBean basic;
        private UpdateBean update;
        private String status;
        private NowBean now;

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public static class BasicBean {
            /**
             * cid : CN101280101
             * location : 广州
             * parent_city : 广州
             * admin_area : 广东
             * cnty : 中国
             * lat : 23.12517738
             * lon : 113.28063965
             * tz : +8.00
             */

            private String location;
            private String parent_city;

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getParent_city() {
                return parent_city;
            }

            public void setParent_city(String parent_city) {
                this.parent_city = parent_city;
            }
        }

        public static class UpdateBean {
            /**
             * loc : 2018-12-30 13:57
             * utc : 2018-12-30 05:57
             */

            private String loc;

            public String getLoc() {
                return loc;
            }

            public void setLoc(String loc) {
                this.loc = loc;
            }
        }

        public static class NowBean {
            /**
             * cloud : 75
             * cond_code : 101
             * cond_txt : 多云
             * fl : 7
             * hum : 51
             * pcpn : 0.0
             * pres : 1028
             * tmp : 10
             * vis : 10
             * wind_deg : 66
             * wind_dir : 东北风
             * wind_sc : 3
             * wind_spd : 13
             */

            private String cond_code;
            private String cond_txt;
            private String hum;
            private String pres;
            private String tmp;
            private String wind_dir;
            private String wind_sc;

            public String getCond_code() {
                return cond_code;
            }

            public void setCond_code(String cond_code) {
                this.cond_code = cond_code;
            }

            public String getCond_txt() {
                return cond_txt;
            }

            public void setCond_txt(String cond_txt) {
                this.cond_txt = cond_txt;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }
        }
    }
}
