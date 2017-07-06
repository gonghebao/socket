package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cillent {
	private Socket socket;
	public Cillent(){
		
		try {
			System.out.println("ka");
			socket = new Socket("localhost",8088);
			System.out.println("ok");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	public void start(){
		try {
			OutputStream o = socket.getOutputStream();
			OutputStreamWriter os = new OutputStreamWriter(o,"UTF-8");
			PrintWriter p = new PrintWriter(os,true);
			Scanner s = new Scanner(System.in);
			ServerHanlder ss = new ServerHanlder();
			Thread t = new Thread(ss);
			t.start();
			while(true){
				String s1 = s.nextLine();
				p.println(s1);
			}
			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
	}
	public static void main(String[] args) {
		Cillent c =new Cillent();
		c.start();
	}
	class ServerHanlder implements Runnable{
		public void run(){
			try {
				InputStream i = socket.getInputStream();
				InputStreamReader put = new InputStreamReader(i,"UTF-8");
				BufferedReader buff = new BufferedReader(put);
				String  s = null;
				while((s = buff.readLine())!=null){
					System.out.println(s);
				}
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}finally{
				try {
					socket.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			
		}
	}

}
