package com.livehereandnow.ages.util;


import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CropSubImage {

    static Rectangle clip;

    String inputFileLocation = "/home/mark/ages-game/cards.jpg";
    String outputDir = "/home/mark/ages-game/cardsv2/";
    BufferedImage originalImage;

    public static void main(String args[]) throws Exception {
        new CropSubImage().run();
    }

    public CropSubImage() {
        System.out.println("Reading Original File : " + inputFileLocation);
        originalImage = readImage(inputFileLocation);

    }

    public void run() throws Exception {

        /**
         * Image Cropping Parameters
         */
        // int height = 270;
        //int width = 180;
        int height = 270;
        int width = 180;

        int xadj = 0;
        int x0 = 2;
        int y0 = 2;
        int k = 1000;
        for (int row = 0; row < 26; row++) {

            for (int col = 0; col <= 9; col++) {
                k++;
                int cropStartX = x0 + col * (width+xadj);
                int cropStartY = y0 + row * height;
                BufferedImage processedImage = cropMyImage(originalImage, width,
                        height, cropStartX, cropStartY);
                String filename = "";
//                filename = outputDir +"r"+row+ "c" + col + ".jpg";
                filename = outputDir + "p" + k + ".jpg";
                System.out.print("      xxxxWriting the cropped image to: " + filename);
                writeImage(processedImage, filename, "jpg");
                System.out.println("   xxx...Done");
            }
        }
    }

    public static BufferedImage cropMyImage(BufferedImage img, int cropWidth,
            int cropHeight, int cropStartX, int cropStartY) throws Exception {
        BufferedImage clipped = null;
        Dimension size = new Dimension(cropWidth, cropHeight);

        createClip(img, size, cropStartX, cropStartY);

        try {
            int w = clip.width;
            int h = clip.height;

            System.out.println("Crop Width " + w);
            System.out.println("Crop Height " + h);
            System.out.println("Crop Location " + "(" + clip.x + "," + clip.y
                    + ")");

            clipped = img.getSubimage(clip.x, clip.y, w, h);

//            System.out.println("Image Cropped. <span class="IL_AD" id="IL_AD11">New Image</span> Dimension: "
//+ clipped.getWidth() + "w X " + clipped.getHeight() + "h"
//         );
        } catch (RasterFormatException rfe) {
            System.out.println("Raster format error: " + rfe.getMessage());
            return null;
        }
        return clipped;
    }

    /**
     * This method crops an original image to the crop parameters provided.
     *     
* If the crop rectangle lies outside the rectangle (even if partially),
     * adjusts the rectangle to be included within the image area.
     *     
* @param img = Original Image To Be Cropped
     * @param size = Crop area rectangle
     * @param clipX = Starting X-position of crop area rectangle
     * @param clipY = Strating Y-position of crop area rectangle
     * @throws Exception
     */
    private static void createClip(BufferedImage img, Dimension size,
            int clipX, int clipY) throws Exception {
        /**
         * <span class="IL_AD" id="IL_AD6">Some times</span> clip area might lie
         * outside the original image, fully or partially. In such cases, this
         * program will adjust the crop area to fit within the original image.
         *         
* isClipAreaAdjusted flas is usded to denote if there was any
         * adjustment made.
         */
        boolean isClipAreaAdjusted = false;

        /**
         * Checking for negative X Co-ordinate*
         */
        if (clipX < 0) {
            clipX = 0;
            isClipAreaAdjusted = true;
        }
        /**
         * Checking for negative Y Co-ordinate*
         */
        if (clipY < 0) {
            clipY = 0;
            isClipAreaAdjusted = true;
        }

        /**
         * Checking if the clip area lies outside the rectangle*
         */
        if ((size.width + clipX) <= img.getWidth()
                && (size.height + clipY) <= img.getHeight()) {

            /**
             * Setting up <span class="IL_AD" id="IL_AD2">a clip</span>
             * rectangle when clip area lies within the image.
             */
            clip = new Rectangle(size);
            clip.x = clipX;
            clip.y = clipY;
        } else {

            /**
             * Checking if the width of the clip area lies outside the image. If
             * so, making the image width <span class="IL_AD"
             * id="IL_AD3">boundary</span> as the clip width.
             */
            if ((size.width + clipX) > img.getWidth()) {
                size.width = img.getWidth() - clipX;
            }

            /**
             * Checking if the height of the clip area lies outside the image.
             * If so, making the image height boundary as the clip height.
             */
            if ((size.height + clipY) > img.getHeight()) {
                size.height = img.getHeight() - clipY;
            }

            /**
             * Setting up the clip are based on our clip area size adjustment*
             */
            clip = new Rectangle(size);
            clip.x = clipX;
            clip.y = clipY;

            isClipAreaAdjusted = true;

        }
        if (isClipAreaAdjusted) {
            System.out.println("Crop Area Lied Outside The Image."
                    + " Adjusted The Clip Rectangle\n");
        }
    }

    /**
     * This method reads an image from <span class="IL_AD" id="IL_AD8">the
     * file</span>
     *     
* @param fileLocation -- > eg. "C:/testImage.jpg"
     * @return BufferedImage of the file read
     */
    public static BufferedImage readImage(String fileLocation) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileLocation));
            System.out.println("Image Read. Image Dimension: " + img.getWidth()
                    + "w X " + img.getHeight() + "h");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * This method writes a buffered image to a file
     *     
* @param img -- > BufferedImage
     * @param fileLocation --> e.g. "C:/testImage.jpg"
     * @param extension --> e.g. "jpg","gif","png"
     */
    public static void writeImage(BufferedImage img, String fileLocation,
            String extension) {
        try {
            BufferedImage bi = img;
            File outputfile = new File(fileLocation);
            ImageIO.write(bi, extension, outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * SANJAAL CORPS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
     * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT
     * LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
     * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SANJAAL CORPS SHALL NOT BE
     * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING,
     * MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
     *
     * THIS SOFTWARE IS NOT DESIGNED OR INTENDED FOR USE OR RESALE AS ON-LINE
     * CONTROL EQUIPMENT IN HAZARDOUS ENVIRONMENTS REQUIRING FAIL-SAFE
     * PERFORMANCE, SUCH AS IN THE OPERATION OF NUCLEAR FACILITIES, AIRCRAFT
     * NAVIGATION OR <span class="IL_AD" id="IL_AD9">COMMUNICATION SYSTEMS</span>, AIR TRAFFIC CONTROL, DIRECT LIFE
     * SUPPORT MACHINES, OR WEAPONS SYSTEMS, IN WHICH THE FAILURE OF THE
     * SOFTWARE COULD LEAD DIRECTLY TO DEATH, PERSONAL INJURY, OR SEVERE
     * PHYSICAL OR ENVIRONMENTAL DAMAGE ("HIGH RISK ACTIVITIES"). SANJAAL CORPS
     * SPECIFICALLY DISCLAIMS ANY EXPRESS OR IMPLIED WARRANTY OF FITNESS FOR
     * HIGH RISK ACTIVITIES.
     */
}
