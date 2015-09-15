package zhy2002.aspectjexamples.infrastructure;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Load classes from a directory.
 */
public class DirClassLoader extends ClassLoader {

    private static final Logger logger = Logger.getLogger(DirClassLoader.class.getName());

    private final String dir;

    public DirClassLoader(String dir) {
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        synchronized (getClassLoadingLock(name)){
            Class clazz = this.findLoadedClass(name); // 父类已加载
            if (clazz == null) {  //检查该类是否已被加载过
                byte[] classData = getClassData(name);  //根据类的二进制名称,获得该class文件的字节码数组
                if (classData == null) {
                    logger.warning(String.format("DirClassLoader cannot load class '%s' from '%s'", name, dir));
                    throw new ClassNotFoundException();
                }
                clazz = defineClass(name, classData, 0, classData.length);  //将class的字节码数组转换成Class类的实例
            }
            return clazz;
        }
    }

    /**
     * Get the class file from the directory.
     * @param name class full name.
     * @return class file bytes.
     */
    protected byte[] getClassData(String name){

        String classFilePath =  String.join(File.separator, name.split("\\."));
        Path path = Paths.get(dir, classFilePath + ".class");
        try {
            return Files.readAllBytes(path);
        }catch (IOException ex){
            logger.log(Level.WARNING, String.format("Cannot locate file:%s", path), ex);
            return null;
        }
    }
}
