package teamassignment2;

import java.util.Scanner;

//NAMA ANGGOTA
//GILANG SURYA PRATAMA - 2902784896
//FAIZ AZHAR RISTYA NUGRAHA - 2902799903
//ADITYA SECHAN SAPUTRA - 2902795211
//ROBBY FAHSYA - 2902765505

//============================================================
//Class Lagu: merepresentasikan satu objek lagu dalam playlist
//Atribut: judul, artis, durasi
//============================================================
class Lagu {
 String judul;
 String artis;
 double durasi; // dalam menit

 // Constructor untuk membuat objek Lagu baru
 public Lagu(String judul, String artis, double durasi) {
     this.judul  = judul;
     this.artis  = artis;
     this.durasi = durasi;
 }

 // Method untuk menampilkan informasi lagu secara terformat
 public void tampilkanInfo() {
     System.out.printf("  %-25s - %-20s (%.2f menit)%n", judul, artis, durasi);
 }
}

//============================================================
//Class PlaylistArray: mengelola kumpulan lagu menggunakan array
//Kapasitas maksimum: 10 lagu
//============================================================
class PlaylistArray {

 // Array statis dengan ukuran maksimum 10
 private Lagu[] playlist = new Lagu[10];

 // Variabel penghitung jumlah lagu yang ada saat ini
 private int jumlahLagu = 0;

 // ----------------------------------------------------------
 // OPERASI 1: TRAVERSAL — Menampilkan semua lagu
 // Kompleksitas Waktu : O(n)
 //   → Program harus mengunjungi setiap elemen array satu per
 //     satu dari indeks 0 hingga jumlahLagu-1, sehingga waktu
 //     eksekusi bertumbuh linier seiring jumlah lagu.
 // ----------------------------------------------------------
 public void tampilkanSemuaLagu() {
     System.out.println("\n=== DAFTAR SEMUA LAGU ===");

     // Periksa apakah playlist kosong
     if (jumlahLagu == 0) {
         System.out.println("  Playlist masih kosong.");
         return;
     }

     // Traversal: iterasi dari indeks 0 hingga akhir data
     for (int i = 0; i < jumlahLagu; i++) {
         System.out.printf("  %d. ", i + 1);
         playlist[i].tampilkanInfo();
     }
     System.out.println("  Total: " + jumlahLagu + " lagu");
 }

 // ----------------------------------------------------------
 // OPERASI 2: INSERTION — Menambahkan lagu baru ke playlist
 // Kompleksitas Waktu : O(1)
 //   → Lagu baru selalu disisipkan di posisi akhir array
 //     (indeks jumlahLagu), sehingga tidak ada pergeseran
 //     elemen yang diperlukan. Waktu eksekusi konstan.
 // ----------------------------------------------------------
 public void tambahLagu(Scanner scanner) {
     System.out.println("\n=== TAMBAH LAGU BARU ===");

     // Periksa apakah array sudah penuh (maksimum 10 lagu)
     if (jumlahLagu == playlist.length) {
         System.out.println("  Playlist sudah penuh! Maksimum " + playlist.length + " lagu.");
         return;
     }

     // Input data lagu dari pengguna
     System.out.print("  Masukkan judul lagu  : ");
     String judul = scanner.nextLine().trim();

     System.out.print("  Masukkan artis       : ");
     String artis = scanner.nextLine().trim();

     double durasi = 0;
     while (true) {
         try {
             System.out.print("  Masukkan durasi (menit): ");
             durasi = Double.parseDouble(scanner.nextLine().trim());
             if (durasi <= 0) throw new NumberFormatException();
             break;
         } catch (NumberFormatException e) {
             System.out.println("  [!] Durasi tidak valid. Masukkan angka positif.");
         }
     }

     // Sisipkan objek Lagu baru di posisi akhir array — O(1)
     playlist[jumlahLagu] = new Lagu(judul, artis, durasi);
     jumlahLagu++;

     System.out.println("  Lagu \"" + judul + "\" berhasil ditambahkan ke playlist!");

     // Tampilkan daftar terkini setelah penambahan
     tampilkanSemuaLagu();
 }

