package obllivionsoft.djole.nis.rs.stusdeals.controller.apis;


public class Config {

    public static String APP_URL="https://api.stunii.com/";
    public static String QR_URL="http://108.61.175.63:40117/";
    public static final String FILE_UPLOAD_URL = "https://theagonsports.com/agon_v2/api/postFeed";

    static String BASE_URL = "";

    static AppMode appMode = AppMode.TEST;


    static public String getBaseURL() {
        init(appMode);
        return BASE_URL;
    }
    /**
     * Initialize all the variable in this method
     *
     * @param appMode
     */
    public static void init(AppMode appMode) {

        switch (appMode) {
            case DEV:
                BASE_URL =APP_URL;
                break;

            case TEST:
                BASE_URL =APP_URL;
                break;

            case LIVE:
                BASE_URL =APP_URL;
                break;
        }

    }

    public enum AppMode {
        DEV, TEST, LIVE
    }

}
