package engine;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
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
            // COMPILING
            try{
                ProcessBuilder builder = new ProcessBuilder("g++", this.getMainFileName());
                builder.directory(new File(this.filepath));
                Process compileProcess = builder.start();
                while(compileProcess.isAlive());
                compileProcess.destroy();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }else if(langChoice == Language.PYTHON){
            // NOT NECESSARY
        }
    }

    // start loads processOut, processIn, process
    public void start(){
        int langChoice = this.getBotLanguage();
        ProcessBuilder builder = null;
        if(langChoice == Language.JAVA){
            builder = new ProcessBuilder("java", "-cp", this.filepath, this.getMainFileNameJavaCall());
        }else if(langChoice == Language.CPP){
            builder = new ProcessBuilder("./a.out");
            builder.directory(new File(this.filepath));
        }else if(langChoice == Language.PYTHON){
            builder = new ProcessBuilder("python", this.filepath +  "/" + this.getMainFileName());
            //builder.directory(new File(this.filepath));
        }
        // start up process & grab streams
        try{
            this.process = builder.start();
            this.processIn = process.getOutputStream();
            this.processOut = process.getInputStream();
            System.out.println("Running: " + String.join(" ", builder.command()));
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public String sendAndReceive(String dataToSend, long timeLimit){
        // SEND DATA
        try{
            OutputStreamWriter writer = new OutputStreamWriter(processIn, "UTF-8");
            writer.write(dataToSend + "\n");
            writer.flush();
        } catch (UnsupportedEncodingException e){
            System.out.println(e.getMessage());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        // RECEIVE DATA
        BufferedReader programOutput = new BufferedReader(new InputStreamReader(processOut));
        long startTime = System.currentTimeMillis();
        String received = "";
        try{
            while(System.currentTimeMillis() - startTime <= timeLimit && (received = programOutput.readLine()) == null);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return (received != null && received.length() > 0) ? received : "timeout";
    }
    // CONFIG FUNCS
    public String getBotName(){
        return this.bot_config.getProperty("bot_name");
    }
    public String getMainFileName(){
        return this.bot_config.getProperty("main_file");
    }
    public String getMainFileNameJavaCall(){
        return this.bot_config.getProperty("main_file").replaceFirst(".java", "");
    }
    public String getPathToMainFile(){
        return this.filepath + "/" + this.bot_config.getProperty("main_file");
    }
    public int getBotLanguage(){
        return Integer.parseInt(this.bot_config.getProperty("bot_language"));
    }
}