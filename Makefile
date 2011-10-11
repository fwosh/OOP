Translator: 
	javac -sourcepath . -classpath \
	xtc/xtc.jar:xtc/rats-runtime.jar Translator.java
run: Translator
	java -classpath .:xtc/xtc.jar:xtc/rats-runtime.jar Translator -outputAll Translator.java