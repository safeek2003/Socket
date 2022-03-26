package com.company;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
class Main{
    public static void main(String args[])throws Exception{
        ServerSocket socket=new ServerSocket(3333);
        System.out.println("Server is waiting ");
        Socket client=socket.accept();
        System.out.println("Server is now connected with client");
        DataInputStream din=new DataInputStream(client.getInputStream());
        DataOutputStream dout=new DataOutputStream(client.getOutputStream());
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        Send send=new Send(dout,br);
        Receive send2=new Receive(din);
        send.start();
        send2.start();


    }
}



class Receive extends Thread{
    DataInputStream din;


    Receive(DataInputStream din){
    this.din=din;
    }


    public void run(){
        String str="";
        try {
        while(!str.equals("stop")) {

                str = din.readUTF();

                System.out.println("client says: " + str);
            }
            System.out.println("Bye");
            din.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
class Send extends  Thread{
    DataOutputStream dout;
    BufferedReader br;
    Send(DataOutputStream dout,BufferedReader br){
        this.dout=dout;
        this.br=br;
    }
    public void run(){
        String str2="";
        try {
            while (!str2.equals("stop")) {

                str2 = br.readLine();
                if(!str2.equals("")) {
                    dout.writeUTF(str2);
                    dout.flush();
                }
            }
            dout.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }


}
