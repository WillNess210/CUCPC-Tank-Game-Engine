clear
javac $(find -name "*.java" -not -path "./bots/*")
java Main bots/SampleJavaBot bots/SampleJavaBot
#java Main bots/SampleJavaBot bots/SamplePythonBot
#java Main bots/SampleJavaBot bots/SampleCPPBot
find -name "*.class" -not -path "./bots/*" -delete
