/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.hashcode.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author lucas
 */
public interface DataFileTransformer {
    public void onLoad(BufferedReader bufferReader, DataFileIn dataFileIn) throws IOException;
    public void onSave(PrintWriter outputFile, DataFileOut dataFileOut);
}
