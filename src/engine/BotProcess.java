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
import engine.constants.Language;

public class BotProcess{
    private InputStream processOut, processErr;
    private OutputStream processIn;
    private Process process;
    private Properties bot_config;
    private String filepath;

    public BotProcess(String filepath){
        // LOAD IN CONFIG
        this.filepath = filepath.trim();
        try{
        	this.filepath = (new File(this.filepath)).getAbsolutePath();
            File configFile = new File(this.filepath + "/bot_config.properties");
            System.out.println("Looking for config file in: " + configFile.getAbsolutePath());
            FileReader configFileReader = new FileReader(configFile);
            Properties config = new Properties();
            config.load(configFileReader);
            configFileReader.close();
            this.bot_config = config;
        } catch (FileNotFoundException ex){
            System.out.println("Could not find 'bot_config.properties'");
            System.out.println(", looked at: " + this.filepath + "/bot_config.properties");
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
    public void start(Engine engine){
        int langChoice = this.getBotLanguage();
        ProcessBuilder builder = null;
        if(langChoice == Language.JAVA){
            builder = new ProcessBuilder("java", this.getMainFileNameJavaCall());
            builder.directory(new File(this.filepath));
            System.err.println("this.filepath: " + this.filepath);
            System.err.println(builder.directory().toString());
        }else if(langChoice == Language.CPP){
            builder = new ProcessBuilder("./a.out");
            builder.directory(new File(this.filepath));
        }else if(langChoice == Language.PYTHON){
            builder = new ProcessBuilder(engine.getPythonCommand(), this.filepath +  "/" + this.getMainFileName());
            //builder.directory(new File(this.filepath));
        }
        // start up process & grab streams
        try{
        	System.out.println("Running: " + String.join(" ", builder.command()));
            this.process = builder.start();
            this.processIn = process.getOutputStream();
            this.processOut = process.getInputStream();
            this.processErr = process.getErrorStream();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public String sendAndReceive(int id, String dataToSend, long timeLimit){
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
        BufferedReader programOutput = new BufferedReader(new InputStreamReader(this.processOut));
        BufferedReader errorOutput = new BufferedReader(new InputStreamReader(this.processErr));
        long startTime = System.currentTimeMillis();
        String received = "";
        try{
            while(System.currentTimeMillis() - startTime <= timeLimit && (this.processOut.available() == 0 || (received = programOutput.readLine()) == null)){
                //System.out.println("Waiting " + this.getBotName() + " " + (System.currentTimeMillis() - startTime) + " " + timeLimit);
            }
            String errRec = "";
            while(errorOutput.ready()) {
            	errRec = errorOutput.readLine();
            	if(errRec == null || errRec.length() == 0) {
            		break;
            	}
            	System.err.println(id + ": " + errRec);
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return (received != null && received.length() > 0) ? received : "timeout";
    }
    public void send(String dataToSend){
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
    public void close() {
    	this.process.destroy();
    }
}