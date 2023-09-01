package trajectory;

import java.io.File;

public class Source {
    private final String path;
    private String fileName;

    public Source(String fullPath){
        File file = new File(fullPath);
        this.fileName = file.getName();
        this.path = file.getParent();

    }

    public String getPath(){
        return path;
    }
    public String getFileName(){
        return fileName;
    }
    public String getSource(){return path + '/' + fileName;}

}
