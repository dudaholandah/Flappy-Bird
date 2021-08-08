
package flappybird_grupo11;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Files {
    protected static int[] recordes = new int[3];
    protected static String[] dates = new String[3];

    public static int[] getRecordes() {
        return recordes;
    }

    public static void setRecordes(int[] recordes) {
        Files.recordes = recordes;
    }

    public static String[] getDates() {
        return dates;
    }

    public static void setDates(String[] dates) {
        Files.dates = dates;
    }
    
    public static void read(String file){
        try(
                BufferedReader bufferRead = new BufferedReader(new FileReader(file));
                ){
            
            String linha = "";
            int i = 0;
            
            while(true){
                if(linha != null){
                    if(!linha.equals("")){
                        if(i>=3){
                            break;
                        }
                        recordes[i] = Integer.parseInt(linha);
                        i++;
                    }
                }
                else{
                    break;
                }
                
                linha = bufferRead.readLine();
            }
        }catch(IOException e){
            //e.printStackTrace();
            //return "";
        }
    }
    
    public static void readDate(String file){
        try(
                BufferedReader bufferRead = new BufferedReader(new FileReader(file));
                ){
            
            String linha = "";
            int i = 0;
            
            while(true){
                if(linha != null){
                    if(!linha.equals("")){
                        if(i>=3){
                            break;
                        }
                        dates[i] = linha;
                        i++;
                    }
                }
                else{
                    break;
                }
                
                linha = bufferRead.readLine();
            }
        }catch(IOException e){
            //e.printStackTrace();
            //return "";
        }
    }
    
    public static void write(String file){
        String record = "";
        
        for(int i=0; i<3; i++){
            record += Integer.toString(recordes[i]);
            record += "\n";
        }
        
        try(
                FileWriter fl = new FileWriter(file, false);
                BufferedWriter buffer = new BufferedWriter(fl);
                PrintWriter writer = new PrintWriter(buffer);
                
                ){
            
            fl.append(record);
            
        }catch(IOException e){
            //return;
            //e.printStackTrace();
        }
    }
    
    public static void writeDate(String file){
        String record = "";
        
        for(int i=0; i<3; i++){
            record += dates[i];
            record += "\n";
        }
        
        try(
                FileWriter fl = new FileWriter(file, false);
                BufferedWriter buffer = new BufferedWriter(fl);
                PrintWriter writer = new PrintWriter(buffer);
                
                ){
            
            fl.append(record);
            
        }catch(IOException e){
            //return;
            //e.printStackTrace();
        }
    }
}
