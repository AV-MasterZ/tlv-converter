Запуск:
1. Собрать проект: mvn package
2. Запустить: java -jar <Путь до jar-файла>\tlv-converter-jar-with-dependencies.jar <Путь до файла-источника> <Путь до файла-приемника>
Пример:
java -jar D:\jp\tlv-converter\target\tlv-converter-jar-with-dependencies.jar D:\jp\tlv-converter\target\data-1.bin D:\jp\tlv-converter\result.json
Если файл-источник лежит в одном каталоге с приложением, можно указать только имя файла:
java -jar tlv-converter-jar-with-dependencies.jar data-1.bin result.json