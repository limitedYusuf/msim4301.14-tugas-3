import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

abstract class MenuItem {
   protected String nama;
   protected double harga;
   protected String kategori;

   public MenuItem(String nama, double harga, String kategori) {
      this.nama = nama;
      this.harga = harga;
      this.kategori = kategori;
   }

   public abstract void tampilMenu();
}

class Makanan extends MenuItem {
   private String jenisMakanan;

   public Makanan(String nama, double harga, String jenisMakanan) {
      super(nama, harga, "Makanan");
      this.jenisMakanan = jenisMakanan;
   }

   @Override
   public void tampilMenu() {
      System.out.println("Nama: " + nama);
      System.out.println("Harga: " + harga);
      System.out.println("Kategori: " + kategori);
      System.out.println("Jenis Makanan: " + jenisMakanan);
      System.out.println("----------------------");
   }
}

class Minuman extends MenuItem {
   private String jenisMinuman;

   public Minuman(String nama, double harga, String jenisMinuman) {
      super(nama, harga, "Minuman");
      this.jenisMinuman = jenisMinuman;
   }

   @Override
   public void tampilMenu() {
      System.out.println("Nama: " + nama);
      System.out.println("Harga: " + harga);
      System.out.println("Kategori: " + kategori);
      System.out.println("Jenis Minuman: " + jenisMinuman);
      System.out.println("----------------------");
   }
}

class Diskon extends MenuItem {
   private double diskon;

   public Diskon(String nama, double harga, double diskon) {
      super(nama, harga, "Diskon");
      this.diskon = diskon;
   }

   @Override
   public void tampilMenu() {
      System.out.println("Nama: " + nama);
      System.out.println("Harga: " + harga);
      System.out.println("Kategori: " + kategori);
      System.out.println("Diskon: " + diskon + "%");
      System.out.println("----------------------");
   }
}

class Menu {
   private ArrayList<MenuItem> daftarMenu = new ArrayList<>();

   public void tambahMenu(MenuItem item) {
      daftarMenu.add(item);
   }

   public void tampilMenuRestoran() {
      System.out.println("===== Menu Restoran =====");
      for (MenuItem item : daftarMenu) {
         item.tampilMenu();
      }
   }

   public MenuItem cariMenu(String nama) {
      for (MenuItem item : daftarMenu) {
         if (item.nama.equals(nama)) {
            return item;
         }
      }
      return null;
   }
}

class Pesanan {
   private ArrayList<MenuItem> pesananPelanggan = new ArrayList<>();

   public void tambahPesanan(MenuItem item) {
      pesananPelanggan.add(item);
   }

   public double hitungTotalBiaya() {
      double totalBiaya = 0;
      for (MenuItem item : pesananPelanggan) {
         totalBiaya += item.harga;
      }
      return totalBiaya;
   }

   public void tampilStrukPesanan() {
      System.out.println("===== Struk Pesanan =====");
      for (MenuItem item : pesananPelanggan) {
         item.tampilMenu();
      }
      System.out.println("Total Biaya: " + hitungTotalBiaya());
   }
}

public class Main {
   public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      Menu menuRestoran = new Menu();
      Pesanan pesananPelanggan = new Pesanan();

      menuRestoran.tambahMenu(new Makanan("Nasi Goreng", 25000, "Mie Goreng"));
      menuRestoran.tambahMenu(new Minuman("Es Teh", 5000, "Teh Manis"));
      menuRestoran.tambahMenu(new Diskon("Promo Hari Ini", 0, 10));

      int pilihan;
      do {
         System.out.println("===== Menu Utama =====");
         System.out.println("1. Tambah Item Menu");
         System.out.println("2. Tampilkan Menu Restoran");
         System.out.println("3. Pesan Menu");
         System.out.println("4. Tampilkan Struk Pesanan");
         System.out.println("5. Keluar");

         try {
            System.out.print("Pilih menu (1-5): ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
               case 1:
                  tambahItemMenu(scanner, menuRestoran);
                  break;
               case 2:
                  menuRestoran.tampilMenuRestoran();
                  break;
               case 3:
                  pesanMenu(scanner, menuRestoran, pesananPelanggan);
                  break;
               case 4:
                  pesananPelanggan.tampilStrukPesanan();
                  break;
               case 5:
                  System.out.println("Terima kasih!");
                  break;
               default:
                  System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
         } catch (InputMismatchException e) {
            System.out.println("Masukkan angka yang valid.");
            scanner.nextLine();
            pilihan = 0;
         }
      } while (pilihan != 5);
   }

   private static void tambahItemMenu(Scanner scanner, Menu menuRestoran) {
      System.out.print("Masukkan nama item: ");
      String nama = scanner.nextLine();
      System.out.print("Masukkan harga item: ");
      double harga = scanner.nextDouble();
      scanner.nextLine();

      System.out.println("Pilih kategori item:");
      System.out.println("1. Makanan");
      System.out.println("2. Minuman");
      System.out.println("3. Diskon");
      System.out.print("Pilih (1-3): ");
      int kategori = scanner.nextInt();
      scanner.nextLine();

      switch (kategori) {
         case 1:
            System.out.print("Masukkan jenis makanan: ");
            String jenisMakanan = scanner.nextLine();
            menuRestoran.tambahMenu(new Makanan(nama, harga, jenisMakanan));
            System.out.println("Makanan berhasil ditambahkan!");
            break;
         case 2:
            System.out.print("Masukkan jenis minuman: ");
            String jenisMinuman = scanner.nextLine();
            menuRestoran.tambahMenu(new Minuman(nama, harga, jenisMinuman));
            System.out.println("Minuman berhasil ditambahkan!");
            break;
         case 3:
            System.out.print("Masukkan diskon (%): ");
            double diskon = scanner.nextDouble();
            menuRestoran.tambahMenu(new Diskon(nama, harga, diskon));
            System.out.println("Diskon berhasil ditambahkan!");
            break;
         default:
            System.out.println("Pilihan tidak valid. Item tidak ditambahkan.");
      }
   }

   private static void pesanMenu(Scanner scanner, Menu menuRestoran, Pesanan pesananPelanggan) {
      System.out.print("Masukkan nama item yang akan dipesan: ");
      String nama = scanner.nextLine();
      MenuItem item = menuRestoran.cariMenu(nama);
      if (item != null) {
         pesananPelanggan.tambahPesanan(item);
         System.out.println("Pesanan berhasil ditambahkan!");
      } else {
         System.out.println("Item tidak ditemukan dalam menu.");
      }
   }
}
