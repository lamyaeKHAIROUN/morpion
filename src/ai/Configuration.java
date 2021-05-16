package ai;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Configuration implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static String fileName = "configuration.conf";

	private static Configuration configuration;
	private int size;
	private String algorithm;
	private int taux;
	private int level;
	private Configuration() {
		size = 3;
		algorithm = "MiniMax";
		taux = 10;
		level = 1;
	}
	public static Configuration getConfiguration() {
		if(configuration == null) configuration = new Configuration();
		return configuration;
	}
	public static void setConfiguration(Configuration configuration) {
		Configuration.configuration = configuration;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getTaux() {
		return taux;
	}
	public void setTaux(int taux) {
		this.taux = taux;
	}
	@Override
	public String toString() {
		return size + ", " + algorithm  + ", " + level  + ", " + taux ;
	}
	public Configuration saveConfig() {
		File file = new File(fileName);
        ObjectOutputStream oos;
        try {
			oos = new ObjectOutputStream(
					new BufferedOutputStream(
			            new FileOutputStream(file)
			        ));
	        oos.writeObject(this);
	        oos.close();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return configuration;
	}
	public static Configuration loadConfig() {
		File file = new File(fileName);
        ObjectInputStream ois;
        try {
        	ois = new ObjectInputStream(
		              new BufferedInputStream(
		  	                new FileInputStream(file)
			        ));
        	Object obj = ois.readObject();
        	if(obj instanceof Configuration)
        		configuration = (Configuration) obj;
        	ois.close();
        } catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return configuration;
	}
	
}
