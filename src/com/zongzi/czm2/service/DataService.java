package com.zongzi.czm2.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.zongzi.czm2.bean.Message;
import com.zongzi.czm2.utils.Constant;

public class DataService {
	public final static String FAVORITES_FILE = "favorites_czm.text";
	public final static String AD_FILE = "ad_czm.text";

	/**
	 * 开始游戏随机读取
	 * @param context
	 * @return
	 */
	public Message getOneRandomBytxt(Context context) {
		Message message = null;
		try {
			// Return an AssetManager instance for your application's package
			int random = new Random().nextInt(Constant.recodCount) + 1;
			InputStream is = context.getAssets().open("nj_3.txt");
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(is));
			String tempString = bufferedReader.readLine();
			int index = 0;
			while (tempString != null) {
				++index;
				Log.e("ssssss", tempString);
				if (index >= random) {

					String[] joke = tempString.split("---");
					Log.e("lenght", joke.length + "");
					if (joke.length >= 3) {
						message = new Message();
						message.setId(Integer.parseInt(joke[0]));
						message.setMessage(joke[1]);
						message.setAnswer(joke[2]);
						break;
					}
				}
				tempString = bufferedReader.readLine();
			}
			bufferedReader.close();
			is.close();

		} catch (IOException e) {
			// Should never happen!
			throw new RuntimeException(e);
		}
		return message;
	}
	
	/**
	 * 读取所有
	 * @param context
	 * @return
	 */
	public List<Message> getAllMessage(Context context) {
		List<Message> messages = new ArrayList<Message>();
		Message message = null;
		try {
			// Return an AssetManager instance for your application's package
			InputStream is = context.getAssets().open("nj_3.txt");
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(is));
			String tempString = bufferedReader.readLine();
			while (tempString != null) {
				String[] joke = tempString.split("---");
				Log.e("lenght", joke.length + "");
				if (joke.length >= 3) {
					message = new Message();
					message.setId(Integer.parseInt(joke[0]));
					message.setMessage(joke[1]);
					message.setAnswer(joke[2]);
					messages.add(message);
				}
				
				tempString = bufferedReader.readLine();
			}
			bufferedReader.close();
			is.close();

		} catch (IOException e) {
			// Should never happen!
			throw new RuntimeException(e);
		}
		return messages;
	}

	/**
	 * 
	 * @param context
	 * @param id
	 *            //当前id
	 * @return
	 */
	public Message getMessageById(Context context, int id) {
		Message message = null;
		try {
			// Return an AssetManager instance for your application's package
			// int random = new Random().nextInt(Constant.recodCount)+1;
			if (id > Constant.recodCount) {
				id = Constant.recodCount;
			}
			if (id < 1) {
				id = 1;
			}
			InputStream is = context.getAssets().open("nj_3.txt");
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(is));
			String tempString = bufferedReader.readLine();
			int index = 0;
			while (tempString != null) {
				++index;
				Log.e("ssssss", tempString);
				if (index >= id) {
					String[] joke = tempString.split("---");
					Log.e("lenght", joke.length + "");
					if (joke.length >= 3) {
						message = new Message();
						message.setId(Integer.parseInt(joke[0]));
						message.setMessage(joke[1]);
						message.setAnswer(joke[2]);
						break;
					}
				}
				tempString = bufferedReader.readLine();
			}
			bufferedReader.close();
			is.close();

		} catch (IOException e) {
			// Should never happen!
			throw new RuntimeException(e);
		}
		return message;
	}

	/**
	 * 增加收藏
	 * 
	 * @param index
	 */
	public void addFavorites(String message) {
		boolean has = false;
		if (isSdCardExist()) {
			try {
				String path = getSdCardPath();
				File favorites = new File(path + "/" + FAVORITES_FILE);
				if (!favorites.exists()) {
					favorites.createNewFile();
				}
				BufferedReader reader = new BufferedReader(new FileReader(
						favorites));
				String temString = null;
				while ((temString = reader.readLine()) != null) {
					if (temString.equals(message)) {
						has = true;
						break;
					}
				}
				reader.close();
				if (!has) {
					FileWriter fw = new FileWriter(path + "/" + FAVORITES_FILE,
							true);
					PrintWriter pw = new PrintWriter(fw);
					pw.println(message); // 字符串末尾不需要换行符
					pw.close();
					fw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	/**
	 * 删除一条收藏
	 * @param index
	 */
	public void deletFavorites(int index) {
		try {
			String path = getSdCardPath();
			BufferedReader br = new BufferedReader(new FileReader(path + "/" + FAVORITES_FILE));
			StringBuffer sb = new StringBuffer(4096);
			int tempInt = 0;
			String temp = null;
			while ((temp = br.readLine()) != null) {
				++tempInt;
				if(index == tempInt)
					continue;
				sb.append(temp).append("\n");
			}
			br.close();
			BufferedWriter bw = new BufferedWriter(new FileWriter(path + "/" + FAVORITES_FILE));
			bw.write(sb.toString());
			bw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	/**
	 * 
	 * @return
	 */
	public List<Message> getAllFavorites() {
		List<Message> messages = new ArrayList<Message>(); 
		Message message = null;
		try {
			String path = getSdCardPath();
			BufferedReader br = new BufferedReader(new FileReader(path + "/" + FAVORITES_FILE));
			String temp = null;
			while ((temp = br.readLine()) != null) {
				String[] tStrings = temp.split("---");
				message = new Message();
				message.setId(Integer.parseInt(tStrings[0]));
				message.setMessage(tStrings[1]);
				message.setAnswer(tStrings[2]);
				messages.add(message);
			}
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return messages;
	}

	/*
	 * public Message getOneRandomBySlx(Context context) { try {
	 * Log.e("fsfsfdfs", String.valueOf(isSdCardExist())); if(isSdCardExist()){
	 * String filePath = getSdCardPath()+"/nj_2.xlsx"; // Excel文件所在路径
	 * Log.e("filePath", filePath); File file = new File(filePath);
	 * if(!file.exists()){ file.createNewFile(); } InputStream is =
	 * context.getAssets().open("nj_2.xlsx"); file = inputstreamtofile(is,
	 * file);
	 * 
	 * Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
	 * 
	 * Sheet sheet = wb.getSheet(0); // 从工作区中取得页（Sheet） Log.e("sssssssddddd",
	 * sheet.getName()); for (int i = 0; i < sheet.getRows(); i++) { //
	 * 循环打印Excel表中的内容 for (int j = 0; j < sheet.getColumns(); j++) { Cell cell =
	 * sheet.getCell(j, i); Log.e("ssssss", cell.getContents()); }
	 * System.out.println(); } }
	 * 
	 * } catch (BiffException e) { e.printStackTrace(); } catch (IOException e)
	 * { e.printStackTrace(); } return null; }
	 */

	public File inputstreamtofile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 判断SDCard是否存在 [当没有外挂SD卡时，内置ROM也被识别为存在sd卡]
	 * 
	 * @return
	 */
	public boolean isSdCardExist() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取SD卡根目录路径
	 * 
	 * @return
	 */
	public String getSdCardPath() {
		boolean exist = isSdCardExist();
		String sdpath = "";
		if (exist) {
			sdpath = Environment.getExternalStorageDirectory()
					.getAbsolutePath()+"/njjzw2";
			if(!new File(sdpath).exists()){
				new File(sdpath).mkdir();
			}
		} else {
			sdpath = "不适用";
		}
		return sdpath;

	}
	
	
	public void initData(){
		if (isSdCardExist()) {
			try {
				String path = getSdCardPath();
				File favorites = new File(path + "/" + AD_FILE);
				if (!favorites.exists()) {
					favorites.createNewFile();
				}
				BufferedReader reader = new BufferedReader(new FileReader(
						favorites));
				String temString = reader.readLine();
				reader.close();
				if (temString==null) {
					FileWriter fw = new FileWriter(path + "/" + AD_FILE,
							true);
					PrintWriter pw = new PrintWriter(fw);
					pw.println("showAdAmout=3"); // 字符串末尾不需要换行符
					pw.println("showDialog=1"); // 字符串末尾不需要换行符
					pw.println("pass_count=1"); // 字符串末尾不需要换行符
					Log.e("sssss", "sssssssss");
					pw.close();
					fw.close();
				}else {
					Log.e("sssss", "ssssssdddddssssss");
					String[] ssStrings = temString.split("=");
					Constant.showAdAmout = Integer.parseInt(ssStrings[1]);
					temString = reader.readLine();
					if(temString!=null){
						ssStrings = temString.split("=");
						Constant.showDialog = Integer.parseInt(ssStrings[1]);
					}
					temString = reader.readLine();
					if(temString!=null){
						ssStrings = temString.split("=");
						Constant.pass_count = Integer.parseInt(ssStrings[1]);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	
	}
	
	public void alertAdConfig(int pass_count){
		try {
			String path = getSdCardPath();
			StringBuffer sb = new StringBuffer(4096);
			String temp = null;
			temp = "showAdAmout=5";	
			sb.append(temp).append("\n");
			temp = "showDialog=2";	
			sb.append(temp).append("\n");
			temp = "pass_count="+pass_count;	
			sb.append(temp).append("\n");
			BufferedWriter bw = new BufferedWriter(new FileWriter(path + "/" + AD_FILE));
			bw.write(sb.toString());
			bw.close();
			Constant.showAdAmout = 5;
			Constant.showDialog = 2;
			Constant.pass_count = pass_count;
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
