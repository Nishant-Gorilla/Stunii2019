package obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class JobResponseModel {
    @SerializedName("data")
    public List<Data> data;
    @SerializedName("message")
    public String message;
    @SerializedName("status")
    public int status;

    public static class Data {
        @SerializedName("__v")
        public int __v;
        @SerializedName("companyImage")
        public String companyImage;
        @SerializedName("jobRate")
        public String jobRate;
        @SerializedName("companyName")
        public String companyName;
        @SerializedName("jobDescription")
        public String jobDescription;
        @SerializedName("jobType")
        public String jobType;
        @SerializedName("jobAddress")
        public String jobAddress;
        @SerializedName("jobName")
        public String jobName;
        @SerializedName("_id")
        public String _id;
    }
}
