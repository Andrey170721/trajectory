package trajectory;

import java.io.File;

public class Source {
    public final String path;

    public Source(String fullPath){
        File file = new File(fullPath);
        this.fileName = file.getName();
        this.path = file.getParent();

    }

    public String fileName;
}
