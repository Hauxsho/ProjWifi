package com.example.projwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

public class WifiDirectBroRec extends BroadcastReceiver {
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private MainActivity mMain;

    public WifiDirectBroRec(WifiP2pManager mManager,WifiP2pManager.Channel mChannel,MainActivity mMain)
    {
        this.mManager=mManager;
        this.mChannel=mChannel;
        this.mMain=mMain;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
     String action = intent.getAction();
     if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action))
     {
         int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,-1);
         if(state==WifiP2pManager.WIFI_P2P_STATE_ENABLED)
         {
             Toast.makeText(context, "Wifi On", Toast.LENGTH_SHORT).show();
         }
         else {
             Toast.makeText(context, "Wifi Off", Toast.LENGTH_SHORT).show();
         }
     }
     else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action))
     {
        if(mManager!=null)
        {
            mManager.requestPeers(mChannel,mMain.peerListListener);
        }
     }
     else if(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action))
     {
        if(mManager==null)
        {
            return;
        }
         NetworkInfo networkInfo=intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
        if (networkInfo.isConnected())
        {
            mManager.requestConnectionInfo(mChannel,mMain.connectionInfoListener);
        }
        else
        {
            mMain.connectionStatus.setText("Device Disconected");
        }
     }
     else if(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action))
     {

     }
    }
}
