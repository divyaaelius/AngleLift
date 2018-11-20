package com.angellift.utils;

public class Const {

    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 101;
    public static int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    public static final int STORAGE_PERMISSION_CODE = 102;
    public static String PREF_NAME = "Angellift";
    public static final long TIME_SCHEDULE = 20 * 1000;
    public static final long DELAY = 0 * 1000;

    // mobile type
    public static final int MOBILETYPE = 1;
    // no request id
    public static final int NO_REQUEST = -1;
    public static final int NO_TIME = -1;
    public static final String MANUAL = "manual";
    // payment mode
    public static final int CASH = 1;
    public static final int CREDIT = 0;
    public static final String URL = "url";

    public class UrlClient {
        public static final String BASE_URL = "http://192.168.137.6/angellift/index.php/api/";
        public static final String REGISTER_URL = BASE_URL+"Client_register_api/index";
        public static final String LOGIN_URL = BASE_URL+"Login_module_api/client";
        public static final String CHANGE_PASSWORD_URL = BASE_URL+"Change_password_api/";
        public static final String GET_PROFILE_DATA_URL = BASE_URL+"profile/Profile_api/index/";
        public static final String SET_PROFILE_DATA_URL = BASE_URL+"profile/Profile_edit_api/index/";
        public static final String REQUEST_RIDE_NOW_URL = BASE_URL+"Request_create_api/index";
        public static final String SCHEDULE_REQUEST_RIDE_NOW_URL = BASE_URL+"Request_future_api/index";

    }

    public class Params {

        // request schedule for ride
        public static final String FR_USERID="fr_userid";
        public static final String FR_CURRENT_LATITUDE="fr_current_latitude";
        public static final String FR_CURRENT_LONGITUDE="fr_current_longitude";
        public static final String FR_PAYMENT_TYPE="fr_payment_type";
        public static final String FR_PROMO_CODE="fr_promo_code";
        public static final String FR_PICKUP_ADDRESS="fr_pickup_address";
        public static final String FR_DESTINATION_ADDRESS="fr_destination_address";
        public static final String FR_DESTINATION_LATITUDE="fr_destination_latitude";
        public static final String FR_DESTINATION_LONGITUDE="fr_destination_longitude";
        public static final String FR_START_DATE="fr_start_date";
        public static final String FR_TIMEZONE="fr_timezone";
        public static final String FR_ANGEL_TYPE="fr_angel_type";
        public static final String FR_CREATEBYID="fr_createbyid";

        // request for ride
        public static final String RQ_USERID="rq_userid";
        public static final String RQ_CURRENT_LATITUDE="rq_current_latitude";
        public static final String RQ_CURRENT_LONGITUDE="rq_current_longitude";
        public static final String RQ_PAYMENT_TYPE="rq_payment_type";
        public static final String RQ_PROMO_CODE="rq_promo_code";
        public static final String RQ_PICKUP_ADDRESS="rq_pickup_address";
        public static final String RQ_DESTINATION_ADDRESS="rq_destination_address";
        public static final String RQ_DESTINATION_LATITUDE="rq_destination_latitude";
        public static final String RQ_DESTINATION_LONGITUDE="rq_destination_longitude";
        public static final String RQ_CURRENT_DATE="rq_current_date";
        public static final String RQ_TIMEZONE="rq_timezone";
        public static final String RQ_ANGEL_TYPE="rq_angel_type";
        public static final String RQ_CREATEBYID="rq_createbyid";
        public static final String RQ_TIME="rq_time";

        // request responce
        public static final String REQUEST_ID="request_id";




        // get profile data
        public static final String PROFILEDETAILS="profiledetails";
        public static final String PROFILE_UPDATE="profile_update";

        // change password
        public static final String OLD="old";
        public static final String NEW="new";
        public static final String NEW_CONFIRM="new_confirm";
        public static final String USER_ID="user_id";
        // Register 12/11/18
        public static final String C_ID = "c_id";
        public static final String C_MOBILENO = "c_mobileno";
        public static final String C_NAME = "c_name";
        public static final String C_ADDRESS = "c_address";
        public static final String C_DOB = "c_dob";
        public static final String MOBILE_TYPE = "mobile_type";
        public static final String C_IMAGE = "c_image";

