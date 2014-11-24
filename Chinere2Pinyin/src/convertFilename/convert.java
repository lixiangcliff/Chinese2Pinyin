package convertFilename;
import java.io.File;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class convert {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        //给定文件所在目录
        String path = "e:\\mp3"; 
        processAllFiles(path);
	}
	
	//处理所有文件
	public static void processAllFiles(String path) {
		//ArrayList<String> list = new ArrayList<String>();
		System.out.println("开始处理" + path + "下的文件：...");
		try {
			File file = new File(path);
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				String curPath  = path + "\\";
				String oldName = filelist[i];
				String newName = convertToPinyin(oldName);
				System.out.println("原名：" + oldName);
				renameFile(curPath, oldName, newName);
				System.out.println("现名：" + newName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return list;
	} 
	
	//重命名文件
	public static void renameFile(String path, String oldname, String newname) {
		if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(path + "/" + oldname);
			File newfile = new File(path + "/" + newname);
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				System.out.println(newname + "已经存在！");
			else {
				oldfile.renameTo(newfile);
			}
		}
	}
	
	//讲给予的中文转化成拼音并返回
	public static String convertToPinyin(String oldName) {
		StringBuilder newName = new StringBuilder();
		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
		//outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		//以下两个必须合用，可以表示出声调号（吕：lǚ）。如果没有如下两行，则返回拼音+声调的数字（吕 : lu3）
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        outputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
		for (int i = 0; i < oldName.length(); i++) {
			char curChar = oldName.charAt(i);	
			String[] curStrArray = null;
			try {
				curStrArray = PinyinHelper.toHanyuPinyinStringArray(curChar, outputFormat);
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			} finally {
				if (curStrArray == null || curStrArray.length == 0) { //当前char不是拼音，则返回原来的char
					newName.append(curChar);
				} else { //是拼音 则加入
					String str = curStrArray[0];
					str = Character.toUpperCase(str.charAt(0)) +  str.substring(1); //拼音第一个字母变大写
					newName.append(str);
				}
			}
		}
		return newName.toString();
	}
	

}
