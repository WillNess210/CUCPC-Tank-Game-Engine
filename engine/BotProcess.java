package engine;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import constants.Language;

public class BotProcess{
    private InputStream processOut;
    private OutputStream processIn;
    private Process process;
    private Properties bot_config;
    private String filepath;

    public BotProcess(String filepath){
        // LOAD IN CONFIG
        this.filepath = filepath;
        try{
            File configFile = new File(filepath + "/bot_config.properties");
            FileReader configFileReader = new FileReader(configFile);
            Properties config = new Properties();
            config.load(configFileReader);
            configFileReader.close();
            this.bot_config = config;
        } catch (FileNotFoundException ex){
            System.out.println("Could not find 'bot_config.properties', looked at: " + filepath + "/bot_config.properties");
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        // COMPILE PROGRAM
    }

    // PROCESS HANDLING FUNCS
    // compiles files, if necessary
    public void compile(){
        int langChoice = this.getBotLanguage();
        if(langChoice == Language.JAVA){
            // COMPILING
            try{
                ProcessBuilder builder = new ProcessBuilder("bash", "-c", "javac $(find -name '*.java')");
                builder.directory(new File(this.filepath));
                Process compileProcess = builder.start();
                while(compileProcess.isAlive());
                compileProcess.destroy();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }else if(langChoice == Language.CPP){

        }else if(langChoice == Language.PYTHON){
            // NOT NECESSARY
        }
    }

    // start loads processOut, processIn, process
    public void start(){
        int langChoice = this.getBotLanguage();
        if(langChoice == Language.JAVA){

        }else if(langChoice == Language.CPP){

        }else if(langChoice == Language.PYTHON){
            
        }
    }

    // CONFIG FUNCS
    public String getBotName(){
        return this.bot_config.getProperty("bot_name");
    }
    public String getPathToMainFile(){
        return this.filepath + "/" + this.bot_config.getProperty("main_file");
    }
    public int getBotLanguage(){
        return Integer.parseInt(this.bot_config.getProperty("bot_language"));
    }
}