        // login
        public static final String IDENTITY = "identity";
        public static final String MOBILE_DEVICE_ID = "mobile_device_id";
        public static final String MOBILE_TOKEN = "mobile_token";

        // login response
        public static final String TRUE = "true";
        public static final String STATUS = "status";
        public static final String DATA = "data";
        public static final String ID = "id";
        public static final String USERNAME = "username";
        public static final String EMAIL = "email";
        public static final String FIRST_NAME = "first_name";
        public static final String C_CREATEBYUSERID = "c_createbyuserid";
        public static final String TOKEN_ID = "token_id";
        public static final String ROLE_ID = "role_id";
        public static final String C_USERDETAILS_ID = "c_userdetails_id";


        public static final String ROUTES = "routes";
        public static final String LEGS = "legs";
        public static final String TEXT = "text";
        public static final String VALUE = "value";
        public static final String DURATION = "duration";
        public static final String START_ADDRESS = "start_address";
        public static final String END_ADDRESS = "end_address";
        public static final String START_LOCATION = "start_location";
        public static final String END_LOCATION = "end_location";
        public static final String LAT = "lat";
        public static final String LNG = "lng";
        public static final String STEPS = "steps";
        public static final String POLYLINE = "polyline";
        public static final String POINTS = "points";
        public static final String HTML_INSTRUCTIONS = "html_instructions";


        // api response param
        public static final String PHONE = "phone";
        public static final String PASSWORD = "password";
        public static final String DEVICE_TOKEN = "device_token";
        public static final String NAME = "name";

        public static final String CLIENT_ID = "client_id";
        public static final String TOKEN = "token";

        public static final String CLIENT_NAME = "client_name";
        public static final String ADDRESS = "address";
        public static final String DOB = "dob";
        public static final String PICTURE = "picture";
        public static final String CONFIRM_PASSWORD = "confirm_password";
        public static final String CITY = "city";
        public static final String STATE = "state";


        public static final String PAYMENT_TOKEN = "payment_token";
        public static final String CARD_NUMBER = "card_number";
        public static final String Expire_Date = "expire_date";
        public static final String OWNER_NAME = "owner_name";
        public static final String CVV = "cvv";
        public static final String CARD_DETAILS = "card_details";
        public static final String CARD_ID = "card_id";
        public static final String OWNER_ID = "owner_id";
        public static final String LONGITUDE = "longitude";
        public static final String LATITUDE = "latitude";
        public static final String DEST_LATITUDE = "dest_latitude";
        public static final String DEST_LONGITUDE = "dest_longitude";
        public static final String SOURCE_ADDRESS = "source_address";
        public static final String DEST_ADDRESS = "dest_address";

        public static final String CUR_LONGITUDE = "cur_longitude";
        public static final String CUR_LATITUDE = "cur_latitude";
        public static final String SOURCE_LONGITUDE = "source_longitude";
        public static final String SOURCE_LATITUDE = "source_latitude";
        public static final String CANCEL_REASON = "cancel_reason";

        public static final String DRIVER_LATITUDE = "driver_latitude";
        public static final String DRIVER_LONGITUDE = "driver_longitude";


        public static final String KMS = "kms";

        public static final String ANGEL_TYPE = "angel_type";
        public static final String BEARING = "bearing";
        public static final String DISTANCE = "distance";
        public static final String PROMO_CODE = "promo_code";
        public static final String FROM_DATE = "from_date";
        public static final String TO_DATE = "to_date";
        public static final String TIME_ZONE = "time_zone";
        public static final String START_TIME = "start_time";
        public static final String CURRENT_DATE = "create_date_time";
        public static final String PICKUP_ADDRESS = "pickup_address";


        // driver details

        public static final String RATING = "rating";
        public static final String CAR_MODEL = "car_model";
        public static final String CAR_NUMBER = "car_number";
        public static final String DRIVER_ID = "driver_id";

