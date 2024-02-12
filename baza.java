// Plik ten zawiera ogólny schemat bazy danych
import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.io.*;
public class baza{
    static GPU[] baza=new GPU[50];
    public static String nazwaOtwartegoPliku ="baza123.dat";
    final static int rozmiar=50;
    public static void main(String[] args) throws
            java.io.IOException,java.lang.ClassNotFoundException,InterruptedException
    {
  
        while(true)
            menu();
    }

    static void drukujMenu(){
        System.out.println(" MENU");
        System.out.println("============================");
        System.out.println(" 1.Otworz baze danych");
        System.out.println(" 2.Utworz nowa baze");
        System.out.println(" 3 Usun baze");
        System.out.println(" 4.Zakoncz program");
        System.out.println("============================");
    }//drukuj menu
    static void menu() throws IOException, ClassNotFoundException, InterruptedException {
        while(true){
            drukujMenu();
            char ch;
            int opcja=0;
            String input;
            boolean w=false;
            Scanner sc=new Scanner(System.in);
            do {
                w=false;
                System.out.print("Wybierz opcje : ");
                input=sc.next();
                ch = input.charAt(0);
                opcja=ch-'0';
                if (opcja<1||opcja>6){
                    w=true;
                    System.out.println("Niepoprawna opcja");
                }
                input="";
            } while (w);
            System.out.printf("\n Opcja=%d",opcja);
            System.in.read();
            switch (opcja){
                case 1:// opcja otworz bazę
                    System.out.print("Wybrano opcje 1\n");
                    otworzBaze();
                    break;
                case 2:
                    System.out.print("Wybrano opcje 2\n");
                    utworzBaze();
                    break;
                case 3:
                    System.out.print("Wybrano opcje 4\n");
                    usunBaze();
                    break;
                case 4:
                    System.exit(0);
                    break;

            }// switch
        } //while
    }//menu
    static void otworzBaze()throws java.io.IOException
    {
        String nazwaBazy=new String();
        Scanner sc=new Scanner(System.in);
        System.out.print(" Podaj nazwe bazy:");
        boolean poprawna=false;
        boolean w1=true;
        do{
            System.out.println("Podaj nazwe bazy w formacie bazaXX.dat, gdzie XX cyfry z [0,9]:");
            nazwaBazy=sc.next();
            poprawna = false;
            w1=true;
            poprawna=sprawdzPoprawnoscNazwyBazy(nazwaBazy);
            File file = new File(nazwaBazy);
            w1=!file.exists();
            if(w1)
                System.out.println("Nazwa bazy niepoprawna lub plik istnieje");
            System.in.read();
        }while(w1);
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(nazwaBazy));
            baza =(GPU[]) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        przegladBazy();
        zapiszBaze(nazwaBazy);
    }
    static void utworzBaze()throws java.io.IOException
    {
        Scanner sc=new Scanner(System.in);
        String nazwaBazy=new String();
        boolean poprawna=false;
        boolean w1=true;
        do{
            System.out.println("Podaj nazwe bazy w formacie bazaXX.dat, gdzie XX cyfry z [0,9]:");
            nazwaBazy=sc.next();
            poprawna = false;
            w1=true;
            poprawna=sprawdzPoprawnoscNazwyBazy(nazwaBazy);
            File file = new File(nazwaBazy);
            w1=file.exists()||file.isDirectory() || !poprawna;
            if(w1)
                System.out.println("Nazwa bazy niepoprawna lub plik istnieje");
            else {
                try {
                    if (file.createNewFile()) {
                        System.out.println("Plik bazy danych został utworzony.");
                    } else {
                        System.out.println("Plik bazy danych istnieje już w systemie.");
                    }
                } catch (IOException e) {
                    System.out.println("Wystąpił błąd podczas tworzenia pliku bazy danych: " + e.getMessage());
                }
            }
            System.in.read();
        }while(w1);
        System.out.println("Podaj liczbe rekordow: ");
        int ile= sc.nextInt();
        for (int i=0;i<ile;i++){
            GPU x=new GPU();
            x.wczytajDane();
            baza[i]=x;
        }
        System.out.println("wczytano dane");
        przegladBazy();
        zapiszBaze(nazwaBazy);
//clss();
    }
    static void przegladBazy()throws java.io.IOException
    {
        boolean przeglad=true;
        int id=0;
        while(przeglad) {
            System.out.println("Metoda przegladBazy");
            baza[id].drukujDane();
            char ch;
            List<Character> lista = Arrays.asList('y', 'g', 'h', 'b', 'd', 'u', 'm', 's', 'e');
            char opcja = '0';
            String input;
            boolean w = false;
            int s=0;
            for(GPU x:baza)if(x!=null)s++;
            Scanner sc = new Scanner(System.in);
            do {
                w = false;
                System.out.print("Wybierz opcje : ");
                System.out.println("Y-up B-down G-Home H-End D-dopisz M-modyfikuj U-usun S-sortuj  E-wyjdz");
                input = sc.next();
                ch = input.charAt(0);
                opcja = ch;
                if (!lista.contains(opcja)) {
                    w=true;
                    System.out.println("Niepoprawna opcja");
                }
            } while (w);
            switch (opcja) {
                case 'y':
                    if(id<s-1)id++;
                    break;
                case 'g':
                    id=0;
                    break;
                case 'h':
                    id=s-1;
                    break;
                case 'b':
                    if(id>0)id--;
                    break;
                case 'd':
                    GPU nowy=new GPU();
                    nowy.wczytajDane();
                    baza[s]=nowy;
                    break;
                case 'u':
                    if(id!=s-1) {
                        baza[id] = baza[s - 1];
                        baza[s - 1] = null;
                    }
                    else{
                        id--;
                        baza[s - 1] = null;
                    }
                    break;
                case 'm':
                    GPU mod=new GPU();
                    mod.wczytajDane();
                    baza[id]=mod;
                    break;
                case 's':
                    sortowanie();
                    break;
                case 'e':
                    przeglad=false;
                    break;
            }
            System.in.read();
        }
    }
    static void sortowanie()throws java.io.IOException
    {
        System.out.println("Sortowanie");
        System.out.println("Wybierz pole wg którego ma być posortowane:");
        System.out.println("1.Producent");
        System.out.println("2.Model");
        System.out.println("3.Cena");
        Scanner sc=new Scanner(System.in);
        int w=7;
        do {
            if(sc.hasNextInt())w=sc.nextInt();
            else System.out.println("Podaj liczbe w przedziale 1-3");
        }while(w<1||w>3);
        w--;
        GPU.wyborSort=w;
        // Usunięcie obiektów null z tablicy baza
        List<GPU> nonNullObjects = new ArrayList<>();
        for (GPU gpu : baza) {
            if (gpu != null) {
                nonNullObjects.add(gpu);
            }
        }
        GPU[] nonNullArray = nonNullObjects.toArray(new GPU[0]);
        // Sortowanie tablicy bez obiektów null
        Arrays.sort(nonNullArray);
        // Ustawienie posortowanej tablicy bez obiektów null z powrotem do tablicy baza
        for (int i = 0; i < nonNullArray.length; i++) {
            baza[i] = nonNullArray[i];
        }

        System.in.read();
    }
    static void usunBaze()throws java.io.IOException
    {
        System.out.println("Metoda usunBaze");
        System.out.println("Podaj nazwe bazy");
        String usun;
        Scanner sc=new Scanner(System.in) ;
        boolean poprawna=false;
        boolean w1=true;
        do{
            System.out.println("Podaj nazwe bazy w formacie bazaXX.dat, gdzie XX cyfry z [0,9]:");
            usun=sc.next();
            poprawna = false;
            w1=true;
            poprawna=sprawdzPoprawnoscNazwyBazy(usun);
            System.in.read();
        }while(poprawna);
        File plik = new File(usun);
        if (plik.exists()) {
            if (plik.delete()) {
                System.out.println("Plik został usunięty.");
            } else {
                System.out.println("Nie można usunąć pliku.");
            }
        } else {
            System.out.println("Plik o podanej nazwie nie istnieje.");
        }
        System.in.read();
    }
    static void zapiszBaze(String nazwaOtwartegoPliku)throws java.io.IOException
    {
        //System.out.println("Metoda zapiszBaze");
        try {
            FileOutputStream fileOut = new FileOutputStream(nazwaOtwartegoPliku);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(baza);
            objectOut.close();
            fileOut.close();
            System.out.println("Plik został nadpisany.");

        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas nadpisywania pliku: " + e.getMessage());
        }
        for (int i=0;i<baza.length;i++ ){
            baza[i]=null;
        }
        System.in.read();
    }

    static boolean sprawdzPoprawnoscNazwyBazy(String nazwaBazy)
    {
        boolean spr = false;
        boolean spr1=false;
        if( nazwaBazy.length()==10)
        {
            spr1=true;
            String strb="baza";
            String strroz=".dat";
// bazaXX.dat // xx cyfry dziesiętne
//bazaXX.dat
            String str1=nazwaBazy.substring(0,4);
            System.out.println("str1="+str1);

            String str2= nazwaBazy.substring(4,6);
            System.out.println("str2="+str2);
            String str3=nazwaBazy.substring(6,10);
            System.out.println("str3="+str3);
            int wynik21=0,wynik22=0;
            int wynik1=str1.compareTo(strb);
            int wynik3=str3.compareTo(strroz);
            char ch1=str2.charAt(0);
            char ch2=str2.charAt(1);
            if(Character.isDigit(ch1)) wynik21=1;
            if( Character.isDigit(ch2)) wynik22=1;
            int wynik=wynik1+wynik3+wynik21+wynik22;
            if(wynik==2) spr=true;
        }

        if (spr==false||spr1==false) System.out.println("Niepoprawny format");
        else System.out.println("Poprawny format");
        return spr;
    }
}
class GPU implements Serializable,Comparable<GPU> {
    private String producent;
    private String model;
    private Double cena;
    static int wyborSort=0;
    GPU(){};
    GPU(String producent, String model, double cena)
    {
        this.cena=cena;
        this.model=model;
        this.producent=producent;
    }
    void wczytajDane()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Podaj producenta");
        producent = sc.next();
        System.out.println("Podaj model");
        model = sc.next();
        System.out.println("Podaj cenę:");

        while (!sc.hasNextDouble()) {
            System.out.println("Podaj ponownie cenę");
            sc.next(); // wyczyść błąd
        }
        cena = sc.nextDouble();
    }

    void drukujDane(){
        System.out.println("Producent:"+producent);
        System.out.println("model:"+model);
        System.out.println("cena:"+cena);
    }
    public String pobierzProducent() {
        return producent;
    }
    public String pobierzModel() {
        return model;
    }
    public double pobierzCena() {
        return cena;
    }

    @Override
    public int compareTo(GPU o){
        int p=0;
        if (wyborSort==0)
            p=this.producent.compareTo(o.pobierzProducent());
        if (wyborSort==1)
            p=this.model.compareTo(o.pobierzModel());
        if (wyborSort==2)
            p=this.cena.compareTo(o.pobierzCena());
        return p;
    }
}