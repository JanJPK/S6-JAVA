package imagebrowser.plugin;

import javafx.scene.image.Image;

import java.lang.reflect.Method;

public class ExtendedClassLoader extends ClassLoader
{

    //<editor-fold desc="constructor">

    public ExtendedClassLoader()
    {
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
