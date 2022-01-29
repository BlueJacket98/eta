# MySQL Configuration
## VS Code SQLTools Configuration
In terminal
```
mysqlsh
\sql
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'YOUR_PASSWORD';
```

# Bugs
If loading .pmml model shows 
Exception in thread "main" java.lang.IllegalArgumentException: http://www.dmg.org/PMML-4_4
Change header of .pmml file to 
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<PMML xmlns="http://www.dmg.org/PMML-4_3" xmlns:data="http://jpmml.org/jpmml-model/InlineTable" version="4.3">
```