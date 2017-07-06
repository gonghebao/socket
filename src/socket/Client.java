package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private Socket socket;
	public Client(){
		try {
			Socket socket = new Socket("127.0.0.1",8088);
			this.socket = socket;
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public void start(){
		PrintWriter pw = null ;
		try {
			pw = new PrintWriter(
						new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true
					);
			
			clientThread ct = new clientThread();
			new Thread(ct).start();
			Scanner scan = new Scanner(System.in);
			String message = null;
			System.out.println("请输入");
			while(true){
				message = scan.nextLine();
				if("exit".equals(message)){
					break;
				}
				pw.println(message);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			pw.close();
		}
	}
	public static void main(String[] args) {
		Client client = new Client();
		client.start();
	}
	class clientThread implements Runnable{
		public void run() {
			try {
				BufferedReader br = new BufferedReader(
							new InputStreamReader(socket.getInputStream(),"utf-8")
						);
				String str = null;
				while((str = br.readLine())!=null){
					System.out.println(str);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
