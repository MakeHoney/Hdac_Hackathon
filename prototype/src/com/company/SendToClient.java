package com.company;

import com.hdacSdk.hdacCoreApi.*;
import com.hdacSdk.hdacWallet.*;
import org.json.JSONObject;

public class SendToClient {
    private static final String rpcIp = "http://0.0.0.0";
    private static final String rpcPort = "38822";
    private static final String rpcUser = "makehoney";
    private static final String rpcPassword = "1234";
    private static final String chainName = "master";

    private HdacCommand command;
    private HdacRpcClient client;


    public SendToClient() {

    }

    public void getAddresses() {
        System.out.println("http sending start");
        HdacRpcClient rpc_client = createHdacRpcClient(null);

        try {
            HdacCommand hdac_command = new HdacCommand(rpc_client);


            hdac_command.setRpcHandler(new RpcHandler() {
                public void onResponse(int method, JSONObject data) {

                    switch(method) {
                        case CommandUtils.GET_ADDRESSES :
                            System.out.println("-------------------");
                            System.out.println(data.toString());
                    }
                }

                public void onError(int method, String err, int i1) {
                    System.out.print("send error : " + err + "\n");
                }
            });
            hdac_command.getaddresses("1");
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
}
