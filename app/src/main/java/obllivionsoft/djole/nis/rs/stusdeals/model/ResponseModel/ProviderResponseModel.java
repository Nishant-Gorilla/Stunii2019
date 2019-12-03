package obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class ProviderResponseModel {

    @Expose
    @SerializedName("data")
    public List<Data> data;
    @Expose
    @SerializedName("message")
    public String message;
    @Expose
    @SerializedName("status")
    public int status;

    public static class Data {
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
        public Location location;
        @Expose
        @SerializedName("address")
        public Address address;
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
}
