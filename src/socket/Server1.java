package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server1 {
	private ServerSocket server;
	private List<PrintWriter> list;

	public Server1() {
		try {
			 server = new ServerSocket(8088);
			list = new ArrayList<PrintWriter>();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		try {
			while (true) {
				System.out.println("等待客户端...");
				Socket socket = server.accept();
				serverThread st = new serverThread(socket);
				new Thread(st).start();
				System.out.println("连接成功^_^");
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server1 server = new Server1();
		server.start();
	}

	class serverThread implements Runnable {
		private Socket socket;

		public serverThread(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			PrintWriter pw = null;
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						socket.getInputStream(), "utf-8"));
				pw = new PrintWriter(new OutputStreamWriter(
						socket.getOutputStream(), "utf-8"),true);
//				OutputStream output = socket.getOutputStream();
//				OutputStreamWriter os = new OutputStreamWriter(output,"UTF-8");
//				pw = new PrintWriter(os,true);
				synchronized (list) {
					list.add(pw);
				}
				
				String str = null;
				while ((str = br.readLine()) != null) {
					System.out.println(str);
					synchronized (list) {
						for (PrintWriter p : list) {
						p.println(str);
					}
					}
					
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
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
