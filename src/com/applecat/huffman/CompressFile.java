package com.applecat.huffman;

import java.io.*;

public class CompressFile {

    HuffmanCode code;


    public CompressFile(String filename) throws IOException {
        File file = new File(filename);
        FileInputStream fis = new FileInputStream(file);
        byte[] fileB = new byte[fis.available()];
        fis.read(fileB);
        this.code = new HuffmanCode(fileB);
    }

    public void fileUnzip(String filename) throws IOException{
        FileInputStream inputStream = new FileInputStream(filename);
        byte[] fileB = new byte[inputStream.available()];
        int read = inputStream.read(fileB);
        byte[] unzip = code.unzip(fileB);
        FileOutputStream fos = new FileOutputStream("D:\\picture\\wallpaper\\2.jpg");
        fos.write(unzip);
    }

    public void fileZip(String filename) throws IOException {



        FileInputStream inputStream = new FileInputStream(filename);
        byte[] fileB = new byte[inputStream.available()];
        int read = inputStream.read(fileB);
        byte[] zip = code.zip(fileB);
        FileOutputStream fos = new FileOutputStream(filename + ".hfm");
        fos.write(zip);
    }

    public static void main(String[] args) {
        String file = "D:\\picture\\wallpaper\\1.jpg";
        try {
            CompressFile compressFile = new CompressFile(file);
            compressFile.fileZip(file);
            compressFile.fileUnzip(file + ".hfm");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
