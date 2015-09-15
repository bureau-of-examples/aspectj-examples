package zhy2002.aspectjexamples.infrastructure;

/**
 * Mock class loader.
 */
public class AllowDuplicateClassLoader extends DirClassLoader {

    public AllowDuplicateClassLoader(String dir){
        super(dir);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

        synchronized (getClassLoadingLock(name)){
            try{
                return Class.forName(name, true, null);//cannot define a class in java.lang package due to Java security restriction.

            }catch (ClassNotFoundException ex){
                byte[] classData = getClassData(name);
                if (classData == null) {
                    throw new ClassNotFoundException();
                }
                return defineClass(name, classData, 0, classData.length); //all classes referenced by this .class will be loaded through this class loader instance as well.
            }
        }

    }

}