        public static final String LICENSE_NUMBER = "license_number";
        public static final String LICENSE_PHOTO = "license_photo";
        public static final String LICENSE_REGISTRATION_DATE = "license_registration_date";
        public static final String EXPIRE_DATE = "expire_date";
        public static final String DATE_OF_BIRTH = "date_of_birth";
        public static final String CAR_PHOTO = "car_photo";
        public static final String CAR_DOCUMENT = "car_document";


        //trip param

        public final String IS_WALKER_STARTED = "is_walker_started";
        public final String IS_WALKER_ARRIVED = "is_walker_arrived";
        public final String IS_WALK_STARTED = "is_walk_started";
        public final String IS_WALKER_RATED = "is_walker_rated";
        public final String IS_COMPLETED = "is_completed";
        public final String CONFIRMED_WALKER = "confirmed_walker";

        public final String PAYMENT_TYPE = "payment_type";
        public final String TIME = "time";
        public final String BASE_PRICE = "base_price";
        public final String BASE_DISTANCE = "base_distance";
        public final String DISTANCE_COST = "distance_cost";

        //

        public final String OFFLINE = "offline";
        public final String ONLINE = "online";
        public final String TOKEN_ = "token";
        public final String DESTINATION_LATITUDE = "destination_latitude";
        public final String DESTINATION_LONGITUDE = "destination_longitude";
        public final String CURRENCY = "currency";
        public final String ACCEPTED = "accepted";
        public final String REQUEST_ASSIGNED = "request_assigned";
        public final String CANCEL_STATUS = "cancel_status";
        public final String DRIVER_START = "driver_start";
        public final String DRIVER_ARRIVED = "driver_arrived";
        public final String TRIP_START = "trip_start";
        public final String TRIP_COMPLETE = "trip_complete";
        public final String CLIENT_DESTINATION_LATITUDE = "client_destination_latitude";
        public final String CLIENT_DESTINATION_LONGITUDE = "client_destination_longitude";
        public final String PROMO_BONUS = "promo_bonus";
        public final String KM = "km";

        public static final String OTP_NUMBER = "otp_number";


    }

    public class ServiceCode {
        public static final int REGISTER = 1;
        public static final int LOGIN = 2;
        public static final int GET_ROUTE = 3;
        public static final int ADD_CARD = 6;
        public static final int PICK_ME_UP = 7;
        public static final int CREATE_REQUEST = 8;
        public static final int GET_REQUEST_STATUS = 9;
        public static final int GET_REQUEST_LOCATION = 10;
        public static final int GET_REQUEST_IN_PROGRESS = 11;
        public static final int RATING = 12;
        public static final int CANCEL_REQUEST = 13;
        public static final int GET_PAGES = 14;
        public static final int GET_PAGES_DETAILS = 15;
        public static final int GET_VEHICAL_TYPES = 16;
        public static final int FORGET_PASSWORD = 18;
        public static final int UPDATE_PROFILE = 19;
        public static final int GET_CARDS = 20;
        public static final int HISTORY = 21;
        public static final int GET_PATH = 22;
        public static final int GET_REFERREL = 23;
        public static final int APPLY_REFFRAL_CODE = 24;
        public static final int GET_PROVIDERS = 26;
        public static final int GET_DURATION = 27;
        public static final int DRAW_PATH_ROAD = 28;
        public static final int DRAW_PATH = 29;
        public static final int PAYMENT_TYPE = 30;
        public static final int DEFAULT_CARD = 31;
        public static final int GET_QUOTE = 32;
        public static final int GET_FARE_QUOTE = 34;
        public static final int GET_NEAR_BY = 35;
        public static final int SET_DESTINATION = 37;
        public static final int UPDATE_PROVIDERS = 38;
        public static final int APPLY_PROMO = 39;
        public static final int LOGOUT = 40;
        public static final int CREATE_FUTURE_REQUEST = 41;
        public static final int DELETE_FUTURE_REQUEST = 42;
        public static final int GET_FUTURE_REQUEST = 43;
        public static final int DELETE_CARD = 44;
        public static final int ADD_EVENT = 45;
        public static final int GET_EVENT = 46;
        public static final int DELETE_EVENT = 47;
    }

    // Placesurls
    public static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    public static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    public static final String TYPE_NEAR_BY = "/nearbysearch";
    public static final String OUT_JSON = "/json";
}
