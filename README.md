# USAGE 
adicionar 
```
<additionalClasspathElements>
    <additionalClasspathElement>${project.basedir}/lib/LDAUtils-1.0.1.jar</additionalClasspathElement>
</additionalClasspathElements>
```
como mostrado

```
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.1.0</version>
    <configuration>
        <mainClass>control.GUIManager</mainClass>
        <classpathScope>compile</classpathScope>
        <includeProjectDependencies>true</includeProjectDependencies>
        <includePluginDependencies>true</includePluginDependencies>
        <additionalClasspathElements>
            <additionalClasspathElement>${project.basedir}/lib/LDAUtils-1.0.1.jar</additionalClasspathElement>
        </additionalClasspathElements>
    </configuration>
</plugin>
```
