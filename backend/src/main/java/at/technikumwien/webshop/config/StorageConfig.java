package at.technikumwien.webshop.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class StorageConfig {
   
     
@Value("${C:\\Users\\a899081\\OneDrive - Atos\\Desktop\\Webshop\\frontend\\Image}")
  private String location ;

    
     

public String getLocation() {
    return location;}

    public Path getLocationPath() {
        return Path.of(location).toAbsolutePath().normalize();
    }

    public String getLocationPathPattern() {
        return "/" + location + "/**";
    }

    public String getResourceLocation() {
        return "file:/" + getLocationPath() + "/";
    }
}