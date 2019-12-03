package obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class SubCategoryResponse {

    @SerializedName("subCategory")
    public List<SubCategory> subCategory;
    @SerializedName("deals")
    public List<Deals> deals;
    @SerializedName("message")
    public String message;
    @SerializedName("status")
    public int status;

    public static class SubCategory {
        @SerializedName("__v")
        public int __v;
        @SerializedName("subCategoryName")
        public String subCategoryName;
        @SerializedName("categoryId")
        public String categoryId;
        @SerializedName("_id")
        public String _id;
    }

    public static class Deals {
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
        @SerializedName("redeemType")
        public String redeemType;
        @SerializedName("limitByStudent")
        public int limitByStudent;
        @SerializedName("isEvent")
        public boolean isEvent;
        @SerializedName("isActive")
        public boolean isActive;
        @SerializedName("ratings")
        public double ratings;
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
        public int distance;
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
        @SerializedName("endDay")
        public String endDay;
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
        public _provider _provider;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("_id")
        public String _id;
    }

    public static class Address {
        @SerializedName("zip")
        public String zip;
        @SerializedName("city")
        public String city;
        @SerializedName("street")
        public String street;
    }

    public static class Location {
        @SerializedName("r")
        public double r;
        @SerializedName("lng")
        public double lng;
        @SerializedName("lat")
        public double lat;
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
        public Locations location;
        @SerializedName("address")
        public Addresss address;
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
        @SerializedName("r")
        public double r;
        @SerializedName("lng")
        public double lng;
        @SerializedName("lat")
        public double lat;
    }

    public static class Addresss {
        @SerializedName("zip")
        public String zip;
        @SerializedName("city")
        public String city;
        @SerializedName("street")
        public String street;
    }
}
