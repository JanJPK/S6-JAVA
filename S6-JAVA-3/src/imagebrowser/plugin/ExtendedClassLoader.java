package imagebrowser.plugin;

import javafx.scene.image.Image;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ExtendedClassLoader extends ClassLoader
{

    //<editor-fold desc="variables">

    private List<Class> loadedClasses;

    //</editor-fold>

    //<editor-fold desc="constructor">

    public ExtendedClassLoader()
    {
        loadedClasses = new ArrayList<>();
    }

    //</editor-fold>

    //<editor-fold desc="get/set">


    //</editor-fold>

    //<editor-fold desc="methods">

    public Method getMethod(Class loadedClass, String methodName)
    {
        try
        {
            return loadedClass.getMethod(methodName, Image.class);
        } catch (NoSuchMethodException ex)
        {
            return null;
        }

    }

    public Class getClass(String className)
    {
        try
        {
            return loadClass(className);
        } catch (ClassNotFoundException ex)
        {
            return null;
        }
    }

    private Class searchLoadedClasses(String name)
    {
        for (Class loadedClass : loadedClasses)
        {
            if (loadedClass.getName().equals(name))
            {
                return loadedClass;
            }
        }
        return null;
    }

    public Image invokeImage(Class loadedClass, String methodName, Image input)
    {
        try
        {
            Method method = loadedClass.getMethod(methodName, Image.class);
            return (Image) method.invoke(loadedClass.newInstance(), input);
        } catch (Exception ex)
        {
            return null;
        }

    }

    //</editor-fold>
}
