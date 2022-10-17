import javax.print.attribute.standard.Compression;
import java.io.*;
import java.util.*;

public class Main {
    static ArrayList<Beer> list = new ArrayList<Beer>();
    static Scanner sc = new Scanner(System.in);
    static HashMap<String, Command> commands = new HashMap<String, Command>();
    static HashMap<String, Comparator<Beer>> comps = new HashMap<String, Comparator<Beer>>();
    static {
        comps.put("name", Comparator.comparing(b -> b.getName()));
        comps.put("style", Comparator.comparing(b -> b.getStyle()));
        comps.put("strength", Comparator.comparingDouble(b -> b.getStrength()));
    }
    public static void main(String[] args) {
        commands.put("add", Main::add);
        commands.put("list", Main::list);
        commands.put("save", Main::save);
        commands.put("load", Main::load);
        commands.put("search", Main::search);
        commands.put("find", Main::find);
        commands.put("delete", Main::delete);
        commands.put("exit", (cmd) -> System.exit(0) );


        String[] cmd;
        while (true) {
            cmd = sc.nextLine().split(" ");
            if (commands.get(cmd[0]) == null)
                System.out.println("Unknown command...");
            else
                commands.get(cmd[0]).execute(cmd);
        }
    }
    /**
     * A listához ad egy új elemet.
     */
    protected static void add(String[] cmd) {
        list.add(new Beer(cmd[1], cmd[2], Double.parseDouble(cmd[3])));
    }
    static List<String> lparams = new LinkedList<String>(comps.keySet());
    /**
     * Kilistázza a listában levő elemeket.
     */
    protected static void list(String[] cmd) {
        if (cmd.length > 1) {
            lparams.remove(cmd[1]);
            lparams.add(0, cmd[1]);
        }
        Comparator<Beer> cmp = comps.get("name");
        ArrayList<Beer> ordered = new ArrayList<>(list);
        Comparator<Beer> comparator = comps.get(lparams.get(0)).thenComparing(comps.get(lparams.get(1))).thenComparing(comps.get(lparams.get(2)));
        Collections.sort(ordered, comparator);
        for (Beer b : ordered) {
            System.out.println(b.toString());
        }
    }
    /**
     * Az argumentumként megadott névvel lementi a listát.
     * @param cmd utasítás tömb, [1] elem határozza meg a helyet.
     */
    protected static void save(String[] cmd) {
        try {
            FileOutputStream fos = new FileOutputStream(cmd[1]);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
            fos.close();
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
    /**
     * Az argumentumként megadott névvel beolvassa a listát.
     * @param cmd utasítás tömb, [1] elem határozza meg a helyet.
     */
    protected static void load(String[] cmd) {
        try {
            FileInputStream fis = new FileInputStream(cmd[1]);
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (ArrayList<Beer>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.toString());
        }
    }
    /**
     * Kikeresi az argumentummal megyező nevű elemeket.
     */
    protected static void search(String[] cmd) {
        for (Beer b : list) {
            if ("name".equals(cmd[1])) {
                if (cmd[2].equals(b.getName()))
                    System.out.println(b.toString());
            } else if ("style".equals(cmd[1])) {
                if (cmd[2].equals(b.getStyle()))
                    System.out.println(b.toString());
            } else if ("strength".equals(cmd[1])) {
                if (b.getStrength() == Double.parseDouble(cmd[2]))
                    System.out.println(b.toString());
            }
        }
    }
    /**
     * Kikeresi az argumentumot tartalmazó nevű elemeket.
     */
    protected static void find(String[] cmd) {
        for (Beer b : list) {
            if ("name".equals(cmd[1])) {
                if (b.getName().contains(cmd[2]))
                    System.out.println(b.toString());
            } else if ("style".equals(cmd[1])) {
                if (b.getStyle().contains(cmd[2]))
                    System.out.println(b.toString());
            } else if ("strength".equals(cmd[1])) {
                if (b.getStrength() >= Double.parseDouble(cmd[2]))
                    System.out.println(b.toString());
            } else if ("weaker".equals(cmd[1])) {
                if (b.getStrength() <= Double.parseDouble(cmd[2]))
                    System.out.println(b.toString());
            }
        }
    }
    /**
     * Törli az argumentummal megegyező nevű elemeket.
     */
    protected static void delete(String[] cmd) {
        Collections.sort(list, (b1, b2) -> b1.getName().compareTo(b2.getName()));
        int idx = Collections.binarySearch(list, new Beer(cmd[1], null, 0), (b1, b2) -> b1.getName().compareTo(b2.getName()));
        list.remove(idx);
    }
}