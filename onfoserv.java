package fr.maxx;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class onfoserv {
	
	public static int count = -1;
	public static int serv = -1;
	
	public static void main1(String ip, int port) {
        try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 1 * 1000);
               
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());
               
                out.write(0xFE);
               
                StringBuilder str = new StringBuilder();
               
                int b;
                while ((b = in.read()) != -1) {
                        if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
                                str.append((char) b);
                        }
                }
               
                String[] data = str.toString().split("§");
                String motd = data[0];
                int onlinePlayers = Integer.valueOf(data[1]);
                count = onlinePlayers;
 
                int maxPlayers = Integer.valueOf(data[2]);
                
                serv = maxPlayers;
        } catch (Exception e) {
                
        }
}

}

