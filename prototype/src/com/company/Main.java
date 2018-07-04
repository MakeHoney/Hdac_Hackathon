package com.company;


import org.json.JSONArray;

public class Main {
    private static final String rpcIp = "http://localhost";
    private static final String rpcPort = "38822";
    private static final String rpcUser = "makehoney";
    private static final String rpcPassword = "1234";
    private static final String chainName = "master";
    private static final JSONArray userAddr = new JSONArray();
    private static final JSONArray hotelOwnerAddr = new JSONArray();


    public static void main(String[] args) {
        try {
            hotelOwnerAddr.put("HBCU3nfhmTzSVvPTTUktv1BpdG1CoGY91K");
            userAddr.put("HKMDrmVWbB9mQRJ2YTs5zWNip68YSAVTLX");

            Hodadac hodadac = new Hodadac(rpcIp, rpcPort, rpcUser,
                    rpcPassword, chainName, userAddr, hotelOwnerAddr);

//            hodadac.getBalanceOf(userAddr);
            hodadac.writeApplianceInfo("byunghun");
            Thread.sleep(10000);
              hodadac.readApplianceInfo();
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
