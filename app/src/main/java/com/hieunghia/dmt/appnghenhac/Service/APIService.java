package com.hieunghia.dmt.appnghenhac.Service;

public class APIService {

    public static String base_url = "https://team5appplaymusic.000webhostapp.com/Server/"; // service lưu file ảnh music
    public static String user_url = "https://filesavemusic.000webhostapp.com/Server/";  // service lưu dữ liệu account

    public static DataService getService() // tuong tac giua data va Client
    {
        return APIRetrofitClient.getClient(base_url).create(DataService.class);
    }

    public static DataService GetUserAccount()
    {
        return APIRetrofitClient.getClient(user_url).create(DataService.class);
    }

}
