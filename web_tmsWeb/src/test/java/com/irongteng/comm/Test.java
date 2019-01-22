package com.irongteng.comm;

import java.net.*;

class Test {
    public static void main(String[] args) throws Exception {
        send();
        recv();
        if (args.length > 0) {
            System.out.println("to send");
            //send();
        } else {
            System.out.println("to recv");
            //recv();
        }
    }

    public static void send() throws Exception {
        DatagramSocket s = new DatagramSocket(null);
        s.setReuseAddress(true);
        s.bind(new InetSocketAddress(8888));
        byte[] b = new byte[100];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) i;
        }
        DatagramPacket p = new DatagramPacket(b, 0, b.length, new InetSocketAddress("localhost", 9999));
        s.send(p);
    }

    public static void recv() throws Exception {
        DatagramSocket s = new DatagramSocket(null);
        s.setReuseAddress(true);
        s.bind(new InetSocketAddress(9999));
        byte[] b = new byte[100];
        DatagramPacket p = new DatagramPacket(b, 0, b.length);
        s.receive(p);
        
        System.out.println(p.getAddress() + ":" + p.getPort() + " " + p.getLength());
    }
    
    

};