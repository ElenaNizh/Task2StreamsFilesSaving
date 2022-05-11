import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        String dirPathSave = "/Users/dns/Games/savegames/";
        String zipNameFile = "zip.zip";
        String zipPathFile = dirPathSave + zipNameFile;
        List<String> filesSaves = new ArrayList<>();

        //Создайте три экземпляра класса GameProgress.
        List<GameProgress> gameProgress = Arrays.asList(
                new GameProgress(85, 4, 13, 17.4),
                new GameProgress(60, 2, 20, 70.2),
                new GameProgress(30, 1, 11, 12.3)
        );

        savingGameProgressObjects(gameProgress, dirPathSave, filesSaves);
        zipFiles(zipPathFile, filesSaves);
        filesSavesDeleting(dirPathSave);
    }

    //Реализуйте метод saveGame(), принимающий в качестве аргументов полный путь к файлу типа String
   // (например, "/Users/admin/Games/GunRunner/savegames/save3.dat") и объект класса GameProgress.
  ////  Для записи Вам потребуются такие классы как FileOutputStream и ObjectOutputStream.
   // У последнего есть метод writeObject(),     подходящий для записи сериализованного объекта .
  //  о избежание утечек памяти, не забудьте либо использовать try с ресурсами,
  //  либо вручную закрыть файловые стримы (это касается всех случаев работы с файловыми потоками).

    public static void savingGameProgressObjects(List<GameProgress> gameProgress, String dirPathSave, List<String> filesSaves) {
        for (int i = 0; i < gameProgress.size(); i++) {
            String fileNameSave = "save" + i + ".dat";
            String filePathSave = dirPathSave + fileNameSave;
            saveGame(filePathSave, gameProgress.get(i));
            filesSaves.add(filePathSave);
            System.out.println("Создаем файл сохранений \"" + filePathSave + "\"");
        }
    }

    public static void saveGame(String filePathSave, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePathSave);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage(
            ));
        }
    }
    //Далее реализуйте метод zipFiles(), принимающий в качестве аргументов String полный путь к файлу архива
  //  (например, "/Users/admin/Games/GunRunner/savegames/zip.zip") и список запаковываемых объектов в виде
   // списка строчек String полного пути к файлу (например, "/Users/admin/Games/GunRunner/savegames/save3.dat").
    //В методе Вам потребуется реализовать блок try-catch с объектами ZipOutputStream и FileOutputStream.
   // Внутри него пробегитесь по списку файлов и для каждого организуйте запись в блоке try-catch через FileInputStream.
   // Для этого создайте экземпляр ZipEntry и уведомьте ZipOutputStream о новом элементе архива с помощью метода putNextEntry().
   // Далее необходимо считать упаковываемый файл с помощью метода read() и записать его с помощью метода write().
    //После чего уведомьте ZipOutputStream о записи файла в архив с помощью метода closeEntry().

    public static void zipFiles(String zipPathFile, List<String> filesSaves) {
        try (FileOutputStream fos = new FileOutputStream(zipPathFile);
             ZipOutputStream zout = new ZipOutputStream(fos)) {
            for (String fileSave : filesSaves) {
                File fileZip = new File(fileSave);
                try (FileInputStream fis = new FileInputStream(fileZip)) {
                    ZipEntry zipEntry = new ZipEntry(fileZip.getName());
                    zout.putNextEntry(zipEntry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                    System.out.println("Файл \"" + fileSave + "\" добавлен в архив " + "\"" + zipPathFile + "\"");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void filesSavesDeleting(String dirPathSave) {
        Arrays.stream(new File(dirPathSave).listFiles())
                .filter(x -> !x.getName().contains("zip.zip"))
                .forEach(File::delete);
        System.out.println("Файлы сохранений не лежащие в архиве удалены из " + "\"" + dirPathSave + "\"");
    }

}