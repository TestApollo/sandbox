package playground;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ParsingCandidatesFromExcel {
    /*
    public void parseExcelTable(String orgId, String userId, int toRow) throws Exception {
        DataFormatter dataFormatter = new DataFormatter(); -- Через форматер получаем данные
        EmailValidator validator = EmailValidator.getInstance(); --Нужная штука. Зачем? Ну для мейла наверное

        File file = new File(Thread.currentThread().getContextClassLoader()
                .getResource("Sombra.xlsx").getPath()); -- Файл вносится таким способом, что не прописывать конкретный путь, а чтобы он сам назодился
                только по имени. Добавлено после того, как я внёс все файлы в структуру проекта


-- Форматтеры для дат, которые были как через слэш, так и через точку

        SimpleDateFormat slashFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        SimpleDateFormat dotFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");

--Воркбук используется в Apache POI для парсинга файлов. Через него, по сути, всё и делается
        Workbook workbook = WorkbookFactory.create(file);

--Создание листа Эксель
        Sheet sheet = workbook.getSheetAt(0);

--Карта для получения комментариев.Key - parentId который общий в таблице с кандидатами и в таблице с комментами к ним
Value - orgId который мы присваеваем каждому кандидату в своей системе. Чтобы потом можно было соотнести комменты к кандидатам
        Map<String, String> fromNotes = new HashMap<>();

--Идёт по каждой строке листа, начиная с первой (0 строка это заголовки). Счёт строк и колонок начинается с 0
--Используется не forEach, чтобы точно видеть количество пройденных кандидатов. До этого, кандидаты дублировались в систему
        for (int i = 0; i < toRow; i++) {

        --Блок try-catch нужен на каждом цикле, чтобы если вылазит исключение на одном кандидате, от этого нестопался весь метод.
        --Исключение просто перехватывается и всё
            try {
                Row row = sheet.getRow(i);
                if (row.getRowNum() != 0) {
--Каждый заход берётся новая строка, создаётся новый кандидат которому сетятся определённые параметры. Но не все.
--Некоторые сетятся после сохранения.

                    Candidate candidate = new Candidate();
--Каждое значение достаётся из соответствующей колонки в таблице row.getCell(?) - это номер колонки
                    String idFromFile = dataFormatter.formatCellValue(row.getCell(0));
                    String firstNameCell = dataFormatter.formatCellValue(row.getCell(2));
                    String lastNameCell = dataFormatter.formatCellValue(row.getCell(3));
                    String fullNameCell = dataFormatter.formatCellValue(row.getCell(4));
                    String experienceCell = dataFormatter.formatCellValue(row.getCell(16));
                    String currentEmployerCell = dataFormatter.formatCellValue(row.getCell(17));
                    String currentPositionCell = dataFormatter.formatCellValue(row.getCell(53));
                    String sourceCell = dataFormatter.formatCellValue(row.getCell(37));
                    String skillsCell = dataFormatter.formatCellValue(row.getCell(19));

                    //Set Salary
                    String fullSalary;
                    String input = dataFormatter.formatCellValue(row.getCell(60));

--Зарплата может быть в разных валютах. Смотрим в таблице, какие валюты и спользуются и проверяем при доставании значение

                    if (input.startsWith("USD") && input.contains(" ")) {
-- В fullSalary добавляется пойманное значение(по сути, это элемент массива[1]), и делится по пробелам. Не надо выстраивать сложные регулярки)
                        fullSalary = input.split(" ")[1];
-- В amount берётя только цифровое значение зарплаты (в ячейке содержится валюта и цифра. Мы отделяем одно от другого)
                        int amount = Integer.valueOf(fullSalary);
-- Проверяя с какой валюты начинается зарплата, забираем указанную цифровую сумму и вставляем из ЭНАМа существующую валюту
                        candidate.setSalary(amount); --сумма
                        candidate.setCurrency(CurrencyTypeEnum.USD); --валюта
                    } else if (input.startsWith("UAH") && input.contains(" ")) {
                        fullSalary = input.split(" ")[1];
                        int amount = Integer.valueOf(fullSalary);
                        candidate.setSalary(amount);
                        candidate.setCurrency(CurrencyTypeEnum.UAH);
                    } else if (input.startsWith("EUR") && input.contains(" ")) {
                        fullSalary = input.split(" ")[1];
                        int amount = Integer.valueOf(fullSalary);
                        candidate.setSalary(amount);
                        candidate.setCurrency(CurrencyTypeEnum.EUR);
                    } else if (!(input.isEmpty())) {
                        fullSalary = input.replaceAll("\\D+", "");
                        if (!(fullSalary.isEmpty())) {
                            int amount = Integer.valueOf(fullSalary);
                            candidate.setSalary(amount);
                            candidate.setCurrency(CurrencyTypeEnum.USD);
                        }
                    }

                    String dateOfBirthString = dataFormatter.formatCellValue(row.getCell(54));
                    String createdDateString = dataFormatter.formatCellValue(row.getCell(27));

                    Date dateOfBirth = null;
                    Date createdDate = new Date();

--Дата сетится коряво. Она может писаться через / или .
--Проверяем если она не пустая и представленна одним из вариков, добавляем в соответствующую переменную через dateFormat созданные в начале

                    try {
                        if (StringUtils.isNotBlank(dateOfBirthString) && dateOfBirthString.contains("/")) {
                            dateOfBirth = slashFormatter.parse(dateOfBirthString);
                        } else if (StringUtils.isNotBlank(dateOfBirthString) && dateOfBirthString.contains(".")) {
                            dateOfBirth = dotFormatter.parse(dateOfBirthString);
                        }
                    } catch (ParseException pe) {
                        pe.printStackTrace();
                    } finally {
                        candidate.setDb(dateOfBirth);
                    }

                    try {
                        if (StringUtils.isNotBlank(createdDateString) && createdDateString.contains("/")) {
                            createdDate = slashFormatter.parse(createdDateString);
                        } else if (StringUtils.isNotBlank(createdDateString) && createdDateString.contains(".")) {
                            createdDate = dotFormatter.parse(createdDateString);
                        }
                    } catch (ParseException pe) {
                        pe.printStackTrace();
                    } finally {
                        candidate.setDc(createdDate);
                    }

--В одном поле контакты должны быть собраны 5 полей таблицы

                    //Set Contacts
                    String phoneCell = dataFormatter.formatCellValue(row.getCell(8));
                    String websiteCell = dataFormatter.formatCellValue(row.getCell(9));
                    String skypeCell = dataFormatter.formatCellValue(row.getCell(23));
                    String emailCell = dataFormatter.formatCellValue(row.getCell(5));
                    String secondaryEmailCell = dataFormatter.formatCellValue(row.getCell(52));

--А добавляем эти значения, через создание объекта Contact, в который сохраняется тип контакта и значение
--добавляем этих Contact в список, который целиком сетим в кандидата

                    List<Contact> candidateContacts = new ArrayList<>();

                    if (StringUtils.isNotBlank(phoneCell)) {
                        Contact phone = new Contact(ContactTypeEnum.mphone.toString(), phoneCell);
                        candidateContacts.add(phone);
                    }

                    if (StringUtils.isNotBlank(websiteCell)) {
                        Contact website = new Contact(ContactTypeEnum.homepage.toString(), websiteCell);
                        candidateContacts.add(website);
                    }

                    if (StringUtils.isNotBlank(skypeCell)) {
                        Contact skype = new Contact(ContactTypeEnum.skype.toString(), skypeCell);
                        candidateContacts.add(skype);
                    }

                    if (StringUtils.isNotBlank(emailCell) && validator.isValid(emailCell)) {
                        Contact email1 = new Contact(ContactTypeEnum.email.toString(), emailCell);
                        candidateContacts.add(email1);
                    }

                    if (StringUtils.isNotBlank(secondaryEmailCell) && validator.isValid(secondaryEmailCell)) {
                        Contact secondaryEmail = new Contact(ContactTypeEnum.email.toString(), secondaryEmailCell);
                        candidateContacts.add(secondaryEmail);
                    }

                    if (CollectionUtils.isNotEmpty(candidateContacts))
                        candidate.setContacts(candidateContacts);


                    //Set Language
                    String languagesCell = dataFormatter.formatCellValue(row.getCell(57));

-- Для определения языка есть специальный метод languageService. Он считывает название язык и уровень

                    if (StringUtils.isNotBlank(languagesCell)) {
                      List<Language> languages = new ArrayList<>();
--Проходим по считанной строке разделяя по запятым
                        for (String l : languagesCell.split(",")) {
                        --Забираем название языка
                            String langName = languageService.getKeyForLanguage(l);
                        --уровень владения
                            String langLevel = languageService.getLevelForLanguage(l);

--Создаем объект Language и сетим в него значения, которые потом добавляем в список languages

                            Language la = new Language();
                            la.setName(langName);
                            la.setLevel(langLevel);
                            languages.add(la);
                        }
--Весь список сетим в кандидата
                        candidate.setLanguages(languages);
                    }
--Регион формируется из дву колонок
                    //Set Region
                    String city = dataFormatter.formatCellValue(row.getCell(12));
                    String country = dataFormatter.formatCellValue(row.getCell(15));

--Если колонка не пустая добавляем, если нет - ничего не добавляем

                    city = StringUtils.isNotBlank(city) ? city : "";
                    country = StringUtils.isNotBlank(country) ? country : "";

--Если значение не пустое, в регион через специальный метод, который принимает (город, страну, айди), добавляется значение
                    Region region;
                    if (StringUtils.isNotBlank(city)) {
                    --Города, если есть только город
                        region = regionService.getRegion(city, "", orgId);
                    } else {
                    --Страны если есть только страна
                        region = regionService.getRegion(country, "", orgId);
                    }
                    if (region != null) {
                        candidate.setRegion(region);
                        candidate.setRegionId(region.getRegionId());
                    }
--Описание добавляется из 4 таблиц
Значение каждой, добавляется в стрингбилдер отделяясь новой строчкой

Считалась строка, записалась и перевела каретку на новую строку
                    //Set Description
                    StringBuilder desc = new StringBuilder();

                    String historyOfEmploymentCell = dataFormatter.formatCellValue(row.getCell(58));
                    if (StringUtils.isNotBlank(historyOfEmploymentCell)) {
                        desc.append(historyOfEmploymentCell + "\n <br/><hr/>");
                    }

                    String educationCell = dataFormatter.formatCellValue(row.getCell(59));
                    if (StringUtils.isNotBlank(educationCell)) {
                        desc.append(educationCell + "\n <br/><hr/>");
                    }

                    String technologyStackCell = dataFormatter.formatCellValue(row.getCell(61));
                    if (StringUtils.isNotBlank(technologyStackCell)) {
                        desc.append(technologyStackCell + "\n <br/><hr/>");
                    }

                    String additionalDetailsCell = dataFormatter.formatCellValue(row.getCell(62));
                    if (StringUtils.isNotBlank(additionalDetailsCell)) {
                        desc.append(additionalDetailsCell + "\n");
                    }

                    candidate.setDescr(desc.toString());

--Сеттит всё что считали

                    candidate.setOrgId(orgId);
                    candidate.setFirstName(firstNameCell);
                    candidate.setLastName(lastNameCell);
                    candidate.setFullName(fullNameCell);
                    candidate.setExpirence(experienceCell);
                    candidate.setCurrentWorkPlace(currentEmployerCell);
                    candidate.setCurrentPosition(currentPositionCell);
                    candidate.setOrigin(sourceCell);
                    candidate.setCoreSkills(skillsCell);

--Сохраняем всё в кандидата                 userId и orgId передаются в общий метод. Это параметры аккаунта в который всё будет загружаться
                    candidate = saveCandidate(candidate, userId, orgId);

                    --Так же нужно добавлять всё в Solr
                    solrService.reIndexSolrNOW();

--Дальше добавляется всё, что нужно сетить после сохранения кандидата

--Мапа которая была созданна в начале, заполняется значениями
                    fromNotes.put(idFromFile, candidate.getCandidateId()); --Кандидат уже сохранён и данные можно доставать прям из кандидата

                    //Set Tags
                    String tagsCell = dataFormatter.formatCellValue(row.getCell(10));

                    if (StringUtils.isNotBlank(tagsCell)) {

                    -- Собирает тэги в массив. Делим по запятым, которыми они они разделены в файле

                        String[] tagsArray = tagsCell.split(",");

--Создаем список для всех тегов кандидиата
                        List<CandidateGroup> tags = new ArrayList<>();
                        for (String s : tagsArray) {
--Используем специальный метод для тегов, собирая их из массива. Добавляем в список
                            CandidateGroup tag = candidateGroupService.createCandidateGroup(s.trim(), userId, orgId);
                            tags.add(tag);
                        }
--Проверяем, если объект tag не пуст, проходимся итератором и добавляем в список
                        if (CollectionUtils.isNotEmpty(tags)) {
                            for (CandidateGroup t : tags) {
                                candidateGroupService.addEntryToGroup(t.getName(), candidate.getCandidateId(), userId, orgId);
                            }
                        }
                    }
                }
--Специальная проверка, чтобы отбивать оповещение при заполнении каждых 100 кандидатов.
--Нужна была для лучшего контроля. Мы не понимали, где падает ошибка
                if (row.getRowNum() % 100 == 0) {
                    System.out.println(row.getRowNum() + 1 + " candidates added");
                }
--Проверка отлавливает исключения вощникающие в процессе сохранения кандидат, чтобы из-за одного, не падали остальные и не прирывался процесс
            } catch (Exception e) {
//                System.out.println("ERROR AT ROW " + row.getRowNum());
                e.printStackTrace();
            }
        }
--Поток тормозится специально, чтобы кандидаты успевали сохраняться
        Thread.sleep(2000);

--Комментарии и доп.файлы добавляются после создания кандидата
        //addComments
        addCommentsToCandidates(fromNotes, userId, orgId);

        // addFiles
        addFilesToCandidates(fromNotes, userId, orgId);

    }
--Метод добавляения прикрепленных файлов
    private void addFilesToCandidates(Map<String, String> map, String userId, String orgId) throws Exception{
    --Для преобразования данных
        DataFormatter dataFormatter = new DataFormatter();

--Считывает файл по имени, сам его находя
        File file = new File(Thread.currentThread().getContextClassLoader()
                .getResource("Attachments_001_Sheet.xlsx").getPath());

        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        --Указывает директорию папки из которого будут загружаться файлы
        String attachDir = "Attachments_001";

--Используется тот же принцип с картой в которой сохранялся parentId и наш orgId кандидату, чтобы мы могли найти прикрепленные
--к нему файлы в другой папке

        for(Row row : sheet){
            try {
                if(row.getRowNum() != 0) { -- пропускаем первую строку

                    String parentId = dataFormatter.formatCellValue(row.getCell(9));
                    String fileName = dataFormatter.formatCellValue(row.getCell(7));

--Достаем файл который соответствуем parentId из папки с файлами
                    File attach = new File(Thread.currentThread().getContextClassLoader()
                            .getResource(attachDir + File.separator + fileName).getPath());

--Проверяем, если parentId кандидата совпадает с parentId файла
                    if (map.containsKey(parentId) && StringUtils.isNotBlank(fileName)) {
                        if (attach.exists()) {
--Считываем файл
                            FileInputStream fis = new FileInputStream(attach);

--Получаем айди кандидата которое совпадаем с файловым
                            String candidateId = map.get(parentId);

--Сетим файл соответствующему кандидату через специальный метод
                            MultipartFile multipartFile = new MockMultipartFile(attach.getName(), fis);
                            saveFileToCandidate(candidateId, multipartFile, orgId, userId);
                            System.out.println("Resume file " + attach.getPath() + " added to: " + candidateId);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR Adding files!!!");
                e.printStackTrace();
            }
        }

        Thread.sleep(5000);
    }


--Метод добавления комментариев
    public void addCommentsToCandidates(Map<String, String> map, String userId, String orgId) throws Exception {

--Принцип тот же. Находим коммент соответствующий по айди нашему кандидату и добавляему ему его

        DataFormatter dataFormatter = new DataFormatter();

        File file = new File(Thread.currentThread().getContextClassLoader()
                .getResource("Sombra_Notes.xlsx").getPath());

        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            try {
                if (row.getRowNum() != 0) {
                    String parentId = dataFormatter.formatCellValue(row.getCell(4));
                    String noteComment = dataFormatter.formatCellValue(row.getCell(3));

                    if (map.containsKey(parentId) && StringUtils.isNotBlank(noteComment)) {
                        setMessageCandidate(map.get(parentId), userId, noteComment, orgId);
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR Adding comments!!!");
                e.printStackTrace();
            }
        }

    }
--Специальный метод, необходим для добавления комментариев кандидату

    public void saveFileToCandidate(final String candidateId, final MultipartFile file, final String orgId,
                                    final String userId) throws IOException {
        FileLink fileLink = fileService.createFileLinkOrReturnExists(file, ObjectEnum.candidate, candidateId, userId, orgId);
        solrService.save(fileLink.getFileId(), orgId, ObjectEnum.candidate_attachment, candidateId, fileService.getFileContents(file));
    }
*/
}
