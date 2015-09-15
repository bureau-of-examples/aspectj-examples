package zhy2002.aspectjexamples.infrastructure;

/**
 * Mock class loader.
 */
public class ResolvingClassLoader extends DirClassLoader {

    public ResolvingClassLoader(String dir) {
        super(dir);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

        synchronized (getClassLoadingLock(name)) {

            if (name.startsWith("java.lang")) {
                return getSystemClassLoader().loadClass(name);//this loads Object class but not PrintStream class, thus resolving this class will cause ClassNotFoundException
            }

            byte[] classData = getClassData(name);
            if (classData == null) {
                throw new ClassNotFoundException();
            }
            Class<?> clazz = defineClass(name, classData, 0, classData.length); //all classes referenced by this .class will be loaded through this class loader instance as well.
            resolveClass(clazz);
            return clazz;

        }

    }

}
