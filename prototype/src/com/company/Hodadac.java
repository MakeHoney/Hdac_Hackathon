package com.company;

import com.hdacSdk.hdacCoreApi.*;
import com.hdacSdk.hdacWallet.*;
//import org.apache.commons.codec.DecoderException;
//import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Hodadac implements RpcHandler {
    private String txid;

    private String rpcIp;
    private String rpcPort;
    private String rpcUser;
    private String rpcPassword;
    private String chainName;
    private JSONArray userAddr;
    private JSONArray hotelOwnerAddr;

    public Hodadac(String rpcIp, String rpcPort, String rpcUser, String rpcPassword,
                   String chainName, JSONArray userAddr, JSONArray hotelOwnerAddr) {
        this.rpcIp = rpcIp;
        this.rpcPort = rpcPort;
        this.rpcUser = rpcUser;
        this.rpcPassword = rpcPassword;
        this.chainName = chainName;
        this.userAddr = userAddr;
        this.hotelOwnerAddr = hotelOwnerAddr;
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

            hdacCommand.listunspent("0", "99999999", addr);
        } catch(CommandException e) {
            e.printStackTrace();
        }
    }

    /* return transaction hash value */
    public void writeApplianceInfo(String data) {
        HdacRpcClient rpcClient = createHdacRpcClient(null);

        try {
            HdacCommand hdacCommand = new HdacCommand(rpcClient);

            rpcClient.setRPCHandler(new RpcHandler() {
                @Override
                public void onResponse(int method, JSONObject data) {
                    switch (method) {
                        case CommandUtils.SEND_WITH_DATA_FROM :
                            System.out.println(data);
                            /* transaction id 저장 */
                            txid = data.get("result").toString();
                            System.out.println(txid);
                            break;
                    }
                }

                @Override
                public void onError(int method, String err, int i1) {
                    System.out.print("send error : " + err + "\n");
                }
            });
            hdacCommand.sendwithdatafrom(hotelOwnerAddr.getString(0), userAddr.getString(0),
                    "0", "byunghun");
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    /* 파라미터 들어가야함(스트링) */
    public void readApplianceInfo() {
        HdacRpcClient rpcClient = createHdacRpcClient(null);

        try {
            HdacCommand hdacCommand = new HdacCommand(rpcClient);

            rpcClient.setRPCHandler(new RpcHandler() {
                @Override
                public void onResponse(int method, JSONObject data) {
                    switch (method) {
                        case CommandUtils.GET_TXOUT_DATA :

                            /* transaction id 저장 */
                            System.out.println(data.get("result"));
                            System.out.println(new String(HdacWallet.hexToByte(data.get("result").toString())));
                            break;
                    }
                }

                @Override
                public void onError(int method, String err, int i1) {
                    System.out.print("send error : " + err + "\n");
                }
            });

            /* 사실은 txid는 parameter로 들어가야한다. */
            hdacCommand.gettxoutdata(txid, "1", null, null);
//            hdacCommand.getrawtransaction(txid, "0");
        } catch (CommandException e) {
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
