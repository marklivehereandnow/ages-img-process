/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.livehereandnow.ages.util;

/**
 *
 * @author mark
 */
public class GenerateImgSourceCode {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     //   <img src="http://2nd2go.org/ages/img/p1001.jpg" alt="p1001" />
        int col=0;
        for (int k=1001; k<=1257;k++){
            System.out.println(" <img src=\"http://2nd2go.org/ages/img/p"+k+".jpg\" alt=\"p"+k+"\""+" title=\"p"+k+"\"/>");
            col++;
            if (col==5){
                System.out.println("<br/>");
                col=0;
            }
        
        }
    
    }
    
}
