echo "UMapper shell script"
java -version
echo "if Java version is lower than JRE 1.5, please upgrade"
pause
java -jar UMapper.jar -c commands.run -verbose
pause
