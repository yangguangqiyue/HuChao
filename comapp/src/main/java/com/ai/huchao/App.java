package com.ai.huchao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.sf.json.JSONObject;
/**
 *  The class is aim to achieve a command line application 
 */
public class App {
	public static int flags = 0;
	/**init java project, write JSON data to AddressBook2.json file as backend persistent storage*/
	public  int init() throws IOException {
		String url = getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath()
				+ "AddressBook2.json";
		File file = new File(url);
		String sets = "{\"entries\":{\"lilei\":{\"age\":27,\"mobile\":\"13700000000\",\"address\":\"Earth somewhere\"},\"hanmeimei\":{\"age\":26,\"mobile\":\"13700000001\",\"address\":\"Earth somewhere else\"}}}";
		if (file.exists()) {
			return 1;
		} else {
			FileWriter fw = new FileWriter(url);
			PrintWriter out = new PrintWriter(fw);
			out.write(sets);
			out.println();
			fw.close();
			out.close();
		}
		return 1;
	}
    /**read AddressBook2.json file to obtain JSON data when we use command*/
	public  String readfile() throws IOException {
		String lineString = null;
		String fileString = "";
		String url = getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath()
				+ "AddressBook2.json";
		File f = new File(url);
		BufferedReader reader = new BufferedReader(new FileReader(f));
		while ((lineString = reader.readLine()) != null) {
			fileString += lineString;
		}
		reader.close();
		return fileString;

	}
    /**when the command execute finish,write JSON data to AddressBook2.json file*/
	public void writeFile(String sets) throws IOException {
		String url = getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath()
				+ "AddressBook2.json";
		FileWriter fw = new FileWriter(url);
		PrintWriter out = new PrintWriter(fw);
		out.write(sets);
		out.println();
		fw.close();
		out.close();
	}
    /**'ls' command to list the items in current position*/
	public int Jsonls() throws IOException {
		String jsonText = readfile();
		JSONObject json = JSONObject.fromObject(jsonText);
		Iterator<?> keys = json.keys();
		String jsonls = "";
		if (flags == 0) {
			while (keys.hasNext()) {
				jsonls = jsonls + keys.next() + ".";
			}
			String arrs[] = jsonls.split("\\.");
			for (int i = 0; i < arrs.length; i++) {
				System.out.print(arrs[i] + " ");
			}
			System.out.println();
			return 1;
		} else {
			String key = (String) keys.next();
			String value = json.get(key).toString();
			JSONObject json2 = JSONObject.fromObject(value);
			Iterator<?> keys2 = json2.keys();
			while (keys2.hasNext()) {
				jsonls = jsonls + keys2.next() + ".";
			}
			String arrs[] = jsonls.split("\\.");
			for (int i = 0; i < arrs.length; i++) {
				System.out.print(arrs[i] + " ");
			}
			System.out.println();
			return 1;
		}
	}
    /**'cd' command to go to the entry like go to a directory*/
	public int Jsoncd(String entry) throws IOException {
		String jsonText = readfile();
		JSONObject json = JSONObject.fromObject(jsonText);
		Iterator<?> keys = json.keys();
		String key = (String) keys.next();
		if (key.equalsIgnoreCase(entry)) {
			flags = 1;
			return 1;
		} else
			
			return 0;
	}
    /**'cat' command to display th item data*/
	public int Jsoncat(String name) throws IOException {
		String catText = readfile();
		JSONObject json = JSONObject.fromObject(catText);
		Iterator<?> keys = json.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String value = json.get(key).toString();
			JSONObject json2 = JSONObject.fromObject(value);
			Iterator<?> keys2 = json2.keys();
			while (keys2.hasNext()) {
				String key2 = (String) keys2.next();
				String value2 = json2.get(key2).toString();
				if (key2.equalsIgnoreCase(name)) {
					System.out.println("\"" + key2 + "\":" + value2);
					return 1;
				}
			}
		}

