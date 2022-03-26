package com.company;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

class Main{
    public static void main(String args[])throws Exception{
        Socket server=new Socket("localhost",3333);
        DataInputStream din=new DataInputStream(server.getInputStream());
        DataOutputStream dout=new DataOutputStream(server.getOutputStream());
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
          // Scanner br=new Scanner(System.in);
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

                System.out.println("Server says: " + str);
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


            System.out.println("Bye");
            dout.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }


}