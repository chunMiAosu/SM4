package com.example.sm4_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MainActivity extends AppCompatActivity {

    File writeFile = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        writeFile = new File("/sdcard/aaaaasm4/timeData_1.csv");

        Button button = (Button) findViewById(R.id.buttonSubmit);

        button.setOnClickListener(new View.OnClickListener(){

            long time_iterativeOperation = 0;
            long time_reverseOrderTrans = 0;
            long startTime;
            long endTime;
            long all_startTime;
            long all_endTime;
            int size = 0;
            @Override
            public void onClick(View v) {
                TextView fileSize = (TextView) findViewById(R.id.editTextFileSize);
                String str = fileSize.getText().toString();
                size = Integer.parseInt(str);
                Button submit = (Button) findViewById(R.id.buttonSubmit);
                TextView view_output = (TextView) findViewById(R.id.textViewOutput);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        time_iterativeOperation = 0;
                        time_reverseOrderTrans = 0;

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                submit.setClickable(false);
                                submit.setEnabled(false);
                            }
                        });

                        //生成加密密钥
                        byte[] key = {(byte) 0x01, (byte) 0x23, (byte) 0x45, (byte) 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD,
                                (byte) 0xEF, (byte) 0xFE, (byte) 0xDC, (byte) 0xBA, (byte) 0x98, (byte) 0x76, (byte) 0x54,
                                (byte) 0x32, (byte) 0x10};


                        //文件加密

                        long fileLength = size * 1024 * 1024;

                        File plainTextFile = new File("/sdcard/aaaaasm4/plainText.txt");
                        if(!plainTextFile.exists())
                        {
                            try {
                                plainTextFile.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        //生成明文文件
                        RandomAccessFile file = null;
                        try {

                            file = new RandomAccessFile(plainTextFile,"rw");
                            file.setLength(fileLength);


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        finally {
                            try {
                                file.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        all_startTime = System.currentTimeMillis();
                        //获取轮密钥rk[32],每个轮密钥长度为32bit
                        int[] rk = new int[32];
                        //获取轮密钥
                        getRK(key, rk);

                        //加密过程
                        int len = 0;
                        FileInputStream in = null;
                        try {
                            in = new FileInputStream(plainTextFile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        File ciphertextFile = new File("/sdcard/aaaaasm4/cipherText.txt");
                        if(!ciphertextFile.exists())
                        {
                            try {
                                ciphertextFile.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            ciphertextFile.delete();
                            try {
                                ciphertextFile.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        FileOutputStream out = null;
                        try {
                            out = new FileOutputStream(ciphertextFile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        byte[] plaintext = new byte[1280];//1280 = 16*16*5
                        byte[] ciphertext = new byte[1280]; //1280 = 16*16*5

                        while (true)
                        {
                            try {
                                if (!((len = in.read(plaintext)) != -1)) break;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (len % 16 != 0)
                            {
                                //数据填充
                                for (int i = len; i < len + 16 - (len % 16); i++)
                                {
                                    plaintext[i] = plaintext[len - 1];
                                }
                                len = len + 16 - (len % 16);
                            }
                            for (int i = 0; i < len; i += 16)
                            {
                                byte[] tmp_in = new byte[16];
                                byte[] tmp_out = new byte[16];
                                for (int j = 0; j < 16; j++)
                                {
                                    tmp_in[j] = plaintext[i + j];
                                }
                                sm4(tmp_in, tmp_out, rk, 1);
                                for (int j = 0; j < 16; j++)
                                {
                                    ciphertext[i + j] = tmp_out[j];
                                }
                            }
                            try {
                                out.write(ciphertext, 0, len);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            out.close();
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        all_endTime = System.currentTimeMillis();

                        try {
                            BufferedWriter writerText =  new BufferedWriter(new FileWriter(writeFile,true));
                            writerText.newLine();
                            writerText.write(size + "," + time_iterativeOperation + "," + time_reverseOrderTrans +"," +(all_endTime - all_startTime) );
                            writerText.flush();
                            writerText.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view_output.append("\n文件大小:" + String.valueOf(size) + "M");
                                view_output.append("\n32次迭代运算所用时间:" + time_iterativeOperation + "ms");
                                view_output.append("\n反序变换所用时间:" + time_reverseOrderTrans + "ms");
                                view_output.append("\n总时间:" + String.valueOf(all_endTime - all_startTime) + "ms");
                                submit.setClickable(true);
                                submit.setEnabled(true);
                            }
                        });
                    }
                });
                thread.start();
            }

            /**
             * sm4加解密实现
             *
             * @param plaintext  明文128bit
             * @param ciphertext 密文128bit
             * @param rk         轮密钥32个，每个128bit
             * @param enOrDe     1为加密，0为解密
             */
            private void sm4(byte[] plaintext, byte[] ciphertext, int[] rk, int enOrDe)
            {
                //把明文（加密）或密文（解密）分为4部分，每部分4byte，即32bit
                int[] x = new int[4];
                if (enOrDe == 1)
                {
                    for (int i = 0; i < 4; i++)
                    {
                        x[i] = (plaintext[0 + 4 * i] << 24) & 0xFF000000 | (plaintext[1 + 4 * i] << 16) & 0xFF0000 | (plaintext[2 + 4 * i] << 8) & 0xFF00 | plaintext[3 + 4 * i] & 0xFF;
                    }
                }
                else
                {
                    for (int i = 0; i < 4; i++)
                    {
                        x[i] = (ciphertext[0 + 4 * i] << 24) & 0xFF000000 | (ciphertext[1 + 4 * i] << 16) & 0xFF0000 | (ciphertext[2 + 4 * i] << 8) & 0xFF00 | ciphertext[3 + 4 * i] & 0xFF;
                    }
                }


                //32次迭代运算，加密时，轮密钥的使用顺序为0,1，...，31；解密时，轮密钥的使用顺序为31,30，...，1,0
                int tmp;
                int tmp1;
                startTime = System.currentTimeMillis();
                for (int i = 0; i < 32; i+=4)
                {
                    if (enOrDe == 1)
                    {
                        tmp = x[1] ^ x[2] ^ x[3] ^ rk[i];
                        tmp = nonlinearTrans(tmp);
                        tmp = tmp ^ loopOffsetLeft(tmp, 2) ^ loopOffsetLeft(tmp, 10) ^ loopOffsetLeft(tmp, 18) ^ loopOffsetLeft(tmp, 24);
                        tmp1 = x[2] ^ x[3] ^ rk[1 + i];
                        x[0] = x[0] ^ tmp;

                        tmp = tmp1 ^ x[0];

                        tmp = nonlinearTrans(tmp);
                        tmp = tmp ^ loopOffsetLeft(tmp, 2) ^ loopOffsetLeft(tmp, 10) ^ loopOffsetLeft(tmp, 18) ^ loopOffsetLeft(tmp, 24);
                        tmp1 = x[3] ^ x[0] ^ rk[2 + i];
                        x[1] = x[1] ^ tmp;

                        tmp = tmp1 ^ x[1];

                        tmp = nonlinearTrans(tmp);
                        tmp = tmp ^ loopOffsetLeft(tmp, 2) ^ loopOffsetLeft(tmp, 10) ^ loopOffsetLeft(tmp, 18) ^ loopOffsetLeft(tmp, 24);
                        tmp1 = x[0] ^ x[1] ^ rk[3 + i];
                        x[2] = x[2] ^ tmp;

                        tmp = tmp1 ^ x[2];
                        tmp = nonlinearTrans(tmp);
                        tmp = tmp ^ loopOffsetLeft(tmp, 2) ^ loopOffsetLeft(tmp, 10) ^ loopOffsetLeft(tmp, 18) ^ loopOffsetLeft(tmp, 24);
                        x[3] = x[3] ^ tmp;
                    }
                    else
                    {
                        tmp = x[1] ^ x[2] ^ x[3] ^ rk[31-i];

                        tmp = nonlinearTrans(tmp);
                        tmp = tmp ^ loopOffsetLeft(tmp, 2) ^ loopOffsetLeft(tmp, 10) ^ loopOffsetLeft(tmp, 18) ^ loopOffsetLeft(tmp, 24);
                        tmp1 = x[2] ^ x[3] ^ rk[31-(1 + i)]; //
                        x[0] = x[0] ^ tmp;  //这三句的顺序可以更好的利用资源，流水线

                        tmp = tmp1 ^ x[0];

                        tmp = nonlinearTrans(tmp);
                        tmp = tmp ^ loopOffsetLeft(tmp, 2) ^ loopOffsetLeft(tmp, 10) ^ loopOffsetLeft(tmp, 18) ^ loopOffsetLeft(tmp, 24);
                        tmp1 = x[3] ^ x[0] ^ rk[31-(2 + i)];
                        x[1] = x[1] ^ tmp;

                        tmp = tmp1 ^ x[1];

                        tmp = nonlinearTrans(tmp);
                        tmp = tmp ^ loopOffsetLeft(tmp, 2) ^ loopOffsetLeft(tmp, 10) ^ loopOffsetLeft(tmp, 18) ^ loopOffsetLeft(tmp, 24);
                        tmp1 = x[0] ^ x[1] ^ rk[31-(3 + i)];
                        x[2] = x[2] ^ tmp;

                        tmp = tmp1 ^ x[2];
                        tmp = nonlinearTrans(tmp);
                        tmp = tmp ^ loopOffsetLeft(tmp, 2) ^ loopOffsetLeft(tmp, 10) ^ loopOffsetLeft(tmp, 18) ^ loopOffsetLeft(tmp, 24);
                        x[3] = x[3] ^ tmp;
                    }

                }

                endTime = System.currentTimeMillis();
                time_iterativeOperation += endTime - startTime;

                // 反序变换
                startTime = System.currentTimeMillis();
                if (enOrDe == 1)
                {
                    //加密
                    for (int i = 3; i >= 0; i--)
                    {
                        ciphertext[0 + (3 - i) * 4] = (byte) ((x[i] >> 24) & 0xFF);
                        ciphertext[1 + (3 - i) * 4] = (byte) ((x[i] >> 16) & 0xFF);
                        ciphertext[2 + (3 - i) * 4] = (byte) ((x[i] >> 8) & 0xFF);
                        ciphertext[3 + (3 - i) * 4] = (byte) (x[i] & 0xFF);
                    }
                }
                else
                {
                    //解密
                    for (int i = 3; i >= 0; i--)
                    {
                        plaintext[0 + (3 - i) * 4] = (byte) ((x[i] >> 24) & 0xFF);
                        plaintext[1 + (3 - i) * 4] = (byte) ((x[i] >> 16) & 0xFF);
                        plaintext[2 + (3 - i) * 4] = (byte) ((x[i] >> 8) & 0xFF);
                        plaintext[3 + (3 - i) * 4] = (byte) (x[i] & 0xFF);
                    }
                }
                endTime = System.currentTimeMillis();
                time_reverseOrderTrans += endTime - startTime;

            }


            /**
             * 获得轮密钥rk，密钥扩展算法
             *
             * @param key 加密密钥 byte[16] 16*8 = 128bit
             * @param rk  存放计算得到的rk值 rk[32]
             */
            private void getRK(byte[] key, int[] rk)
            {

                //将密钥分成4个部分，每个部分4byte，即32bit
                int[] k = new int[4];
                for (int i = 0; i < 4; i++)
                {
                    k[i] = (key[0 + 4 * i] << 24) & 0xFF000000 | (key[1 + 4 * i] << 16) & 0xFF0000 | (key[2 + 4 * i] << 8) & 0xFF00 | key[3 + 4 * i] & 0xFF;
                }

                //与系统参数进行异或运算
                k[0] = k[0] ^ 0xA3B1BAC6;
                k[1] = k[1] ^ 0x56AA3350;
                k[2] = k[2] ^ 0x677D9197;
                k[3] = k[3] ^ 0xB27022DC;

                //计算得到轮密钥
                for (int i = 0; i < 32; i += 4)
                {
                    int tmp;
                    tmp = k[1] ^ k[2] ^ k[3] ^ getCK(i);
                    tmp = nonlinearTrans(tmp);
                    tmp = tmp ^ loopOffsetLeft(tmp, 13) ^ loopOffsetLeft(tmp, 23);
                    rk[i] = k[0] ^ tmp;

                    k[0] = rk[i];

                    tmp = k[2] ^ k[3] ^ k[0] ^ getCK(i + 1);
                    tmp = nonlinearTrans(tmp);
                    tmp = tmp ^ loopOffsetLeft(tmp, 13) ^ loopOffsetLeft(tmp, 23);
                    rk[i + 1] = k[1] ^ tmp;

                    k[1] = rk[i + 1];

                    tmp = k[3] ^ k[0] ^ k[1] ^ getCK(i + 2);
                    tmp = nonlinearTrans(tmp);
                    tmp = tmp ^ loopOffsetLeft(tmp, 13) ^ loopOffsetLeft(tmp, 23);
                    rk[i + 2] = k[2] ^ tmp;

                    k[2] = rk[i + 2];

                    tmp = k[0] ^ k[1] ^ k[2] ^ getCK(i + 3);
                    tmp = nonlinearTrans(tmp);
                    tmp = tmp ^ loopOffsetLeft(tmp, 13) ^ loopOffsetLeft(tmp, 23);
                    rk[i + 3] = k[3] ^ tmp;

                    k[3] = rk[i + 3];
                }

            }

            /**
             * 循环左移
             *
             * @param data   待移位数据
             * @param offset 移位位数
             * @return 循环左移后的数据
             */
            private int loopOffsetLeft(int data, int offset)
            {
                int tmp = 0x80000000;
                int tmp1 = 1;
                for (int i = 1; i < offset; i++)
                {
                    tmp1 = (tmp1 << 1) + 1;
                }
                tmp = tmp >> (offset - 1);
                tmp = tmp & data;
                tmp = (tmp >> (32 - offset)) & tmp1;
                data = data << offset;
                data = data | tmp;
                return data;
            }


            /**
             * sbox盒子
             *
             * @param a 输入sBox盒子的数
             * @return 从sBox盒子查找得到的值
             */
            private byte sBox(byte a)
            {
                byte[][] sbox = {{(byte) 0xD6, (byte) 0x90, (byte) 0xE9, (byte) 0xFE, (byte) 0xCC, (byte) 0xE1, (byte) 0x3D,
                        (byte) 0xB7, (byte) 0x16, (byte) 0xB6, (byte) 0x14, (byte) 0xC2, (byte) 0x28, (byte) 0xFB,
                        (byte) 0x2C, (byte) 0x05}, {(byte) 0x2B, (byte) 0x67, (byte) 0x9A, (byte) 0x76, (byte) 0x2A,
                        (byte) 0xBE, (byte) 0x04, (byte) 0xC3, (byte) 0xAA, (byte) 0x44, (byte) 0x13, (byte) 0x26,
                        (byte) 0x49, (byte) 0x86, (byte) 0x06, (byte) 0x99}, {(byte) 0x9C, (byte) 0x42, (byte) 0x50,
                        (byte) 0xF4, (byte) 0x91, (byte) 0xEF, (byte) 0x98, (byte) 0x7A, (byte) 0x33, (byte) 0x54,
                        (byte) 0x0B, (byte) 0x43, (byte) 0xED, (byte) 0xCF, (byte) 0xAC, (byte) 0x62}, {(byte) 0xE4,
                        (byte) 0xB3, (byte) 0x1C, (byte) 0xA9, (byte) 0xC9, (byte) 0x08, (byte) 0xE8, (byte) 0x95,
                        (byte) 0x80, (byte) 0xDF, (byte) 0x94, (byte) 0xFA, (byte) 0x75, (byte) 0x8F, (byte) 0x3F,
                        (byte) 0xA6}, {(byte) 0x47, (byte) 0x07, (byte) 0xA7, (byte) 0xFC, (byte) 0xF3, (byte) 0x73,
                        (byte) 0x17, (byte) 0xBA, (byte) 0x83, (byte) 0x59, (byte) 0x3C, (byte) 0x19, (byte) 0xE6,
                        (byte) 0x85, (byte) 0x4F, (byte) 0xA8}, {(byte) 0x68, (byte) 0x6B, (byte) 0x81, (byte) 0xB2,
                        (byte) 0x71, (byte) 0x64, (byte) 0xDA, (byte) 0x8B, (byte) 0xF8, (byte) 0xEB, (byte) 0x0F,
                        (byte) 0x4B, (byte) 0x70, (byte) 0x56, (byte) 0x9D, (byte) 0x35}, {(byte) 0x1E, (byte) 0x24,
                        (byte) 0x0E, (byte) 0x5E, (byte) 0x63, (byte) 0x58, (byte) 0xD1, (byte) 0xA2, (byte) 0x25,
                        (byte) 0x22, (byte) 0x7C, (byte) 0x3B, (byte) 0x01, (byte) 0x21, (byte) 0x78, (byte) 0x87},
                        {(byte) 0xD4, (byte) 0x00, (byte) 0x46, (byte) 0x57, (byte) 0x9F, (byte) 0xD3, (byte) 0x27,
                                (byte) 0x52, (byte) 0x4C, (byte) 0x36, (byte) 0x02, (byte) 0xE7, (byte) 0xA0, (byte) 0xC4,
                                (byte) 0xC8, (byte) 0x9E}, {(byte) 0xEA, (byte) 0xBF, (byte) 0x8A, (byte) 0xD2, (byte) 0x40,
                        (byte) 0xC7, (byte) 0x38, (byte) 0xB5, (byte) 0xA3, (byte) 0xF7, (byte) 0xF2, (byte) 0xCE,
                        (byte) 0xF9, (byte) 0x61, (byte) 0x15, (byte) 0xA1}, {(byte) 0xE0, (byte) 0xAE, (byte) 0x5D,
                        (byte) 0xA4, (byte) 0x9B, (byte) 0x34, (byte) 0x1A, (byte) 0x55, (byte) 0xAD, (byte) 0x93,
                        (byte) 0x32, (byte) 0x30, (byte) 0xF5, (byte) 0x8C, (byte) 0xB1, (byte) 0xE3}, {(byte) 0x1D,
                        (byte) 0xF6, (byte) 0xE2, (byte) 0x2E, (byte) 0x82, (byte) 0x66, (byte) 0xCA, (byte) 0x60,
                        (byte) 0xC0, (byte) 0x29, (byte) 0x23, (byte) 0xAB, (byte) 0x0D, (byte) 0x53, (byte) 0x4E,
                        (byte) 0x6F}, {(byte) 0xD5, (byte) 0xDB, (byte) 0x37, (byte) 0x45, (byte) 0xDE, (byte) 0xFD,
                        (byte) 0x8E, (byte) 0x2F, (byte) 0x03, (byte) 0xFF, (byte) 0x6A, (byte) 0x72, (byte) 0x6D,
                        (byte) 0x6C, (byte) 0x5B, (byte) 0x51}, {(byte) 0x8D, (byte) 0x1B, (byte) 0xAF, (byte) 0x92,
                        (byte) 0xBB, (byte) 0xDD, (byte) 0xBC, (byte) 0x7F, (byte) 0x11, (byte) 0xD9, (byte) 0x5C,
                        (byte) 0x41, (byte) 0x1F, (byte) 0x10, (byte) 0x5A, (byte) 0xD8}, {(byte) 0x0A, (byte) 0xC1,
                        (byte) 0x31, (byte) 0x88, (byte) 0xA5, (byte) 0xCD, (byte) 0x7B, (byte) 0xBD, (byte) 0x2D,
                        (byte) 0x74, (byte) 0xD0, (byte) 0x12, (byte) 0xB8, (byte) 0xE5, (byte) 0xB4, (byte) 0xB0},
                        {(byte) 0x89, (byte) 0x69, (byte) 0x97, (byte) 0x4A, (byte) 0x0C, (byte) 0x96, (byte) 0x77,
                                (byte) 0x7E, (byte) 0x65, (byte) 0xB9, (byte) 0xF1, (byte) 0x09, (byte) 0xC5, (byte) 0x6E,
                                (byte) 0xC6, (byte) 0x84}, {(byte) 0x18, (byte) 0xF0, (byte) 0x7D, (byte) 0xEC, (byte) 0x3A,
                        (byte) 0xDC, (byte) 0x4D, (byte) 0x20, (byte) 0x79, (byte) 0xEE, (byte) 0x5F, (byte) 0x3E,
                        (byte) 0xD7, (byte) 0xCB, (byte) 0x39, (byte) 0x48}};
                int row = 0xF0, col = 0xF;
                row = (row & a) >> 4;
                col = col & a;
                return sbox[row][col];
            }

            /**
             * 非线性函数，(b0,b1,b2,b3) = (sbox(a0),sbox(a1),sbox(a2),sbox(a3))
             *
             * @param a 传入数据
             * @return 非线性函数得到的值
             */
            private int nonlinearTrans(int a)
            {
                int res = 0;
                res = (sBox((byte) (a >> 24 & 0xFF)) & 0xFF) << 24;
                res = res | (sBox((byte) (a >> 16 & 0xFF)) & 0xFF) << 16;
                res = res | (sBox((byte) (a >> 8 & 0xFF)) & 0xFF) << 8;
                res = res | sBox((byte) (a & 0xFF)) & 0xFF;
                return res;
            }

            /**
             * 获得固定参数CK的值
             *
             * @param i CK(i) i=0,1,..,31
             * @return 固定参数的值
             */
            private int getCK(int i)
            {
                int res = 0;
                for (int j = 0; j < 4; j++)
                {
                    res = res << 8;
                    res = res | ((4 * i + j) * 7) % 256;
                }
                return res;
            }
        });


    }
}