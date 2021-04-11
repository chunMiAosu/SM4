package com.example.sm4_4;

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
    byte[][] sbox = {{(byte) 0xD6, (byte) 0x90, (byte) 0xE9, (byte) 0xFE, (byte) 0xCC, (byte) 0xE1,
            (byte) 0x3D, (byte) 0xB7, (byte) 0x16, (byte) 0xB6, (byte) 0x14, (byte) 0xC2, (byte) 0x28, (byte) 0xFB,
            (byte) 0x2C, (byte) 0x05}, {(byte) 0x2B, (byte) 0x67, (byte) 0x9A, (byte) 0x76, (byte) 0x2A, (byte) 0xBE,
            (byte) 0x04, (byte) 0xC3, (byte) 0xAA, (byte) 0x44, (byte) 0x13, (byte) 0x26, (byte) 0x49, (byte) 0x86,
            (byte) 0x06, (byte) 0x99}, {(byte) 0x9C, (byte) 0x42, (byte) 0x50, (byte) 0xF4, (byte) 0x91, (byte) 0xEF,
            (byte) 0x98, (byte) 0x7A, (byte) 0x33, (byte) 0x54, (byte) 0x0B, (byte) 0x43, (byte) 0xED, (byte) 0xCF,
            (byte) 0xAC, (byte) 0x62}, {(byte) 0xE4, (byte) 0xB3, (byte) 0x1C, (byte) 0xA9, (byte) 0xC9, (byte) 0x08,
            (byte) 0xE8, (byte) 0x95, (byte) 0x80, (byte) 0xDF, (byte) 0x94, (byte) 0xFA, (byte) 0x75, (byte) 0x8F,
            (byte) 0x3F, (byte) 0xA6}, {(byte) 0x47, (byte) 0x07, (byte) 0xA7, (byte) 0xFC, (byte) 0xF3, (byte) 0x73,
            (byte) 0x17, (byte) 0xBA, (byte) 0x83, (byte) 0x59, (byte) 0x3C, (byte) 0x19, (byte) 0xE6, (byte) 0x85,
            (byte) 0x4F, (byte) 0xA8}, {(byte) 0x68, (byte) 0x6B, (byte) 0x81, (byte) 0xB2, (byte) 0x71, (byte) 0x64,
            (byte) 0xDA, (byte) 0x8B, (byte) 0xF8, (byte) 0xEB, (byte) 0x0F, (byte) 0x4B, (byte) 0x70, (byte) 0x56,
            (byte) 0x9D, (byte) 0x35}, {(byte) 0x1E, (byte) 0x24, (byte) 0x0E, (byte) 0x5E, (byte) 0x63, (byte) 0x58,
            (byte) 0xD1, (byte) 0xA2, (byte) 0x25, (byte) 0x22, (byte) 0x7C, (byte) 0x3B, (byte) 0x01, (byte) 0x21,
            (byte) 0x78, (byte) 0x87}, {(byte) 0xD4, (byte) 0x00, (byte) 0x46, (byte) 0x57, (byte) 0x9F, (byte) 0xD3,
            (byte) 0x27, (byte) 0x52, (byte) 0x4C, (byte) 0x36, (byte) 0x02, (byte) 0xE7, (byte) 0xA0, (byte) 0xC4,
            (byte) 0xC8, (byte) 0x9E}, {(byte) 0xEA, (byte) 0xBF, (byte) 0x8A, (byte) 0xD2, (byte) 0x40, (byte) 0xC7,
            (byte) 0x38, (byte) 0xB5, (byte) 0xA3, (byte) 0xF7, (byte) 0xF2, (byte) 0xCE, (byte) 0xF9, (byte) 0x61,
            (byte) 0x15, (byte) 0xA1}, {(byte) 0xE0, (byte) 0xAE, (byte) 0x5D, (byte) 0xA4, (byte) 0x9B, (byte) 0x34,
            (byte) 0x1A, (byte) 0x55, (byte) 0xAD, (byte) 0x93, (byte) 0x32, (byte) 0x30, (byte) 0xF5, (byte) 0x8C,
            (byte) 0xB1, (byte) 0xE3}, {(byte) 0x1D, (byte) 0xF6, (byte) 0xE2, (byte) 0x2E, (byte) 0x82, (byte) 0x66,
            (byte) 0xCA, (byte) 0x60, (byte) 0xC0, (byte) 0x29, (byte) 0x23, (byte) 0xAB, (byte) 0x0D, (byte) 0x53,
            (byte) 0x4E, (byte) 0x6F}, {(byte) 0xD5, (byte) 0xDB, (byte) 0x37, (byte) 0x45, (byte) 0xDE, (byte) 0xFD,
            (byte) 0x8E, (byte) 0x2F, (byte) 0x03, (byte) 0xFF, (byte) 0x6A, (byte) 0x72, (byte) 0x6D, (byte) 0x6C,
            (byte) 0x5B, (byte) 0x51}, {(byte) 0x8D, (byte) 0x1B, (byte) 0xAF, (byte) 0x92, (byte) 0xBB, (byte) 0xDD,
            (byte) 0xBC, (byte) 0x7F, (byte) 0x11, (byte) 0xD9, (byte) 0x5C, (byte) 0x41, (byte) 0x1F, (byte) 0x10,
            (byte) 0x5A, (byte) 0xD8}, {(byte) 0x0A, (byte) 0xC1, (byte) 0x31, (byte) 0x88, (byte) 0xA5, (byte) 0xCD,
            (byte) 0x7B, (byte) 0xBD, (byte) 0x2D, (byte) 0x74, (byte) 0xD0, (byte) 0x12, (byte) 0xB8, (byte) 0xE5,
            (byte) 0xB4, (byte) 0xB0}, {(byte) 0x89, (byte) 0x69, (byte) 0x97, (byte) 0x4A, (byte) 0x0C, (byte) 0x96,
            (byte) 0x77, (byte) 0x7E, (byte) 0x65, (byte) 0xB9, (byte) 0xF1, (byte) 0x09, (byte) 0xC5, (byte) 0x6E,
            (byte) 0xC6, (byte) 0x84}, {(byte) 0x18, (byte) 0xF0, (byte) 0x7D, (byte) 0xEC, (byte) 0x3A, (byte) 0xDC,
            (byte) 0x4D, (byte) 0x20, (byte) 0x79, (byte) 0xEE, (byte) 0x5F, (byte) 0x3E, (byte) 0xD7, (byte) 0xCB,
            (byte) 0x39, (byte) 0x48}};

    int[] lookup0 = {0x8ed55b5b, 0xd0924242, 0x4deaa7a7, 0x6fdfbfb, 0xfccf3333, 0x65e28787, 0xc93df4f4,
            0x6bb5dede, 0x4e165858, 0x6eb4dada, 0x44145050, 0xcac10b0b, 0x8828a0a0, 0x17f8efef, 0x9c2cb0b0,
            0x11051414, 0x872bacac, 0xfb669d9d, 0xf2986a6a, 0xae77d9d9, 0x822aa8a8, 0x46bcfafa, 0x14041010,
            0xcfc00f0f, 0x2a8aaaa, 0x54451111, 0x5f134c4c, 0xbe269898, 0x6d482525, 0x9e841a1a, 0x1e061818, 0xfd9b6666
            , 0xec9e7272, 0x4a430909, 0x10514141, 0x24f7d3d3, 0xd5934646, 0x53ecbfbf, 0xf89a6262, 0x927be9e9,
            0xff33cccc, 0x4555151, 0x270b2c2c, 0x4f420d0d, 0x59eeb7b7, 0xf3cc3f3f, 0x1caeb2b2, 0xea638989, 0x74e79393
            , 0x7fb1cece, 0x6c1c7070, 0xdaba6a6, 0xedca2727, 0x28082020, 0x48eba3a3, 0xc1975656, 0x80820202,
            0xa3dc7f7f, 0xc4965252, 0x12f9ebeb, 0xa174d5d5, 0xb38d3e3e, 0xc33ffcfc, 0x3ea49a9a, 0x5b461d1d,
            0x1b071c1c, 0x3ba59e9e, 0xcfff3f3, 0x3ff0cfcf, 0xbf72cdcd, 0x4b175c5c, 0x52b8eaea, 0x8f810e0e, 0x3d586565
            , 0xcc3cf0f0, 0x7d196464, 0x7ee59b9b, 0x91871616, 0x734e3d3d, 0x8aaa2a2, 0xc869a1a1, 0xc76aadad,
            0x85830606, 0x7ab0caca, 0xb570c5c5, 0xf4659191, 0xb2d96b6b, 0xa7892e2e, 0x18fbe3e3, 0x47e8afaf,
            0x330f3c3c, 0x674a2d2d, 0xb071c1c1, 0xe575959, 0xe99f7676, 0xe135d4d4, 0x661e7878, 0xb4249090, 0x360e3838
            , 0x265f7979, 0xef628d8d, 0x38596161, 0x95d24747, 0x2aa08a8a, 0xb1259494, 0xaa228888, 0x8c7df1f1,
            0xd73becec, 0x5010404, 0xa5218484, 0x9879e1e1, 0x9b851e1e, 0x84d75353, 0x0, 0x5e471919, 0xb565d5d,
            0xe39d7e7e, 0x9fd04f4f, 0xbb279c9c, 0x1a534949, 0x7c4d3131, 0xee36d8d8, 0xa020808, 0x7be49f9f, 0x20a28282
            , 0xd4c71313, 0xe8cb2323, 0xe69c7a7a, 0x42e9abab, 0x43bdfefe, 0xa2882a2a, 0x9ad14b4b, 0x40410101,
            0xdbc41f1f, 0xd838e0e0, 0x61b7d6d6, 0x2fa18e8e, 0x2bf4dfdf, 0x3af1cbcb, 0xf6cd3b3b, 0x1dfae7e7,
            0xe5608585, 0x41155454, 0x25a38686, 0x60e38383, 0x16acbaba, 0x295c7575, 0x34a69292, 0xf7996e6e,
            0xe434d0d0, 0x721a6868, 0x1545555, 0x19afb6b6, 0xdf914e4e, 0xfa32c8c8, 0xf030c0c0, 0x21f6d7d7, 0xbc8e3232
            , 0x75b3c6c6, 0x6fe08f8f, 0x691d7474, 0x2ef5dbdb, 0x6ae18b8b, 0x962eb8b8, 0x8a800a0a, 0xfe679999,
            0xe2c92b2b, 0xe0618181, 0xc0c30303, 0x8d29a4a4, 0xaf238c8c, 0x7a9aeae, 0x390d3434, 0x1f524d4d, 0x764f3939
            , 0xd36ebdbd, 0x81d65757, 0xb7d86f6f, 0xeb37dcdc, 0x51441515, 0xa6dd7b7b, 0x9fef7f7, 0xb68c3a3a,
            0x932fbcbc, 0xf030c0c, 0x3fcffff, 0xc26ba9a9, 0xba73c9c9, 0xd96cb5b5, 0xdc6db1b1, 0x375a6d6d, 0x15504545,
            0xb98f3636, 0x771b6c6c, 0x13adbebe, 0xda904a4a, 0x57b9eeee, 0xa9de7777, 0x4cbef2f2, 0x837efdfd,
            0x55114444, 0xbdda6767, 0x2c5d7171, 0x45400505, 0x631f7c7c, 0x50104040, 0x325b6969, 0xb8db6363,
            0x220a2828, 0xc5c20707, 0xf531c4c4, 0xa88a2222, 0x31a79696, 0xf9ce3737, 0x977aeded, 0x49bff6f6,
            0x992db4b4, 0xa475d1d1, 0x90d34343, 0x5a124848, 0x58bae2e2, 0x71e69797, 0x64b6d2d2, 0x70b2c2c2,
            0xad8b2626, 0xcd68a5a5, 0xcb955e5e, 0x624b2929, 0x3c0c3030, 0xce945a5a, 0xab76dddd, 0x867ff9f9,
            0xf1649595, 0x5dbbe6e6, 0x35f2c7c7, 0x2d092424, 0xd1c61717, 0xd66fb9b9, 0xdec51b1b, 0x94861212,
            0x78186060, 0x30f3c3c3, 0x897cf5f5, 0x5cefb3b3, 0xd23ae8e8, 0xacdf7373, 0x794c3535, 0xa0208080,
            0x9d78e5e5, 0x56edbbbb, 0x235e7d7d, 0xc63ef8f8, 0x8bd45f5f, 0xe7c82f2f, 0xdd39e4e4, 0x68492121};

    int[] lookup1 = {0x5b8ed55b, 0x42d09242, 0xa74deaa7, 0xfb06fdfb, 0x33fccf33, 0x8765e287, 0xf4c93df4,
            0xde6bb5de, 0x584e1658, 0xda6eb4da, 0x50441450, 0xbcac10b, 0xa08828a0, 0xef17f8ef, 0xb09c2cb0, 0x14110514
            , 0xac872bac, 0x9dfb669d, 0x6af2986a, 0xd9ae77d9, 0xa8822aa8, 0xfa46bcfa, 0x10140410, 0xfcfc00f,
            0xaa02a8aa, 0x11544511, 0x4c5f134c, 0x98be2698, 0x256d4825, 0x1a9e841a, 0x181e0618, 0x66fd9b66,
            0x72ec9e72, 0x94a4309, 0x41105141, 0xd324f7d3, 0x46d59346, 0xbf53ecbf, 0x62f89a62, 0xe9927be9, 0xccff33cc
            , 0x51045551, 0x2c270b2c, 0xd4f420d, 0xb759eeb7, 0x3ff3cc3f, 0xb21caeb2, 0x89ea6389, 0x9374e793,
            0xce7fb1ce, 0x706c1c70, 0xa60daba6, 0x27edca27, 0x20280820, 0xa348eba3, 0x56c19756, 0x2808202, 0x7fa3dc7f
            , 0x52c49652, 0xeb12f9eb, 0xd5a174d5, 0x3eb38d3e, 0xfcc33ffc, 0x9a3ea49a, 0x1d5b461d, 0x1c1b071c,
            0x9e3ba59e, 0xf30cfff3, 0xcf3ff0cf, 0xcdbf72cd, 0x5c4b175c, 0xea52b8ea, 0xe8f810e, 0x653d5865, 0xf0cc3cf0
            , 0x647d1964, 0x9b7ee59b, 0x16918716, 0x3d734e3d, 0xa208aaa2, 0xa1c869a1, 0xadc76aad, 0x6858306,
            0xca7ab0ca, 0xc5b570c5, 0x91f46591, 0x6bb2d96b, 0x2ea7892e, 0xe318fbe3, 0xaf47e8af, 0x3c330f3c,
            0x2d674a2d, 0xc1b071c1, 0x590e5759, 0x76e99f76, 0xd4e135d4, 0x78661e78, 0x90b42490, 0x38360e38,
            0x79265f79, 0x8def628d, 0x61385961, 0x4795d247, 0x8a2aa08a, 0x94b12594, 0x88aa2288, 0xf18c7df1,
            0xecd73bec, 0x4050104, 0x84a52184, 0xe19879e1, 0x1e9b851e, 0x5384d753, 0x0, 0x195e4719, 0x5d0b565d,
            0x7ee39d7e, 0x4f9fd04f, 0x9cbb279c, 0x491a5349, 0x317c4d31, 0xd8ee36d8, 0x80a0208, 0x9f7be49f, 0x8220a282
            , 0x13d4c713, 0x23e8cb23, 0x7ae69c7a, 0xab42e9ab, 0xfe43bdfe, 0x2aa2882a, 0x4b9ad14b, 0x1404101,
            0x1fdbc41f, 0xe0d838e0, 0xd661b7d6, 0x8e2fa18e, 0xdf2bf4df, 0xcb3af1cb, 0x3bf6cd3b, 0xe71dfae7,
            0x85e56085, 0x54411554, 0x8625a386, 0x8360e383, 0xba16acba, 0x75295c75, 0x9234a692, 0x6ef7996e,
            0xd0e434d0, 0x68721a68, 0x55015455, 0xb619afb6, 0x4edf914e, 0xc8fa32c8, 0xc0f030c0, 0xd721f6d7,
            0x32bc8e32, 0xc675b3c6, 0x8f6fe08f, 0x74691d74, 0xdb2ef5db, 0x8b6ae18b, 0xb8962eb8, 0xa8a800a, 0x99fe6799
            , 0x2be2c92b, 0x81e06181, 0x3c0c303, 0xa48d29a4, 0x8caf238c, 0xae07a9ae, 0x34390d34, 0x4d1f524d,
            0x39764f39, 0xbdd36ebd, 0x5781d657, 0x6fb7d86f, 0xdceb37dc, 0x15514415, 0x7ba6dd7b, 0xf709fef7,
            0x3ab68c3a, 0xbc932fbc, 0xc0f030c, 0xff03fcff, 0xa9c26ba9, 0xc9ba73c9, 0xb5d96cb5, 0xb1dc6db1, 0x6d375a6d
            , 0x45155045, 0x36b98f36, 0x6c771b6c, 0xbe13adbe, 0x4ada904a, 0xee57b9ee, 0x77a9de77, 0xf24cbef2,
            0xfd837efd, 0x44551144, 0x67bdda67, 0x712c5d71, 0x5454005, 0x7c631f7c, 0x40501040, 0x69325b69, 0x63b8db63
            , 0x28220a28, 0x7c5c207, 0xc4f531c4, 0x22a88a22, 0x9631a796, 0x37f9ce37, 0xed977aed, 0xf649bff6,
            0xb4992db4, 0xd1a475d1, 0x4390d343, 0x485a1248, 0xe258bae2, 0x9771e697, 0xd264b6d2, 0xc270b2c2,
            0x26ad8b26, 0xa5cd68a5, 0x5ecb955e, 0x29624b29, 0x303c0c30, 0x5ace945a, 0xddab76dd, 0xf9867ff9,
            0x95f16495, 0xe65dbbe6, 0xc735f2c7, 0x242d0924, 0x17d1c617, 0xb9d66fb9, 0x1bdec51b, 0x12948612,
            0x60781860, 0xc330f3c3, 0xf5897cf5, 0xb35cefb3, 0xe8d23ae8, 0x73acdf73, 0x35794c35, 0x80a02080,
            0xe59d78e5, 0xbb56edbb, 0x7d235e7d, 0xf8c63ef8, 0x5f8bd45f, 0x2fe7c82f, 0xe4dd39e4, 0x21684921};

    int[] lookup2 = {0x5b5b8ed5, 0x4242d092, 0xa7a74dea, 0xfbfb06fd, 0x3333fccf, 0x878765e2, 0xf4f4c93d,
            0xdede6bb5, 0x58584e16, 0xdada6eb4, 0x50504414, 0xb0bcac1, 0xa0a08828, 0xefef17f8, 0xb0b09c2c, 0x14141105
            , 0xacac872b, 0x9d9dfb66, 0x6a6af298, 0xd9d9ae77, 0xa8a8822a, 0xfafa46bc, 0x10101404, 0xf0fcfc0,
            0xaaaa02a8, 0x11115445, 0x4c4c5f13, 0x9898be26, 0x25256d48, 0x1a1a9e84, 0x18181e06, 0x6666fd9b,
            0x7272ec9e, 0x9094a43, 0x41411051, 0xd3d324f7, 0x4646d593, 0xbfbf53ec, 0x6262f89a, 0xe9e9927b, 0xccccff33
            , 0x51510455, 0x2c2c270b, 0xd0d4f42, 0xb7b759ee, 0x3f3ff3cc, 0xb2b21cae, 0x8989ea63, 0x939374e7,
            0xcece7fb1, 0x70706c1c, 0xa6a60dab, 0x2727edca, 0x20202808, 0xa3a348eb, 0x5656c197, 0x2028082, 0x7f7fa3dc
            , 0x5252c496, 0xebeb12f9, 0xd5d5a174, 0x3e3eb38d, 0xfcfcc33f, 0x9a9a3ea4, 0x1d1d5b46, 0x1c1c1b07,
            0x9e9e3ba5, 0xf3f30cff, 0xcfcf3ff0, 0xcdcdbf72, 0x5c5c4b17, 0xeaea52b8, 0xe0e8f81, 0x65653d58, 0xf0f0cc3c
            , 0x64647d19, 0x9b9b7ee5, 0x16169187, 0x3d3d734e, 0xa2a208aa, 0xa1a1c869, 0xadadc76a, 0x6068583,
            0xcaca7ab0, 0xc5c5b570, 0x9191f465, 0x6b6bb2d9, 0x2e2ea789, 0xe3e318fb, 0xafaf47e8, 0x3c3c330f,
            0x2d2d674a, 0xc1c1b071, 0x59590e57, 0x7676e99f, 0xd4d4e135, 0x7878661e, 0x9090b424, 0x3838360e,
            0x7979265f, 0x8d8def62, 0x61613859, 0x474795d2, 0x8a8a2aa0, 0x9494b125, 0x8888aa22, 0xf1f18c7d,
            0xececd73b, 0x4040501, 0x8484a521, 0xe1e19879, 0x1e1e9b85, 0x535384d7, 0x0, 0x19195e47, 0x5d5d0b56,
            0x7e7ee39d, 0x4f4f9fd0, 0x9c9cbb27, 0x49491a53, 0x31317c4d, 0xd8d8ee36, 0x8080a02, 0x9f9f7be4, 0x828220a2
            , 0x1313d4c7, 0x2323e8cb, 0x7a7ae69c, 0xabab42e9, 0xfefe43bd, 0x2a2aa288, 0x4b4b9ad1, 0x1014041,
            0x1f1fdbc4, 0xe0e0d838, 0xd6d661b7, 0x8e8e2fa1, 0xdfdf2bf4, 0xcbcb3af1, 0x3b3bf6cd, 0xe7e71dfa,
            0x8585e560, 0x54544115, 0x868625a3, 0x838360e3, 0xbaba16ac, 0x7575295c, 0x929234a6, 0x6e6ef799,
            0xd0d0e434, 0x6868721a, 0x55550154, 0xb6b619af, 0x4e4edf91, 0xc8c8fa32, 0xc0c0f030, 0xd7d721f6,
            0x3232bc8e, 0xc6c675b3, 0x8f8f6fe0, 0x7474691d, 0xdbdb2ef5, 0x8b8b6ae1, 0xb8b8962e, 0xa0a8a80, 0x9999fe67
            , 0x2b2be2c9, 0x8181e061, 0x303c0c3, 0xa4a48d29, 0x8c8caf23, 0xaeae07a9, 0x3434390d, 0x4d4d1f52,
            0x3939764f, 0xbdbdd36e, 0x575781d6, 0x6f6fb7d8, 0xdcdceb37, 0x15155144, 0x7b7ba6dd, 0xf7f709fe,
            0x3a3ab68c, 0xbcbc932f, 0xc0c0f03, 0xffff03fc, 0xa9a9c26b, 0xc9c9ba73, 0xb5b5d96c, 0xb1b1dc6d, 0x6d6d375a
            , 0x45451550, 0x3636b98f, 0x6c6c771b, 0xbebe13ad, 0x4a4ada90, 0xeeee57b9, 0x7777a9de, 0xf2f24cbe,
            0xfdfd837e, 0x44445511, 0x6767bdda, 0x71712c5d, 0x5054540, 0x7c7c631f, 0x40405010, 0x6969325b, 0x6363b8db
            , 0x2828220a, 0x707c5c2, 0xc4c4f531, 0x2222a88a, 0x969631a7, 0x3737f9ce, 0xeded977a, 0xf6f649bf,
            0xb4b4992d, 0xd1d1a475, 0x434390d3, 0x48485a12, 0xe2e258ba, 0x979771e6, 0xd2d264b6, 0xc2c270b2,
            0x2626ad8b, 0xa5a5cd68, 0x5e5ecb95, 0x2929624b, 0x30303c0c, 0x5a5ace94, 0xddddab76, 0xf9f9867f,
            0x9595f164, 0xe6e65dbb, 0xc7c735f2, 0x24242d09, 0x1717d1c6, 0xb9b9d66f, 0x1b1bdec5, 0x12129486,
            0x60607818, 0xc3c330f3, 0xf5f5897c, 0xb3b35cef, 0xe8e8d23a, 0x7373acdf, 0x3535794c, 0x8080a020,
            0xe5e59d78, 0xbbbb56ed, 0x7d7d235e, 0xf8f8c63e, 0x5f5f8bd4, 0x2f2fe7c8, 0xe4e4dd39, 0x21216849};

    int[] lookup3 = {0xd55b5b8e, 0x924242d0, 0xeaa7a74d, 0xfdfbfb06, 0xcf3333fc, 0xe2878765, 0x3df4f4c9,
            0xb5dede6b, 0x1658584e, 0xb4dada6e, 0x14505044, 0xc10b0bca, 0x28a0a088, 0xf8efef17, 0x2cb0b09c, 0x5141411
            , 0x2bacac87, 0x669d9dfb, 0x986a6af2, 0x77d9d9ae, 0x2aa8a882, 0xbcfafa46, 0x4101014, 0xc00f0fcf,
            0xa8aaaa02, 0x45111154, 0x134c4c5f, 0x269898be, 0x4825256d, 0x841a1a9e, 0x618181e, 0x9b6666fd, 0x9e7272ec
            , 0x4309094a, 0x51414110, 0xf7d3d324, 0x934646d5, 0xecbfbf53, 0x9a6262f8, 0x7be9e992, 0x33ccccff,
            0x55515104, 0xb2c2c27, 0x420d0d4f, 0xeeb7b759, 0xcc3f3ff3, 0xaeb2b21c, 0x638989ea, 0xe7939374, 0xb1cece7f
            , 0x1c70706c, 0xaba6a60d, 0xca2727ed, 0x8202028, 0xeba3a348, 0x975656c1, 0x82020280, 0xdc7f7fa3,
            0x965252c4, 0xf9ebeb12, 0x74d5d5a1, 0x8d3e3eb3, 0x3ffcfcc3, 0xa49a9a3e, 0x461d1d5b, 0x71c1c1b, 0xa59e9e3b
            , 0xfff3f30c, 0xf0cfcf3f, 0x72cdcdbf, 0x175c5c4b, 0xb8eaea52, 0x810e0e8f, 0x5865653d, 0x3cf0f0cc,
            0x1964647d, 0xe59b9b7e, 0x87161691, 0x4e3d3d73, 0xaaa2a208, 0x69a1a1c8, 0x6aadadc7, 0x83060685,
            0xb0caca7a, 0x70c5c5b5, 0x659191f4, 0xd96b6bb2, 0x892e2ea7, 0xfbe3e318, 0xe8afaf47, 0xf3c3c33, 0x4a2d2d67
            , 0x71c1c1b0, 0x5759590e, 0x9f7676e9, 0x35d4d4e1, 0x1e787866, 0x249090b4, 0xe383836, 0x5f797926,
            0x628d8def, 0x59616138, 0xd2474795, 0xa08a8a2a, 0x259494b1, 0x228888aa, 0x7df1f18c, 0x3bececd7, 0x1040405
            , 0x218484a5, 0x79e1e198, 0x851e1e9b, 0xd7535384, 0x0, 0x4719195e, 0x565d5d0b, 0x9d7e7ee3, 0xd04f4f9f,
            0x279c9cbb, 0x5349491a, 0x4d31317c, 0x36d8d8ee, 0x208080a, 0xe49f9f7b, 0xa2828220, 0xc71313d4, 0xcb2323e8
            , 0x9c7a7ae6, 0xe9abab42, 0xbdfefe43, 0x882a2aa2, 0xd14b4b9a, 0x41010140, 0xc41f1fdb, 0x38e0e0d8,
            0xb7d6d661, 0xa18e8e2f, 0xf4dfdf2b, 0xf1cbcb3a, 0xcd3b3bf6, 0xfae7e71d, 0x608585e5, 0x15545441,
            0xa3868625, 0xe3838360, 0xacbaba16, 0x5c757529, 0xa6929234, 0x996e6ef7, 0x34d0d0e4, 0x1a686872,
            0x54555501, 0xafb6b619, 0x914e4edf, 0x32c8c8fa, 0x30c0c0f0, 0xf6d7d721, 0x8e3232bc, 0xb3c6c675,
            0xe08f8f6f, 0x1d747469, 0xf5dbdb2e, 0xe18b8b6a, 0x2eb8b896, 0x800a0a8a, 0x679999fe, 0xc92b2be2,
            0x618181e0, 0xc30303c0, 0x29a4a48d, 0x238c8caf, 0xa9aeae07, 0xd343439, 0x524d4d1f, 0x4f393976, 0x6ebdbdd3
            , 0xd6575781, 0xd86f6fb7, 0x37dcdceb, 0x44151551, 0xdd7b7ba6, 0xfef7f709, 0x8c3a3ab6, 0x2fbcbc93,
            0x30c0c0f, 0xfcffff03, 0x6ba9a9c2, 0x73c9c9ba, 0x6cb5b5d9, 0x6db1b1dc, 0x5a6d6d37, 0x50454515, 0x8f3636b9
            , 0x1b6c6c77, 0xadbebe13, 0x904a4ada, 0xb9eeee57, 0xde7777a9, 0xbef2f24c, 0x7efdfd83, 0x11444455,
            0xda6767bd, 0x5d71712c, 0x40050545, 0x1f7c7c63, 0x10404050, 0x5b696932, 0xdb6363b8, 0xa282822, 0xc20707c5
            , 0x31c4c4f5, 0x8a2222a8, 0xa7969631, 0xce3737f9, 0x7aeded97, 0xbff6f649, 0x2db4b499, 0x75d1d1a4,
            0xd3434390, 0x1248485a, 0xbae2e258, 0xe6979771, 0xb6d2d264, 0xb2c2c270, 0x8b2626ad, 0x68a5a5cd,
            0x955e5ecb, 0x4b292962, 0xc30303c, 0x945a5ace, 0x76ddddab, 0x7ff9f986, 0x649595f1, 0xbbe6e65d, 0xf2c7c735
            , 0x924242d, 0xc61717d1, 0x6fb9b9d6, 0xc51b1bde, 0x86121294, 0x18606078, 0xf3c3c330, 0x7cf5f589,
            0xefb3b35c, 0x3ae8e8d2, 0xdf7373ac, 0x4c353579, 0x208080a0, 0x78e5e59d, 0xedbbbb56, 0x5e7d7d23,
            0x3ef8f8c6, 0xd45f5f8b, 0xc82f2fe7, 0x39e4e4dd, 0x49212168};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        writeFile = new File("/sdcard/aaaaasm4/timeData_4.csv");

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

                        long fileLength = size << 20;

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

                        if(size < 16)
                        {
                            len = (int)(fileLength+16-(fileLength%16));
                        }
                        else
                        {
                            len = 16<<20; //16M
                        }

                        byte[] plaintext = new byte[len];//1280 = 16*16*5
                        byte[] ciphertext = new byte[len]; //1280 = 16*16*5

                        len = 0;
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

                int tmp;
                int tmp1;

                //32次迭代运算，加密时，轮密钥的使用顺序为0,1，...，31；解密时，轮密钥的使用顺序为31,30，...，1,0
                startTime = System.currentTimeMillis();
                for (int i = 0; i < 32; i += 4)
                {
                    if (enOrDe == 1)
                    {
                        tmp = x[1] ^ x[2] ^ x[3] ^ rk[i];

                        tmp = lookup0[(tmp>>24) & 0xFF] ^ lookup1[(tmp>>16) & 0xFF] ^ lookup2[(tmp>>8) & 0xFF] ^ lookup3[tmp & 0xFF];//
                        tmp1 = x[2] ^ x[3] ^ rk[1 + i]; //
                        x[0] = x[0] ^ tmp;  //这三句的顺序可以更好的利用资源，流水线

                        tmp = tmp1 ^ x[0];

                        tmp = lookup0[(tmp>>24) & 0xFF] ^ lookup1[(tmp>>16) & 0xFF] ^ lookup2[(tmp>>8) & 0xFF] ^ lookup3[tmp & 0xFF];
                        tmp1 = x[3] ^ x[0] ^ rk[2 + i];
                        x[1] = x[1] ^ tmp;

                        tmp = tmp1 ^ x[1];

                        tmp = lookup0[(tmp>>24) & 0xFF] ^ lookup1[(tmp>>16) & 0xFF] ^ lookup2[(tmp>>8) & 0xFF] ^ lookup3[tmp & 0xFF];
                        tmp1 = x[0] ^ x[1] ^ rk[3 + i];
                        x[2] = x[2] ^ tmp;

                        tmp = tmp1 ^ x[2];
                        tmp = lookup0[(tmp>>24) & 0xFF] ^ lookup1[(tmp>>16) & 0xFF] ^ lookup2[(tmp>>8) & 0xFF] ^ lookup3[tmp & 0xFF];
                        x[3] = x[3] ^ tmp;
                    }
                    else
                    {
                        tmp = x[1] ^ x[2] ^ x[3] ^ rk[31-i];

                        tmp = lookup0[(tmp>>24) & 0xFF] ^ lookup1[(tmp>>16) & 0xFF] ^ lookup2[(tmp>>8) & 0xFF] ^ lookup3[tmp & 0xFF];//
                        tmp1 = x[2] ^ x[3] ^ rk[31-(1 + i)]; //
                        x[0] = x[0] ^ tmp;  //这三句的顺序可以更好的利用资源，流水线

                        tmp = tmp1 ^ x[0];

                        tmp = lookup0[(tmp>>24) & 0xFF] ^ lookup1[(tmp>>16) & 0xFF] ^ lookup2[(tmp>>8) & 0xFF] ^ lookup3[tmp & 0xFF];
                        tmp1 = x[3] ^ x[0] ^ rk[31-(2 + i)];
                        x[1] = x[1] ^ tmp;

                        tmp = tmp1 ^ x[1];

                        tmp = lookup0[(tmp>>24) & 0xFF] ^ lookup1[(tmp>>16) & 0xFF] ^ lookup2[(tmp>>8) & 0xFF] ^ lookup3[tmp & 0xFF];
                        tmp1 = x[0] ^ x[1] ^ rk[31-(3 + i)];
                        x[2] = x[2] ^ tmp;

                        tmp = tmp1 ^ x[2];
                        tmp = lookup0[(tmp>>24) & 0xFF] ^ lookup1[(tmp>>16) & 0xFF] ^ lookup2[(tmp>>8) & 0xFF] ^ lookup3[tmp & 0xFF];
                        x[3] = x[3] ^ tmp;
                    }

                }
                endTime = System.currentTimeMillis();
                time_iterativeOperation += endTime - startTime;


                //反序变换
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
                int[] ck = {0x00070E15, 0x1C232A31, 0x383F464D, 0x545B6269, 0x70777E85, 0x8C939AA1, 0xA8AFB6BD, 0xC4CBD2D9
                        , 0xE0E7EEF5, 0xFC030A11, 0x181F262D, 0x343B4249, 0x50575E65, 0x6C737A81, 0x888F969D, 0xA4ABB2B9,
                        0xC0C7CED5, 0xDCE3EAF1, 0xF8FF060D, 0x141B2229, 0x30373E45, 0x4C535A61, 0x686F767D, 0x848B9299,
                        0xA0A7AEB5, 0xBCC3CAD1, 0xD8DFE6ED, 0xF4FB0209, 0x10171E25, 0x2C333A41, 0x484F565D, 0x646B7279};
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

                int tmp;
                int nonlinearTrans_res;
                //计算得到轮密钥
                for (int i = 0; i < 32; i += 4)
                {
                    tmp = k[1] ^ k[2] ^ k[3] ^ ck[i];
                    nonlinearTrans_res = 0;
                    nonlinearTrans_res =
                            (sbox[(0x000000F0 & (byte) (tmp >> 24 & 0xFF)) >> 4][0x0000000F & (byte) (tmp >> 24 & 0xFF)] & 0xFF) << 24;
                    nonlinearTrans_res =
                            nonlinearTrans_res | (sbox[(0x000000F0 & (byte) (tmp >> 16 & 0xFF)) >> 4][0x0000000F & (byte) (tmp >> 16 & 0xFF)] & 0xFF) << 16;
                    nonlinearTrans_res =
                            nonlinearTrans_res | (sbox[(0x000000F0 & (byte) (tmp >> 8 & 0xFF)) >> 4][0x0000000F & (byte) (tmp >> 8 & 0xFF)] & 0xFF) << 8;
                    nonlinearTrans_res =
                            nonlinearTrans_res | sbox[(0x000000F0 & (byte) (tmp & 0xFF)) >> 4][0x0000000F & (byte) (tmp & 0xFF)] & 0xFF;
                    tmp = nonlinearTrans_res;

                    tmp = tmp ^ ((tmp >> 19) & 0x1fff | (tmp << 13) & 0xffffe000) ^ ((tmp >> 9) & 0x007fffff | (tmp << 23) & 0xff800000);

                    rk[i] = k[0] ^ tmp;

                    k[0] = rk[i];


                    tmp = k[2] ^ k[3] ^ k[0] ^ ck[i + 1];
                    nonlinearTrans_res = 0;
                    nonlinearTrans_res =
                            (sbox[(0x000000F0 & (byte) (tmp >> 24 & 0xFF)) >> 4][0x0000000F & (byte) (tmp >> 24 & 0xFF)] & 0xFF) << 24;
                    nonlinearTrans_res =
                            nonlinearTrans_res | (sbox[(0x000000F0 & (byte) (tmp >> 16 & 0xFF)) >> 4][0x0000000F & (byte) (tmp >> 16 & 0xFF)] & 0xFF) << 16;
                    nonlinearTrans_res =
                            nonlinearTrans_res | (sbox[(0x000000F0 & (byte) (tmp >> 8 & 0xFF)) >> 4][0x0000000F & (byte) (tmp >> 8 & 0xFF)] & 0xFF) << 8;
                    nonlinearTrans_res =
                            nonlinearTrans_res | sbox[(0x000000F0 & (byte) (tmp & 0xFF)) >> 4][0x0000000F & (byte) (tmp & 0xFF)] & 0xFF;
                    tmp = nonlinearTrans_res;

                    tmp = tmp ^ ((tmp >> 19) & 0x1fff | (tmp << 13) & 0xffffe000) ^ ((tmp >> 9) & 0x007fffff | (tmp << 23) & 0xff800000);
                    rk[i + 1] = k[1] ^ tmp;

                    k[1] = rk[i + 1];


                    tmp = k[3] ^ k[0] ^ k[1] ^ ck[i + 2];
                    nonlinearTrans_res = 0;
                    nonlinearTrans_res =
                            (sbox[(0x000000F0 & (byte) (tmp >> 24 & 0xFF)) >> 4][0x0000000F & (byte) (tmp >> 24 & 0xFF)] & 0xFF) << 24;
                    nonlinearTrans_res =
                            nonlinearTrans_res | (sbox[(0x000000F0 & (byte) (tmp >> 16 & 0xFF)) >> 4][0x0000000F & (byte) (tmp >> 16 & 0xFF)] & 0xFF) << 16;
                    nonlinearTrans_res =
                            nonlinearTrans_res | (sbox[(0x000000F0 & (byte) (tmp >> 8 & 0xFF)) >> 4][0x0000000F & (byte) (tmp >> 8 & 0xFF)] & 0xFF) << 8;
                    nonlinearTrans_res =
                            nonlinearTrans_res | sbox[(0x000000F0 & (byte) (tmp & 0xFF)) >> 4][0x0000000F & (byte) (tmp & 0xFF)] & 0xFF;
                    tmp = nonlinearTrans_res;

                    tmp = tmp ^ ((tmp >> 19) & 0x1fff | (tmp << 13) & 0xffffe000) ^ ((tmp >> 9) & 0x007fffff | (tmp << 23) & 0xff800000);
                    rk[i + 2] = k[2] ^ tmp;

                    k[2] = rk[i + 2];


                    tmp = k[0] ^ k[1] ^ k[2] ^ ck[i + 3];
                    nonlinearTrans_res = 0;
                    nonlinearTrans_res =
                            (sbox[(0x000000F0 & (byte) (tmp >> 24 & 0xFF)) >> 4][0x0000000F & (byte) (tmp >> 24 & 0xFF)] & 0xFF) << 24;
                    nonlinearTrans_res =
                            nonlinearTrans_res | (sbox[(0x000000F0 & (byte) (tmp >> 16 & 0xFF)) >> 4][0x0000000F & (byte) (tmp >> 16 & 0xFF)] & 0xFF) << 16;
                    nonlinearTrans_res =
                            nonlinearTrans_res | (sbox[(0x000000F0 & (byte) (tmp >> 8 & 0xFF)) >> 4][0x0000000F & (byte) (tmp >> 8 & 0xFF)] & 0xFF) << 8;
                    nonlinearTrans_res =
                            nonlinearTrans_res | sbox[(0x000000F0 & (byte) (tmp & 0xFF)) >> 4][0x0000000F & (byte) (tmp & 0xFF)] & 0xFF;
                    tmp = nonlinearTrans_res;

                    tmp = tmp ^ ((tmp >> 19) & 0x1fff | (tmp << 13) & 0xffffe000) ^ ((tmp >> 9) & 0x007fffff | (tmp << 23) & 0xff800000);
                    rk[i + 3] = k[3] ^ tmp;

                    k[3] = rk[i + 3];
                }
            }


        });
    }
}