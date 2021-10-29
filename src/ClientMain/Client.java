/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientMain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


/**
 *
 * @author Admin
 */
public class Client {
    
    String host = "127.0.0.1";
    public Client(){
       sendFile();
       getInfo();
    }
    /**
     * метод для отправки файла серверу
     */
    private void sendFile() {
        Socket socket = null;
        try{  
            socket = new Socket(host, 8000);
        File file = new File("test.txt");
        // Get the size of the file
        long length = file.length();
        byte[] bytes = new byte[16 * 1024];
        InputStream in = new FileInputStream(file); //чтение из файла
        OutputStream out = socket.getOutputStream();

        int count;
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
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
     * метод для получения информации от сервера
     */
    private void getInfo() {
        BufferedReader br=null;
        Socket socket = null;
        try {
         socket = new Socket(host, 8000);
         //открывает потоки для чтения ответа от сервера 
         br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         //читаем содержимое потоков 
         String message = br.readLine();
         //выводим рубари
         System.out.println("Рубари: "+message);
         } catch (IOException e) {
             System.err.println("ошибка: "+ e);
         } finally {
            if (br!= null){
                if (socket!= null){
                    try{  //разрыв соединения
                        socket.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
