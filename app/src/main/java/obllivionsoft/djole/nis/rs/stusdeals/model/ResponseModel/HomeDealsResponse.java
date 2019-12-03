package obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class HomeDealsResponse {


    @SerializedName("data")
    public List<Data> data;
    @SerializedName("message")
    public String message;
    @SerializedName("status")
    public int status;

    public static class Data {
        @SerializedName("data")
        public List<Dataa> dataa;
        @SerializedName("name")
        public String name;
        @SerializedName("photo")
        public String photo;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("_id")
        public String _id;
    }

    public static class Dataa {
        @SerializedName("legal")
        public String legal;
        @SerializedName("type")
        public int type;
        @SerializedName("type_city")
        public String type_city;
        @SerializedName("isVIP")
        public boolean isVIP;
        @SerializedName("featured")
        public boolean featured;
        @SerializedName("sortnum")
        public int sortnum;
        @SerializedName("photo")
        public String photo;
        @SerializedName("coverPhoto")
        public String coverPhoto;
        @SerializedName("code")
        public String code;
        @SerializedName("price")
        public int price;
        @SerializedName("price_original")
        public int price_original;
        @SerializedName("limitTotal")
        public int limitTotal;
        @SerializedName("limitByStudent")
        public int limitByStudent;
        @SerializedName("isEvent")
        public boolean isEvent;
        @SerializedName("isActive")
        public boolean isActive;
        @SerializedName("ratings")
        public int ratings;
        @SerializedName("views")
        public int views;
        @SerializedName("meta_description")
        public String meta_description;
        @SerializedName("meta_title")
        public String meta_title;
        @SerializedName("slug")
        public String slug;
        @SerializedName("isLiked")
        public boolean isLiked;
        @SerializedName("distance")
        public double distance;
        @SerializedName("affiliate")
        public String affiliate;
        @SerializedName("instagram")
        public String instagram;
        @SerializedName("facebook")
        public String facebook;
        @SerializedName("web")
        public String web;
        @SerializedName("hot")
        public List<Boolean> hot;
        @SerializedName("scanForRedeem")
        public boolean scanForRedeem;
        @SerializedName("gallery")
        public List<String> gallery;
        @SerializedName("startDay")
        public String startDay;
        @SerializedName("accommodation_email")
        public String accommodation_email;
        @SerializedName("address")
        public Address address;
        @SerializedName("location")
        public Location location;
        @SerializedName("desc")
        public String desc;
        @SerializedName("title")
        public String title;
        @SerializedName("subCategory")
        public String subCategory;
        @SerializedName("_category")
        public String _category;
        @SerializedName("_provider")
        public List<_provider> _provider;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("_id")
        public String _id;
    }

    public static class Address {
        @SerializedName("street")
        public String street;
        @SerializedName("city")
        public String city;
        @SerializedName("zip")
        public String zip;
    }

    public static class Location {
        @SerializedName("lat")
        public double lat;
        @SerializedName("lng")
        public double lng;
        @SerializedName("r")
        public double r;
    }

    public static class _provider {
        @SerializedName("photo")
        public String photo;
        @SerializedName("coverPhoto")
        public String coverPhoto;
        @SerializedName("phone")
        public List<String> phone;
        @SerializedName("desc")
        public String desc;
        @SerializedName("password")
        public String password;
        @SerializedName("email")
        public String email;
        @SerializedName("person")
        public String person;
        @SerializedName("location")
        public Location location;
        @SerializedName("address")
        public Address address;
        @SerializedName("name")
        public String name;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("_id")
        public String _id;
    }

    public static class Locations {
        @SerializedName("lat")
        public double lat;
        @SerializedName("lng")
        public double lng;
        @SerializedName("r")
        public double r;
    }

    public static class Addresss {
        @SerializedName("street")
        public String street;
        @SerializedName("city")
        public String city;
        @SerializedName("zip")
        public String zip;
    }


}