		return 0;
	}
	/**'add' command to add new address entry to JSON,the key should not duplicated	*/
	public int Jsonadd(String inkey,String invalue) throws IOException {
		String catText = readfile();
		JSONObject json = JSONObject.fromObject(catText);	
		Iterator<?> keyc = json.keys();	
		while (keyc.hasNext()) {
			String key = (String) keyc.next();
			String value = json.get(key).toString();
			JSONObject jsonc = JSONObject.fromObject(value);
			if(jsonc.get(inkey)!=null){
				System.out.println("error: you cann't add duplicated key");
				return 0;
			}
		}	
		
		Iterator<?> keys = json.keys();
		if (keys.hasNext()) {
			String key = (String) keys.next();
			String value = json.get(key).toString();
			JSONObject json2 = JSONObject.fromObject(value);
			json2.put(inkey, invalue);
			json.put(key, json2.toString());
		}
		writeFile(json.toString());
		return 1;
	}
    /**'remove' command to get one or more address entries*/
	public int Jsonremove(String inkey) throws IOException {
		String catText = readfile();
		JSONObject json = JSONObject.fromObject(catText);
		Iterator<?> keyc = json.keys();
		if (keyc.hasNext()) {
			String key = (String) keyc.next();
			String value = json.get(key).toString();
			JSONObject jsonc = JSONObject.fromObject(value);
			if(jsonc.get(inkey)==null){
				System.out.println("error: There isn't "+inkey);
				return 0;
			}
		}
		
		Iterator<?> keys = json.keys();
		if (keys.hasNext()) {
			String key = (String) keys.next();
			String value = json.get(key).toString();
			JSONObject json2 = JSONObject.fromObject(value);
			json2.remove(inkey);
			json.put(key, json2.toString());
			writeFile(json.toString());
			return 1;
		}
		return 0;
	}
	/**display help message*/
    public void helpmessage(){
		System.out.println("you can only use ls,cd,cat, add, remove command,if you want to quit you can input !quit");
		System.out.println("ls          to list the items in current position");
		System.out.println("cd          command to go to the entry like go to a directory");
		System.out.println("cat [-key]  command to display th item data");
		System.out.println("add         command to add new address entry to JSON");
		System.out.println("remove      command to get one or more address entries");
    }
  /**use to judge the command. If it is right, through ExecutorService produce thread to deal with it*/
	public void startcommand() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		ExecutorService es = Executors.newSingleThreadExecutor();
		while (true) {
			String input = scanner.nextLine();

			final String[] in = input.split(" ");
			if (in[0].equalsIgnoreCase("ls")&&in.length==1) {
				es.execute(new Runnable() {
					public void run() {
						try {
							App ale = new App();
							ale.Jsonls();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			else if (in[0].equalsIgnoreCase("cd")&&in.length==2) {
				es.execute(new Runnable() {
					public void run() {
						try {
							App ale = new App();
								ale.Jsoncd(in[1]);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

			}
			else if (in[0].equalsIgnoreCase("cat")&&in.length==2) {
				es.execute(new Runnable() {
					public void run() {
						try {
							App ale = new App();
							ale.Jsoncat(in[1]);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

			}
			else if (in[0].equalsIgnoreCase("add")&&in.length==1) {
				if (flags == 1) {
					@SuppressWarnings("resource")
					Scanner sa = new Scanner(System.in);
					System.out.print("key:");
					final String key = sa.nextLine();
					System.out.print("value:");
					final String invalue = sa.next();
					if(invalue.startsWith("{")&& invalue.endsWith("}")){
					es.execute(new Runnable() {
						public void run() {
							try {
								App ale = new App();
								ale.Jsonadd(key,invalue);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					}else System.out.println("The value is wrong");
				}
			}
			else if (in[0].equalsIgnoreCase("remove")&&in.length==1) {
				if (flags == 1) {
					System.out.print("please give the key:");
					@SuppressWarnings("resource")
					Scanner sr = new Scanner(System.in);
					final String key = sr.nextLine();
					es.execute(new Runnable() {
						public void run() {
							try {
								App ale = new App();
								ale.Jsonremove(key);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
			}
			else if (in[0].equalsIgnoreCase("!help")&&in.length==1) {
				helpmessage();
			}
			else if (in[0].equalsIgnoreCase("!quit")&&in.length==1) {
				es.shutdown();
				break;
			}
			else{
				helpmessage();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		App app = new App();
		app.init();
		app.startcommand();
	}
}
