package command;

import java.io.File;
import java.util.Scanner;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

/**
 * @author zhangweixiao
 */
public class CommandLineHelper {

    public static CommandArg arg = new CommandArg();
    public static CommandArg runCommand(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("h","help",false,"帮助");
        options
            .addOption("m", "mode", true, "书签格式，1（默认w）按照章节和文字大小提取（第一章 XXX-1.1-1.1.1）2 按照文字大小提取)");
        options.addOption("f","file",true,"文件路径");
        options.addOption("s","size",true,"正文的最大字体大小");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        boolean hasHelpOpt = cmd.hasOption("h");
        if(hasHelpOpt == true){
            System.out.println("-m --mode 书签格式，1-（默认w）按照章节和文字大小提取（第一章 XXX-1.1-1.1.1）2-按照文字大小提取\n" +
                    "-f --file 文件路径\n" +
                    "-s --size 正文的最大字体大小");
        }
        String modeStr = cmd.getOptionValue("m");
        if (modeStr == null) {
            arg.setMode(1);
        } else {
            try {
                int mode = Integer.parseInt(modeStr);
                arg.setMode(mode);
            } catch (NumberFormatException e) {
                System.out.println("mode为1或者2");
            }
        }
        String sizeStr =cmd.getOptionValue("s");
        if(sizeStr != null){
            arg.setSize(Float.valueOf(sizeStr));
        }
        String fileStr = cmd.getOptionValue("f");
        if(fileStr == null){
            System.out.println("请输入文件路径");
            fileStr = scan();
        }
        while ( !checkFileName(fileStr)){
            fileStr=scan();
        }
        arg.setFileName(fileStr);
        return arg;
    }
    private static String scan(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    private static boolean checkFileName(String fileName){

        if(!fileName.endsWith(".pdf")){
            System.out.println("请输入pdf文件");
            return false;
        }
        File file=new File(fileName);
        if(!file.exists()){
            System.out.println("文件不存在");
            return false;
        }
        return true;

    }


}
