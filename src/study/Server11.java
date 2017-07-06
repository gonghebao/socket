package study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server11 {
	private ServerSocket sever;
	private List<PrintWriter> list;
	public Server11(){
		try {
			sever = new ServerSocket(8088);
			list = new ArrayList<PrintWriter>();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public void start(){
		try {
			System.out.println("kaishi");
			while(true){
			Socket socket = sever.accept();
			System.out.println("OK");
			CillentHanlder c = new CillentHanlder(socket);
			Thread t = new Thread(c);
			t.start();
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		Server11 s = new Server11();
		s.start();
	}
	class CillentHanlder implements Runnable{
		private Socket socket;
		public CillentHanlder(Socket s){
			this.socket=s;
		}
		public void run(){
			InputStream is;
			PrintWriter pw  = null;
			try {
				is = socket.getInputStream();
				InputStreamReader put = new InputStreamReader(is,"UTF-8");
				BufferedReader buff = new BufferedReader(put);
				OutputStream output = socket.getOutputStream();
				OutputStreamWriter os = new OutputStreamWriter(output,"UTF-8");
				pw = new PrintWriter(os,true);
				synchronized (list) {
					list.add(pw);
				}
				String s = null;
				while((s = buff.readLine())!=null){
					System.out.println(s);
					synchronized (list) {
						for (PrintWriter printWriter : list) {
							printWriter.println(s);
						}
					}
				}
				
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}finally{
				list.remove(pw);
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