 // ----------------------------------------------------------
 // OPERASI 3: DELETION — Menghapus lagu berdasarkan judul
 // Kompleksitas Waktu : O(n)
 //   → Dalam kasus terburuk, program harus mencari hingga
 //     elemen terakhir (linear search = O(n)), kemudian
 //     menggeser semua elemen di belakang posisi yang dihapus
 //     satu langkah ke kiri (shift = O(n)). Total tetap O(n).
 // ----------------------------------------------------------
 public void hapusLagu(Scanner scanner) {
     System.out.println("\n=== HAPUS LAGU ===");

     if (jumlahLagu == 0) {
         System.out.println("  Playlist masih kosong, tidak ada yang bisa dihapus.");
         return;
     }

     System.out.print("  Masukkan judul lagu yang ingin dihapus: ");
     String cari = scanner.nextLine().trim();

     // Linear search untuk menemukan indeks lagu yang akan dihapus
     int indeksDitemukan = -1;
     for (int i = 0; i < jumlahLagu; i++) {
         if (playlist[i].judul.equalsIgnoreCase(cari)) {
             indeksDitemukan = i;
             break; // Hentikan pencarian setelah ditemukan
         }
     }

     // Jika lagu tidak ditemukan
     if (indeksDitemukan == -1) {
         System.out.println("  Lagu \"" + cari + "\" tidak ditemukan dalam playlist.");
         return;
     }

     String judulDihapus = playlist[indeksDitemukan].judul;

     // Geser semua elemen di belakang indeks yang dihapus ke kiri
     // agar array tetap rapat (tidak ada celah/gap)
     for (int i = indeksDitemukan; i < jumlahLagu - 1; i++) {
         playlist[i] = playlist[i + 1];
     }

     // Kosongkan slot terakhir dan kurangi penghitung
     playlist[jumlahLagu - 1] = null;
     jumlahLagu--;

     System.out.println("  Lagu \"" + judulDihapus + "\" berhasil dihapus dari playlist.");

     // Tampilkan daftar terkini setelah penghapusan
     tampilkanSemuaLagu();
 }

 // ----------------------------------------------------------
 // OPERASI 4: SEARCHING — Mencari lagu berdasarkan judul
 // Algoritma  : Linear Search
 // Kompleksitas Waktu : O(n)
 //   → Program memeriksa setiap elemen dari awal hingga
 //     ditemukan atau hingga akhir array. Dalam kasus terburuk
 //     (lagu tidak ada atau ada di posisi terakhir), seluruh
 //     array ditelusuri → waktu bertumbuh linier terhadap n.
 // ----------------------------------------------------------
 public void cariLagu(Scanner scanner) {
     System.out.println("\n=== CARI LAGU ===");

     if (jumlahLagu == 0) {
         System.out.println("  Playlist masih kosong.");
         return;
     }

     System.out.print("  Masukkan judul lagu yang dicari: ");
     String cari = scanner.nextLine().trim();

     // Linear search: periksa setiap elemen satu per satu
     boolean ditemukan = false;
     for (int i = 0; i < jumlahLagu; i++) {
         if (playlist[i].judul.equalsIgnoreCase(cari)) {
             System.out.println("  Lagu ditemukan di posisi " + (i + 1) + ":");
             System.out.print("  ");
             playlist[i].tampilkanInfo();
             ditemukan = true;
             break;
         }
     }

     if (!ditemukan) {
         System.out.println("  Lagu \"" + cari + "\" tidak ditemukan dalam playlist.");
     }
 }

