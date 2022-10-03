package properties;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class MessagesProperty {

	private static final Logger logger = LogManager.getLogger(MessagesProperty.class);

	public static final String DB_SERVER ;
	public static final String DB_WRITER_USERNAME;
	public static final String DB_WRITER_PASSWORD;
	public static final String DB_NAME;
	
	public static final String PASSWORD;
	public static final String BOT_NAME;
	public static final String URL;
	
	

	static {
		Properties prop =  loadPropertyValues("/Config.properties");
		DB_SERVER = prop.getProperty("DB_SERVER").trim();
		DB_WRITER_USERNAME = prop.getProperty("DB_WRITER_USERNAME").trim();
		DB_WRITER_PASSWORD = prop.getProperty("DB_WRITER_PASSWORD").trim();
		DB_NAME = prop.getProperty("DB_NAME").trim();
		
		PASSWORD = prop.getProperty("PASSWORD").trim();
		BOT_NAME = prop.getProperty("BOT_NAME").trim();
		URL = prop.getProperty("URL").trim();
	}

	public static Properties loadPropertyValues(String fileName) {
		Properties prop = null;
		try (InputStream in = MessagesProperty.class.getResourceAsStream("/properties" + fileName)) {
			prop = new Properties();
			prop.load(new InputStreamReader(in, Charset.forName("UTF-8")));
		} catch (Exception e) {
			logger.fatal("MSILUTILS.loadPropertyValues()==>>Exception:", e);
		}
		return prop;
	}
}