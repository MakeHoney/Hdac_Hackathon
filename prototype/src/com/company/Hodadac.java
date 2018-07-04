package com.company;

import com.hdacSdk.hdacCoreApi.*;
import com.hdacSdk.hdacWallet.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Hodadac implements RpcHandler {
    private HdacWallet hdacWallet;

    private String rpcIp;
    private String rpcPort;
    private String rpcUser;
    private String rpcPassword;
    private String chainName;

    public Hodadac(String rpcIp, String rpcPort, String rpcUser,
                   String rpcPassword, String chainName) {
        this.rpcIp = rpcIp;
        this.rpcPort = rpcPort;
        this.rpcUser = rpcUser;
        this.rpcPassword = rpcPassword;
        this.chainName = chainName;
    }

    public void getBalanceOf(JSONArray addr) {
        HdacRpcClient rpcClient = createHdacRpcClient(null);

        try {
            HdacCommand hdacCommand = new HdacCommand(rpcClient);

            rpcClient.setRPCHandler(new RpcHandler() {
                @Override
                public void onResponse(int method, JSONObject data) {
                    switch (method) {
                        case CommandUtils.LIST_UNSPENT:
                            System.out.println(calculateBalance(data));
                            break;
                    }
                }
                @Override
                public void onError(int method, String err, int i1) {
                    System.out.print("send error : " + err + "\n");
                }
            });

            hdacCommand.listunspent("0", "999999", addr);
        } catch(CommandException e) {
            e.printStackTrace();
        }
    }


    private HdacRpcClient createHdacRpcClient(RpcHandler handler) {
        HdacRpcClient hdacRpcClient = null;

        try {
            hdacRpcClient = HdacApiManager.createRPCClient(rpcIp, rpcPort,
                    rpcUser, rpcPassword, chainName, handler);

        } catch (HdacException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return hdacRpcClient;
    }

    private float calculateBalance(JSONObject data) {
        //listunspent result로 부터 utxos 가져오기
        JSONArray utxos;
        float balance = 0;
        try {
            utxos = data.getJSONArray("result");
            //utxos로 부터 balance 가져오기
            for(int i=0; i<utxos.length();i++) {
                JSONObject utxo;
                utxo = utxos.getJSONObject(i);
                balance += utxo.getFloat("amount");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.print("balance " + balance + "\n");

        return balance;
    }


    @Override
    public void done(int method) {
        // TODO Auto-generated method stub

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onResponse(int method, JSONObject rst) {

    }


    @Override
    public void onError(int method,String err_msg, int rstCode) {

    }
}
