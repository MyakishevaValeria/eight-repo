/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servermain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class Server {
     
    ServerSocket serverSocket = null;
    
     public Server() {
     initServer(8000);
     getFile();
     getStr();
    }
     /**
      * метод подключения к серверу
      * @param port - номер порта 
      */
    public void initServer(int port){  
		try {  
			serverSocket = new ServerSocket(port); // Подключиться к серверу
                        System.out.println("Server started");
			} catch (IOException e) {   
				e.printStackTrace();  
				} 
		 }

    /**
     * метод для получения файла от клиента
     */
    public void getFile() {
        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;
        byte[] bytes = new byte[16*1024];
        try {
            socket = serverSocket.accept(); //ожидание соединения
            in = socket.getInputStream();
            out = new FileOutputStream("test2.txt"); //запись в файл

        int count;
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count); //записываем в файл 
        }
        //закрываем потоки
        out.close();
        in.close();
        socket.close();
        } catch (Exception e) {   
            e.printStackTrace();  
				}
    }
    /**
     * метод для отправки строки клиенту
     */
    public void getStr() { 
     Socket s = null;
     PrintStream ps = null;
     try {
           s = serverSocket.accept(); //ожидание соединения
           File file = new File("test2.txt");
           ps = new PrintStream(s.getOutputStream());
           FileReader fr = new FileReader(file);
           BufferedReader bf = new BufferedReader(fr);
            String ss="";
            while (bf.ready()) {
                ss=ss+bf.readLine();
            }
            String[] words = ss.split("   ");
            System.out.println(words);
            int randomIdx = new Random().nextInt(words.length);
            String m =words[randomIdx];
            //помещение строки в буфер
            ps.println(m);
            //отправка содержимого буфера клиенту
            ps.flush();
    }  catch (IOException e) {
      System.err.println("Ошибка соединения потока: "+ e);
    } finally {
         if (ps!= null){
             ps.close();
         }
         if (s!=null){
             try {  //разрыв соединения
                 s.close();
             } catch (IOException e){
                 e.printStackTrace();
             }
         }
     }
    }     
}
