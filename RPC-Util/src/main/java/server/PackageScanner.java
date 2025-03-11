package server;

import io.github.classgraph.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class PackageScanner {
    private ServiceMap serviceMap;

    public PackageScanner(){
         this.serviceMap = new ServiceMap();
    }

    public ServiceMap scanPackageWithClassGraph(String packageName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        ScanResult scan = new ClassGraph().enableClassInfo().enableMethodInfo().enableFieldInfo().scan();
        for (ClassInfo eachClass : scan.getAllClasses()) {
            System.out.println(eachClass.getName()+ " injecting to Service list!");
            if (eachClass.getName().startsWith(packageName) && !eachClass.isInterface()){
                System.out.println(eachClass.getSimpleName()+ "inject Successfully!");
                Class<?> aClass = Class.forName(eachClass.getName());
                if (aClass.getDeclaredConstructor().getModifiers() == Modifier.PUBLIC) {
                    Object object = aClass.getDeclaredConstructor().newInstance();
                    serviceMap.setInterfaceMap(object);
                }
            }
        }
        return serviceMap;
    }
}
