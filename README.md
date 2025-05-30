## JAR file compile

1. В папке src прописать команду по компиляции файлов классов Java
   - "javac -d ../bin App.java PostalAddress.java"
   [ -d ../bin ] - создаст папку bin и поместит туда скомпилированные файлы классов
2. Создание JAR файла по классу с точкой входа это App
   - "jar cfe ../app.jar App *.class"
3. Запуск JAR файла (он заранее скомпилирован верхние пункты не требуются)
   - "java -jar app.jar"
