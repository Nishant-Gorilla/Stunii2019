package obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class TestingDealsModels {
    @Expose
    @SerializedName("dataFeatured")
    public List<DataFeatured> dataFeatured;
    @Expose
    @SerializedName("dealsList")
    public List<DealsList> dealsList;
    String abc;

    public static class DataFeatured {
        @Expose
        @SerializedName("data")
        public List<Data> data;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("_id")
        public int _id;
    }

    public static class Data {
        @Expose
        @SerializedName("legal")
        public String legal;
        @Expose
        @SerializedName("type")
        public int type;
        @Expose
        @SerializedName("type_city")
        public String type_city;
        @Expose
        @SerializedName("isVIP")
        public boolean isVIP;
        @Expose
        @SerializedName("featured")
        public boolean featured;
        @Expose
        @SerializedName("sortnum")
        public int sortnum;
        @Expose
        @SerializedName("photo")
        public String photo;
        @Expose
        @SerializedName("coverPhoto")
        public String coverPhoto;
        @Expose
        @SerializedName("code")
        public String code;
        @Expose
        @SerializedName("price")
        public double price;
        @Expose
        @SerializedName("price_original")
        public int price_original;
        @Expose
        @SerializedName("limitTotal")
        public int limitTotal;
        @Expose
        @SerializedName("redeemType")
        public String redeemType;
        @Expose
        @SerializedName("limitByStudent")
        public int limitByStudent;
        @Expose
        @SerializedName("isEvent")
        public boolean isEvent;
        @Expose
        @SerializedName("isActive")
        public boolean isActive;
        @Expose
        @SerializedName("ratings")
        public double ratings;
        @Expose
        @SerializedName("views")
        public int views;
        @Expose
        @SerializedName("meta_description")
        public String meta_description;
        @Expose
        @SerializedName("meta_title")
        public String meta_title;
        @Expose
        @SerializedName("slug")
        public String slug;
        @Expose
        @SerializedName("isLiked")
        public boolean isLiked;
        @Expose
        @SerializedName("distance")
        public double distance;
        @Expose
        @SerializedName("affiliate")
        public String affiliate;
        @Expose
        @SerializedName("instagram")
        public String instagram;
        @Expose
        @SerializedName("facebook")
        public String facebook;
        @Expose
        @SerializedName("web")
        public String web;
        @Expose
        @SerializedName("hot")
        public List<Boolean> hot;
        @Expose
        @SerializedName("scanForRedeem")
        public boolean scanForRedeem;

        @Expose
        @SerializedName("endDay")
        public String endDay;
        @Expose
        @SerializedName("startDay")
        public String startDay;
        @Expose
        @SerializedName("accommodation_email")
        public String accommodation_email;
        @Expose
        @SerializedName("address")
        public Address address;
        @Expose
        @SerializedName("location")
        public Location location;
        @Expose
        @SerializedName("desc")
        public String desc;
        @Expose
        @SerializedName("title")
        public String title;
        @Expose
        @SerializedName("subCategory")
        public String subCategory;
        @Expose
        @SerializedName("_category")
        public String _category;
        @Expose
        @SerializedName("_provider")
        public _providers _provider;
        @Expose
        @SerializedName("created_at")
        public String created_at;
        @Expose
        @SerializedName("updated_at")
        public String updated_at;
        @Expose
        @SerializedName("_id")
        public String _id;
    }

    public static class Address {
        @Expose
        @SerializedName("zip")
        public String zip;
        @Expose
        @SerializedName("city")
        public String city;
        @Expose
        @SerializedName("street")
        public String street;
    }

    public static class Location {
        @Expose
        @SerializedName("r")
        public double r;
        @Expose
        @SerializedName("lng")
        public double lng;
        @Expose
        @SerializedName("lat")
        public double lat;
    }

    public static class _providers {
        @Expose
        @SerializedName("photo")
        public String photo;
        @Expose
        @SerializedName("coverPhoto")
        public String coverPhoto;
        @Expose
        @SerializedName("phone")
        public List<String> phone;
        @Expose
        @SerializedName("desc")
        public String desc;
        @Expose
        @SerializedName("distance")
        public double distance;
        @Expose
        @SerializedName("password")
        public String password;
        @Expose
        @SerializedName("email")
        public String email;
        @Expose
        @SerializedName("person")
        public String person;
        @Expose
        @SerializedName("location")
        public Locations location;
        @Expose
        @SerializedName("address")
        public Addresss address;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("created_at")
        public String created_at;
        @Expose
        @SerializedName("updated_at")
        public String updated_at;
        @Expose
        @SerializedName("_id")
        public String _id;
    }

    public static class Locations {
        @Expose
        @SerializedName("r")
        public double r;
        @Expose
        @SerializedName("lng")
        public double lng;
        @Expose
        @SerializedName("lat")
        public double lat;
    }

    public static class Addresss {
        @Expose
        @SerializedName("zip")
        public String zip;
        @Expose
        @SerializedName("city")
        public String city;
        @Expose
        @SerializedName("street")
        public String street;
    }

    public static class DealsList {
        @Expose
        @SerializedName("data")
        public List<Dataa> data;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("photo")
        public String photo;
        @Expose
        @SerializedName("created_at")
        public String created_at;
        @Expose
        @SerializedName("updated_at")
        public String updated_at;
        @Expose
        @SerializedName("_id")
        public String _id;
    }

    public static class Dataa {
        @Expose
        @SerializedName("endDay")
        public String endDay;
        @Expose
        @SerializedName("startDay")
        public String startDay;
        @Expose
        @SerializedName("subCategory")
        public String subCategory;
        @Expose
        @SerializedName("redeemType")
        public String redeemType;
        @Expose
        @SerializedName("scanForRedeem")
        public boolean scanForRedeem;
        @Expose
        @SerializedName("legal")
        public String legal;
        @Expose
        @SerializedName("type")
        public int type;
        @Expose
        @SerializedName("type_city")
        public String type_city;
        @Expose
        @SerializedName("isVIP")
        public boolean isVIP;
        @Expose
        @SerializedName("featured")
        public boolean featured;
        @Expose
        @SerializedName("sortnum")
        public int sortnum;
        @Expose
        @SerializedName("photo")
        public String photo;
        @Expose
        @SerializedName("coverPhoto")
        public String coverPhoto;
        @Expose
        @SerializedName("code")
        public String code;
        @Expose
        @SerializedName("price")
        public int price;
        @Expose
        @SerializedName("price_original")
        public int price_original;
        @Expose
        @SerializedName("limitTotal")
        public int limitTotal;
        @Expose
        @SerializedName("limitByStudent")
        public int limitByStudent;
        @Expose
        @SerializedName("isEvent")
        public boolean isEvent;
        @Expose
        @SerializedName("isActive")
        public boolean isActive;
        @Expose
        @SerializedName("ratings")
        public double ratings;
        @Expose
        @SerializedName("views")
        public int views;
        @Expose
        @SerializedName("meta_description")
        public String meta_description;
        @Expose
        @SerializedName("meta_title")
        public String meta_title;
        @Expose
        @SerializedName("slug")
        public String slug;
        @Expose
        @SerializedName("isLiked")
        public boolean isLiked;
        @Expose
        @SerializedName("distance")
        public double distance;
        @Expose
        @SerializedName("affiliate")
        public String affiliate;
        @Expose
        @SerializedName("instagram")
        public String instagram;
        @Expose
        @SerializedName("facebook")
        public String facebook;
        @Expose
        @SerializedName("web")
        public String web;
        @Expose
        @SerializedName("hot")
        public List<Boolean> hot;
        @Expose
        @SerializedName("accommodation_email")
        public String accommodation_email;
        @Expose
        @SerializedName("address")
        public Addres address;
        @Expose
        @SerializedName("location")
        public Locatio location;
        @Expose
        @SerializedName("desc")
        public String desc;
        @Expose
        @SerializedName("title")
        public String title;
        @Expose
        @SerializedName("_category")
        public String _category;
        @Expose
        @SerializedName("_provider")
        public List<_provider> _provider;
        @Expose
        @SerializedName("created_at")
        public String created_at;
        @Expose
        @SerializedName("updated_at")
        public String updated_at;
        @Expose
        @SerializedName("_id")
        public String _id;
    }

    public static class Addres {
        @Expose
        @SerializedName("street")
        public String street;
        @Expose
        @SerializedName("city")
        public String city;
        @Expose
        @SerializedName("zip")
        public String zip;
    }

    public static class Locatio {
        @Expose
        @SerializedName("lat")
        public double lat;
        @Expose
        @SerializedName("lng")
        public double lng;
        @Expose
        @SerializedName("r")
        public double r;
    }

    public static class _provider {
        @Expose
        @SerializedName("photo")
        public String photo;
        @Expose
        @SerializedName("coverPhoto")
        public String coverPhoto;
        @Expose
        @SerializedName("phone")
        public List<String> phone;
        @Expose
        @SerializedName("desc")
        public String desc;
        @Expose
        @SerializedName("password")
        public String password;
        @Expose
        @SerializedName("email")
        public String email;
        @Expose
        @SerializedName("person")
        public String person;
        @Expose
        @SerializedName("location")
        public Locati location;
        @Expose
        @SerializedName("address")
        public Addre address;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("created_at")
        public String created_at;
        @Expose
        @SerializedName("updated_at")
        public String updated_at;
        @Expose
        @SerializedName("_id")
        public String _id;
    }

    public static class Locati {
        @Expose
        @SerializedName("lat")
        public double lat;
        @Expose
        @SerializedName("lng")
        public double lng;
        @Expose
        @SerializedName("r")
        public double r;
    }

    public static class Addre {
        @Expose
        @SerializedName("street")
        public String street;
        @Expose
        @SerializedName("city")
        public String city;
        @Expose
        @SerializedName("zip")
        public String zip;
    }
//    @SerializedName("dataFeatured")
//    public List<DataFeatured> dataFeatured;
//    @SerializedName("dealsList")
//    public List<DealsList> dealsList;
//    String abc;
//
//    public static class DataFeatured {
//        @SerializedName("data")
//        public List<Data> data;
//        @SerializedName("name")
//        public String name;
//        @SerializedName("_id")
//        public int _id;
//    }
//
//    public static class Data {
//        @SerializedName("legal")
//        public String legal;
//        @SerializedName("type")
//        public int type;
//        @SerializedName("type_city")
//        public String type_city;
//        @SerializedName("isVIP")
//        public boolean isVIP;
//        @SerializedName("featured")
//        public boolean featured;
//        @SerializedName("sortnum")
//        public int sortnum;
//        @SerializedName("photo")
//        public String photo;
//        @SerializedName("coverPhoto")
//        public String coverPhoto;
//        @SerializedName("code")
//        public String code;
//        @SerializedName("price")
//        public int price;
//        @SerializedName("price_original")
//        public int price_original;
//        @SerializedName("limitTotal")
//        public int limitTotal;
//        @SerializedName("redeemType")
//        public String redeemType;
//        @SerializedName("limitByStudent")
//        public int limitByStudent;
//        @SerializedName("isEvent")
//        public boolean isEvent;
//        @SerializedName("isActive")
//        public boolean isActive;
//        @SerializedName("ratings")
//        public double ratings;
//        @SerializedName("views")
//        public int views;
//        @SerializedName("meta_description")
//        public String meta_description;
//        @SerializedName("meta_title")
//        public String meta_title;
//        @SerializedName("slug")
//        public String slug;
//        @SerializedName("isLiked")
//        public boolean isLiked;
//        @SerializedName("distance")
//        public double distance;
//        @SerializedName("affiliate")
//        public String affiliate;
//        @SerializedName("instagram")
//        public String instagram;
//        @SerializedName("facebook")
//        public String facebook;
//        @SerializedName("web")
//        public String web;
//        @SerializedName("hot")
//        public List<Boolean> hot;
//        @SerializedName("scanForRedeem")
//        public boolean scanForRedeem;
//        @SerializedName("gallery")
//        public List<String> gallery;
//        @SerializedName("accommodation_email")
//        public String accommodation_email;
//        @SerializedName("address")
//        public Address address;
//        @SerializedName("location")
//        public Location location;
//        @SerializedName("desc")
//        public String desc;
//        @SerializedName("title")
//        public String title;
//        @SerializedName("_provider")
//        public _providers _provider;
//        @SerializedName("created_at")
//        public String created_at;
//        @SerializedName("updated_at")
//        public String updated_at;
//        @SerializedName("_id")
//        public String _id;
//    }
//
//    public static class Address {
//        @SerializedName("zip")
//        public String zip;
//        @SerializedName("city")
//        public String city;
//        @SerializedName("street")
//        public String street;
//    }
//
//    public static class Location {
//        @SerializedName("r")
//        public double r;
//        @SerializedName("lng")
//        public double lng;
//        @SerializedName("lat")
//        public double lat;
//    }
//
//    public static class _providers {
//        @SerializedName("photo")
//        public String photo;
//        @SerializedName("coverPhoto")
//        public String coverPhoto;
//        @SerializedName("phone")
//        public List<String> phone;
//        @SerializedName("desc")
//        public String desc;
//        @SerializedName("password")
//        public String password;
//        @SerializedName("email")
//        public String email;
//        @SerializedName("person")
//        public String person;
//        @SerializedName("location")
//        public Locati location;
//        @SerializedName("address")
//        public Addre address;
//        @SerializedName("name")
//        public String name;
//        @SerializedName("created_at")
//        public String created_at;
//        @SerializedName("updated_at")
//        public String updated_at;
//        @SerializedName("_id")
//        public String _id;
//    }
//
//    public static class Locati {
//        @SerializedName("r")
//        public double r;
//        @SerializedName("lng")
//        public double lng;
//        @SerializedName("lat")
//        public double lat;
//    }
//
//    public static class Addre {
//        @SerializedName("zip")
//        public String zip;
//        @SerializedName("city")
//        public String city;
//        @SerializedName("street")
//        public String street;
//    }
//
//    public static class DealsList {
//        @SerializedName("data")
//        public List<Dataa> data;
//        @SerializedName("name")
//        public String name;
//        @SerializedName("photo")
//        public String photo;
//        @SerializedName("created_at")
//        public String created_at;
//        @SerializedName("updated_at")
//        public String updated_at;
//        @SerializedName("_id")
//        public String _id;
//    }
//
//    public static class Dataa {
//        @SerializedName("redeemType")
//        public String redeemType;
//        @SerializedName("scanForRedeem")
//        public boolean scanForRedeem;
//        @SerializedName("legal")
//        public String legal;
//        @SerializedName("type")
//        public int type;
//        @SerializedName("type_city")
//        public String type_city;
//        @SerializedName("isVIP")
//        public boolean isVIP;
//        @SerializedName("featured")
//        public boolean featured;
//        @SerializedName("sortnum")
//        public int sortnum;
//        @SerializedName("photo")
//        public String photo;
//        @SerializedName("coverPhoto")
//        public String coverPhoto;
//        @SerializedName("code")
//        public String code;
//        @SerializedName("price")
//        public int price;
//        @SerializedName("price_original")
//        public int price_original;
//        @SerializedName("limitTotal")
//        public int limitTotal;
//        @SerializedName("limitByStudent")
//        public int limitByStudent;
//        @SerializedName("isEvent")
//        public boolean isEvent;
//        @SerializedName("isActive")
//        public boolean isActive;
//        @SerializedName("ratings")
//        public double ratings;
//        @SerializedName("views")
//        public int views;
//        @SerializedName("meta_description")
//        public String meta_description;
//        @SerializedName("meta_title")
//        public String meta_title;
//        @SerializedName("slug")
//        public String slug;
//        @SerializedName("isLiked")
//        public boolean isLiked;
//        @SerializedName("distance")
//        public double distance;
//        @SerializedName("affiliate")
//        public String affiliate;
//        @SerializedName("instagram")
//        public String instagram;
//        @SerializedName("facebook")
//        public String facebook;
//        @SerializedName("web")
//        public String web;
//        @SerializedName("hot")
//        public List<Boolean> hot;
//        @SerializedName("gallery")
//        public List<String> gallery;
//        @SerializedName("accommodation_email")
//        public String accommodation_email;
//        @SerializedName("address")
//        public Addres address;
//        @SerializedName("location")
//        public Locatio location;
//        @SerializedName("desc")
//        public String desc;
//        @SerializedName("title")
//        public String title;
//        @SerializedName("_category")
//        public String _category;
//        @SerializedName("_provider")
//        public List<_provider> _provider;
//        @SerializedName("created_at")
//        public String created_at;
//        @SerializedName("updated_at")
//        public String updated_at;
//        @SerializedName("_id")
//        public String _id;
//    }
//
//    public static class Addres {
//        @SerializedName("street")
//        public String street;
//        @SerializedName("city")
//        public String city;
//        @SerializedName("zip")
//        public String zip;
//    }
//
//    public static class Locatio {
//        @SerializedName("lat")
//        public double lat;
//        @SerializedName("lng")
//        public double lng;
//        @SerializedName("r")
//        public double r;
//    }
//
//    public static class _provider {
//        @SerializedName("photo")
//        public String photo;
//        @SerializedName("coverPhoto")
//        public String coverPhoto;
//        @SerializedName("phone")
//        public List<String> phone;
//        @SerializedName("desc")
//        public String desc;
//        @SerializedName("password")
//        public String password;
//        @SerializedName("email")
//        public String email;
//        @SerializedName("person")
//        public String person;
//        @SerializedName("location")
//        public Locations location;
//        @SerializedName("address")
//        public Addresss address;
//        @SerializedName("name")
//        public String name;
//        @SerializedName("created_at")
//        public String created_at;
//        @SerializedName("updated_at")
//        public String updated_at;
//        @SerializedName("_id")
//        public String _id;
//    }
//
//    public static class Locations {
//        @SerializedName("lat")
//        public double lat;
//        @SerializedName("lng")
//        public double lng;
//        @SerializedName("r")
//        public double r;
//    }
//
//    public static class Addresss {
//        @SerializedName("street")
//        public String street;
//        @SerializedName("city")
//        public String city;
//        @SerializedName("zip")
//        public String zip;
//    }


}
