![java version](https://img.shields.io/badge/java-11-brightgreen)
# Chuck_Norris_Cipher_Encoder
Little project with console menu

### Example
![Example](Example.png)


### Build source
```shell
javac --source-path src/ -d bin src/chucknorris/Main.java
```

### Run
```shell
java --class-path ./bin chucknorris.Main
```

### Create jar
```shell
javac --source-path src/ -d bin src/chucknorris/Main.java
jar cefv chucknorris.Main  chucknorrisCipher.jar -C ./bin .
```

### Run jar
```shell
java -jar ./chucknorrisCipher.jar
```