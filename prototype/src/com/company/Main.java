package com.company;


import org.json.JSONArray;

public class Main {
    private static final String rpcIp = "http://localhost";
    private static final String rpcPort = "38822";
    private static final String rpcUser = "makehoney";
    private static final String rpcPassword = "1234";
    private static final String chainName = "master";


    public static void main(String[] args) {
        try {
            JSONArray userAddr = new JSONArray();
            JSONArray hotelOwner = new JSONArray();

            hotelOwner.put("HBCU3nfhmTzSVvPTTUktv1BpdG1CoGY91K");
            userAddr.put("HKMDrmVWbB9mQRJ2YTs5zWNip68YSAVTLX");

            System.out.println(userAddr.toString());
            Hodadac hodadac = new Hodadac(rpcIp, rpcPort, rpcUser,
                    rpcPassword, chainName);

            hodadac.getBalanceOf(userAddr);

            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
