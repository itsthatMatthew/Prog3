import java.io.*;
import java.util.Scanner;

public class Main {
    protected static File wd;
    public static void main(String args[]) {
        // Aktuális wd beállítása
        wd = new File(System.getProperty("user.dir"));
        // Scanner létrehozása sorok beolvasására
        Scanner sc = new Scanner(System.in);
        while (true) {
            // Egy sor beolvasása, majd annak tagolása szóközök szerint
            String cmd[] = sc.nextLine().split(" ");

            // Parancs kiválasztása
            if (cmd[0].equals("exit")) {
                sc.close();
                exit(cmd);
            } else if (cmd[0].equals("pwd")) {
                pwd(cmd);
            } else if (cmd[0].equals("ls")) {
                ls(cmd);
            } else if (cmd[0].equals("cd")) {
                cd(cmd);
            } else if (cmd[0].equals("mkdir")) {
                mkdir(cmd);
            } else if (cmd[0].equals("cp")) {
                cp(cmd);
            } else if (cmd[0].equals("head")) {
                head(cmd);
            } else {
                System.err.println("command not recognised.");
            }
        }
    }

    // Hatására a program leáll
    protected static void exit(String[] cmd) { System.exit(0); }

    // Hatásárí kiírja az aktuális mappa útvonalát
    protected static void pwd(String[] cmd) {
        try {
            System.out.println(wd.getCanonicalPath());
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    // Kilistázza a mappában található fájlokat
    protected static void ls(String[] cmd) {
        boolean list = false;
        if (cmd.length > 1) list = cmd[1].equals("-l");

        File sub[] = wd.listFiles();

        for (File f : sub) {
            System.out.print(f.getName());
            if (list) {
                System.out.print("\t" + f.length() + " bytes\t");
                if (f.isFile()) System.out.print("f");
                else if (f.isDirectory()) System.out.print("d");
            }
            System.out.print("\n");
        }
    }

    // Átlép a megadott almappába
    protected static void cd(String[] cmd) {
        if (cmd.length == 1) {
            System.err.println("cd error: dir not specified.");
            return;
        }
        if (cmd[1].equals("..")) {
            wd = wd.getParentFile();
        } else {
            File to = new File(wd + "\\" + cmd[1]);
            if (!to.exists()) {
                System.err.println("cd error: the specified file does not exist.");
                return;
            } else if (!to.isDirectory()) {
                System.err.println("cd error: the specified file is not a dir.");
                return;
            } else {
                wd = to;
            }
        }
    }

    // Létrehoz egy új almappát
    protected static void mkdir(String[] cmd) {
        if (cmd.length == 1) {
            System.err.println("mkdor error: dir not specified.");
            return;
        }
        File make = new File(wd + "\\" + cmd[1]);
        if (make.exists()) {
            System.err.println("mkdir error: the specified dir already exists.");
            return;
        } else {
            make.mkdirs();
        }
    }

    // Átmásol egy file-t egy másikba
    protected static void cp(String[] cmd) {
        if (cmd.length < 3) {
            System.err.println("cp error: not enough parameters.");
            return;
        }
        File ffrom = new File(wd + "\\" + cmd[1]);
        File fto = new File(wd + "\\" + cmd[2]);
        try {
            FileInputStream is = new FileInputStream(ffrom);
            FileOutputStream os = new FileOutputStream(fto);
            int b = is.read();
            while (b != -1) {
                os.write(b);
                b = is.read();
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    // Kiírja az átadott file első n sorát
    protected static void head(String[] cmd) {
        if (cmd.length == 1) {
            System.err.println("head error: too few arguments.");
            return;
        }
        int n = 10;
        File ffrom;
        if (cmd[1].equals("-n")) {
            if (cmd.length < 4) {
                System.err.println("head error: too few arguments.");
                return;
            }
            n = Integer.parseInt(cmd[2]);
            ffrom = new File(wd + "\\" + cmd[3]);
        } else {
            ffrom = new File(wd + "\\" + cmd[1]);
        }
        try {
            BufferedReader fread = new BufferedReader(new FileReader(ffrom));
            for (int i = 0; i != n; i++) {
                System.out.println(fread.readLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
            return;
        } catch (IOException e) {
            System.err.println(e);
            return;
        }
    }
}
