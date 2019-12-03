package obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class CategoryResponseModel {


    @Expose
    @SerializedName("data")
    public List<Data> data;
    @Expose
    @SerializedName("total")
    public int total;

    public static class Data {
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
}