 // ----------------------------------------------------------
 // FITUR TAMBAHAN: SORTING — Mengurutkan lagu berdasarkan durasi
 // Algoritma  : Bubble Sort (ascending)
 // Kompleksitas Waktu : O(n²)
 //   → Bubble Sort menggunakan dua loop bersarang: loop luar
 //     berjalan n-1 kali, dan loop dalam berjalan hingga n-1
 //     kali di setiap iterasi luar. Total perbandingan maksimum
 //     adalah n*(n-1)/2 ≈ O(n²). Ini membuat Bubble Sort
 //     kurang efisien untuk dataset besar, namun mudah
 //     diimplementasikan dan cukup untuk playlist kecil (≤10).
 // ----------------------------------------------------------
 public void urutkanLaguBerdasarkanDurasi() {
     System.out.println("\n=== URUTKAN LAGU BERDASARKAN DURASI (ASCENDING) ===");

     if (jumlahLagu == 0) {
         System.out.println("  Playlist masih kosong.");
         return;
     }

     // Tampilkan daftar SEBELUM pengurutan
     System.out.println("  -- Sebelum Diurutkan --");
     for (int i = 0; i < jumlahLagu; i++) {
         System.out.printf("  %d. ", i + 1);
         playlist[i].tampilkanInfo();
     }

     // Bubble Sort: bandingkan pasangan elemen yang berdampingan
     // dan tukar jika elemen kiri lebih besar dari elemen kanan
     for (int i = 0; i < jumlahLagu - 1; i++) {
         for (int j = 0; j < jumlahLagu - 1 - i; j++) {
             // Bandingkan durasi dua lagu yang berdampingan
             if (playlist[j].durasi > playlist[j + 1].durasi) {
                 // Tukar posisi keduanya (swap)
                 Lagu temp      = playlist[j];
                 playlist[j]    = playlist[j + 1];
                 playlist[j + 1] = temp;
             }
         }
     }

     // Tampilkan daftar SESUDAH pengurutan
     System.out.println("\n  -- Sesudah Diurutkan (Ascending) --");
     for (int i = 0; i < jumlahLagu; i++) {
         System.out.printf("  %d. ", i + 1);
         playlist[i].tampilkanInfo();
     }
 }
}

//============================================================
//Class utama: entry point program dengan menu interaktif
//============================================================
public class Main {

 public static void main(String[] args) {
     Scanner scanner     = new Scanner(System.in);
     PlaylistArray mgr   = new PlaylistArray();
     int pilihan;

     System.out.println("╔══════════════════════════════════════╗");
     System.out.println("║   SISTEM MANAJEMEN PLAYLIST MUSIK    ║");
     System.out.println("╚══════════════════════════════════════╝");

     // Loop utama menu — terus berjalan hingga pengguna memilih "Keluar"
     do {
         // Tampilkan menu pilihan
         System.out.println("\n=== MENU PLAYLIST MUSIK ===");
         System.out.println("  1. Tampilkan semua lagu");
         System.out.println("  2. Tambah lagu baru");
         System.out.println("  3. Hapus lagu berdasarkan judul");
         System.out.println("  4. Cari lagu berdasarkan judul");
         System.out.println("  5. Urutkan berdasarkan durasi");
         System.out.println("  6. Keluar");
         System.out.print("  Pilih menu [1-6]: ");

         // Validasi input menu
         try {
             pilihan = Integer.parseInt(scanner.nextLine().trim());
         } catch (NumberFormatException e) {
             pilihan = -1; // Input tidak valid → tampilkan pesan error
         }

         // Eksekusi operasi sesuai pilihan pengguna
         switch (pilihan) {
             case 1:
                 mgr.tampilkanSemuaLagu();
                 break;
             case 2:
                 mgr.tambahLagu(scanner);
                 break;
             case 3:
                 mgr.hapusLagu(scanner);
                 break;
             case 4:
                 mgr.cariLagu(scanner);
                 break;
             case 5:
                 mgr.urutkanLaguBerdasarkanDurasi();
                 break;
             case 6:
                 System.out.println("\n  Terima kasih! Program selesai.");
                 break;
             default:
                 System.out.println("  [!] Pilihan tidak valid. Masukkan angka 1-6.");
         }

     } while (pilihan != 6); // Keluar dari loop jika pilihan = 6

     scanner.close();
 }
}